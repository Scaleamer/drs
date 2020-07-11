package com.scaleamer.concurrent.lock;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;


import java.io.Serializable;
import java.util.*;
import java.util.concurrent.*;

/**
 * 来自https://blog.csdn.net/weixin_40098891/article/details/102771805
 * 改动，删除request id。不允许跨request加解锁行为
 */
@Component
public class RedisLock {

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String LOCK_PREFIX = "lock:";

    private static final long DEFAULT_LOCK_TIMEOUT = 30;

    private static final long TIME_SECONDS_FIVE = 5;

    /**
     * lock 的缓存
     */
    private Map<String, LockContent> lockContentMap = new ConcurrentHashMap<>(512);

    /**
     * redis执行成功的返回
     */
    private static final Long EXEC_SUCCESS = 1L;


    private static final String LOCK_SCRIPT = "return redis.call('set', KEYS[1], ARGV[1], 'NX', 'EX', ARGV[2])";


    private static final String UNLOCK_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then " +
            "return redis.call('del', KEYS[1]) " +
            "else return 0 end";

    private static final String RENEW_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then " +
            "return redis.call('expire', KEYS[1], ARGV[2]) else " +
            "return 0 end";




    /**
     * 改为setter注入
     *
     * @param redisTemplate
     */
    public void setRedisTemplate(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public RedisLock() {

        startSchedule(0, 20, TimeUnit.SECONDS);
    }


    /**
     * 没有那么高的自由度，自由度会导致性能不稳定
     *
     * @param lockKey
     */
    public void lock(String lockKey) {
        //自适应自旋
        int count = 0;
        int maxTry = 32;

        for (; ; ) {
            // 判断是否已经有线程持有锁，减少redis的压力
            LockContent lockContentOld = lockContentMap.get(lockKey);
            boolean unLocked = (null == lockContentOld);
            // 如果没有被锁，就获取锁
            if (unLocked) {
                RedisScript<String> script = RedisScript.of(LOCK_SCRIPT, String.class);
                //省点内存吧
                List<String> keys = new ArrayList<>(1);
                keys.add(lockKey);
                String id_random = UUID.randomUUID().toString();
                String success = redisTemplate.execute(script, keys, id_random, Long.toString(DEFAULT_LOCK_TIMEOUT));
                if (null != success && success.equals("OK")) {
                    // 将锁放入map
                    System.out.println(Thread.currentThread()+" GET SUCCESS");
                    LockContent lockContent = new LockContent();
                    lockContent.setId_random(id_random);
                    lockContent.setThread(Thread.currentThread());
                    lockContent.setLockCount(1);
                    lockContentMap.put(lockKey, lockContent);
                    return;
                }//else就申请redis锁失败了
            }

            // 重复获取锁，在线程池中由于线程复用，线程相等并不能确定是该线程的锁
            // 如果这么说的话jdk所有lock都不能用了 给id只是为了防止其他人删了
            // 总的来说执行到这一步已经代表了已经有锁了，但是还没判断是谁的。同时如果是本线程的话，由于是可重入的，就判断一下是否是本线程。
            // 另外由于是本线程行为，不可能出现那种判断的时候被unlock了。
            if (lockContentOld != null && Thread.currentThread() == lockContentOld.getThread()) {
                // 计数 +1
                lockContentOld.setLockCount(lockContentOld.getLockCount() + 1);
                return;
            }

            count++;
            if (count >= maxTry) {
                count = 0;
                int next_maxTry = maxTry / 2;
                maxTry = next_maxTry > 0 ? next_maxTry : 1;
                // 如果被锁或获取锁失败，则等待100毫秒
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException("Fail to obtain a lock");
                }
            }

        }
    }


