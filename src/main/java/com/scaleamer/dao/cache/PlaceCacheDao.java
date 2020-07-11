package com.scaleamer.dao.cache;


import com.scaleamer.concurrent.lock.RedisLock;
import com.scaleamer.dao.database.PlaceMapper;
import com.scaleamer.domain.Place;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PlaceCacheDao implements PlaceMapper {

    @Autowired
    private PlaceMapper placeMapper;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private RedisLock redisLock;

    @Override
    public List<Place> getPlaces() {
        ValueOperations<String, Object> stringObjectValueOperations = redisTemplate.opsForValue();
        List<Place> result = (List<Place>) stringObjectValueOperations.get(keyOfPlaces());
        if(result==null){
            try{
                redisLock.lock(getLockKey());
                //二次检测 为了啥，为了防止多个线程竞争更新 防止内存击穿
                result = (List<Place>) stringObjectValueOperations.get(keyOfPlaces());
                if(result==null){
                    result = placeMapper.getPlaces();
                }
                //Thread.sleep(10000);
                if(result!=null){
                    stringObjectValueOperations.set(keyOfPlaces(),result);
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                redisLock.unlock(getLockKey());
            }
        }
        return result;
    }

    @Override
    public Place getPlaceById(int place_id) {
        ValueOperations<String, Object> stringObjectValueOperations = redisTemplate.opsForValue();
        Place place = (Place) stringObjectValueOperations.get(keyOfPlace(place_id));
        if(place==null){
            try{
                redisLock.lock(getLockKey());
                place = (Place) stringObjectValueOperations.get(keyOfPlace(place_id));
                if(place==null){
                    place = placeMapper.getPlaceById(place_id);
                }
                //Thread.sleep(10000);
                if(place!=null){
                    stringObjectValueOperations.set(keyOfPlace(place_id),place);
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                redisLock.unlock(getLockKey());
            }
        }
        return place;
    }

    @Override
    public List<Integer> getIds() {
        ValueOperations<String, Object> stringObjectValueOperations = redisTemplate.opsForValue();
        List<Integer> list = (List<Integer>) stringObjectValueOperations.get(keyOfIds());
        if(list==null){
            try{
                redisLock.lock(getLockKey());
                list = (List<Integer>) stringObjectValueOperations.get(keyOfIds());
                if(list==null){
                    list = placeMapper.getIds();
                }
                //Thread.sleep(10000);
                if(list!=null){
                    stringObjectValueOperations.set(keyOfIds(),list); //可以写入空值进redis 可以设置过期时间
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                redisLock.unlock(getLockKey());
            }
        }
        return list;
    }

    /**
     * 问题是增加和删除都是同步的了，插入没有并发性可言，要么用mq解耦，要么等待。
     * @param place
     * @return
     */
    @Override
    public int insertPlace(Place place) {
        try{
            redisLock.lock(getLockKey());
            redisTemplate.delete(keyOfPlaces());
            redisTemplate.delete(keyOfIds());
            Thread.sleep(10000);
            return placeMapper.insertPlace(place);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }finally {
            redisLock.unlock(getLockKey());
        }
    }

    @Override
    public int deletePlaceById(int id) {
        try{
            redisLock.lock(getLockKey());
            redisTemplate.delete(keyOfPlaces());
            redisTemplate.delete(keyOfIds());
            Thread.sleep(10000);
            return placeMapper.deletePlaceById(id);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }finally {
            redisLock.unlock(getLockKey());
        }

    }

    @Override
    public int modifyPlace(Place place) {
        try{
            redisLock.lock(getLockKey());
            redisTemplate.delete(keyOfPlaces());
            redisTemplate.delete(keyOfPlace(place.getPlace_id()));
            Thread.sleep(10000);
            return placeMapper.modifyPlace(place);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }finally {
            redisLock.unlock(getLockKey());
        }
    }

    private String keyOfPlaces(){
        return "place:places";
    }

    private String keyOfPlace(int place_id){
        return "place:place:" + place_id;
    }

    private String keyOfIds(){
        return "place:ids";
    }

    private static String getLockKey(){
        return "lock:place:all";
    }
}