    /**
     * 先remove redis 还是先remove缓存。
     * 假如先remove redis 本进程内查看到有锁的缓存是不会去上锁的，貌似没问题，但是白等了
     * 假如先remove 缓存， 假如这个时刻进来一个线程去获取缓存，空了然后去redis操作，有可能失败，增多了一次开销。
     * 考虑两步之内有其他程序插入的问题？也只有lock能插入了，就是上述的问题
     *
     * @param lockKey
     */
    public void unlock(String lockKey) {
        String lockKeyRenew = lockKey + "_renew";

        LockContent lockContent = lockContentMap.get(lockKey);
        if (lockContent == null || Thread.currentThread() != lockContent.getThread()) {
            throw new IllegalMonitorStateException();
        }
        int c = lockContent.getLockCount() - 1;
        if (c == 0) {
            RedisScript<Long> script = RedisScript.of(UNLOCK_SCRIPT, Long.class);
            List<String> keys = new ArrayList<>(1);
            keys.add(lockKey);
            Long result = redisTemplate.execute(script, keys, lockContent.getId_random());
            if(result==null || result <=0){
                throw new IllegalMonitorStateException("redis 中的锁记录被删了，可能是过期没有续导致，也可能是外部手段被删了");
            }
            lockContentMap.remove(lockKey);
        }
    }


    private boolean renew(String lockKey, LockContent lockContent) {

        // 检测执行业务线程的状态
        Thread.State state = lockContent.getThread().getState();
        if (Thread.State.TERMINATED == state) {
            return false;
        }

        String random_id = lockContent.getId_random();

        RedisScript<Long> script = RedisScript.of(RENEW_SCRIPT, Long.class);
        List<String> keys = new ArrayList<>();
        keys.add(lockKey);

        Long result = redisTemplate.execute(script, keys, random_id, Long.toString(DEFAULT_LOCK_TIMEOUT));
        return EXEC_SUCCESS.equals(result);
    }


    public void startSchedule(long initialDelay, long period, TimeUnit unit) {
        ScheduleTask task = new ScheduleTask();
        long delay = unit.toMillis(initialDelay);
        long period_ = unit.toMillis(period);
        // 定时执行
        new Timer("Lock-Renew-Task").schedule(task, delay, period_);
    }

    private class ScheduleTask extends TimerTask {
        @Override
        public void run() {
            if (lockContentMap.isEmpty()) {
                return;
            }
            Set<Map.Entry<String, LockContent>> entries = lockContentMap.entrySet();
            for (Map.Entry<String, LockContent> entry : entries) {
                String lockKey = entry.getKey();
                LockContent lockContent = entry.getValue();
                ThreadPool.submit(() -> {
                    boolean renew = renew(lockKey, lockContent);
                    if (!renew) {
                        // 续约失败，说明已经执行完 OR redis 出现问题
                        // 确实有可能是unlock之后 然后执行到这里
                        lockContentMap.remove(lockKey);
                    }
                });
            }
        }
    }


    public static class LockContent implements Serializable {


        /**
         * 用于防止锁的误删，全局唯一
         */
        private String id_random;

        /**
         * 执行业务的线程
         */
        private volatile Thread thread;

        /**
         * 可重入的计数器
         */
        private int lockCount = 0;

        public String getId_random() {
            return id_random;
        }

        public void setId_random(String id_random) {
            this.id_random = id_random;
        }

        public Thread getThread() {
            return thread;
        }

        public void setThread(Thread thread) {
            this.thread = thread;
        }

        public int getLockCount() {
            return lockCount;
        }

        public void setLockCount(int lockCount) {
            this.lockCount = lockCount;
        }
    }

    static class ThreadPool {

        private static final int CORESIZE = Runtime.getRuntime().availableProcessors();
        private static final int MAXSIZE = 200;
        private static final int KEEPALIVETIME = 60;
        private static final int CAPACITY = 2000;

        private static ThreadPoolExecutor threadPool;

        static {
            init();
        }

        private ThreadPool() {
        }

        /**
         * 初始化所有线程池。
         */
        private static void init() {
            threadPool = new ThreadPoolExecutor(CORESIZE, MAXSIZE, KEEPALIVETIME,
                    TimeUnit.SECONDS, new LinkedBlockingQueue<>(CAPACITY));
            //log.info("初始化线程池成功。");
        }

        public static Future<?> submit(Runnable task) {
            if (null == task) {
                throw new IllegalArgumentException("任务不能为空");
            }
            return threadPool.submit(task);
        }
    }
}