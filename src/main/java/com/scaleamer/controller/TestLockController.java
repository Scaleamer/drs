package com.scaleamer.controller;

import com.github.pagehelper.PageInfo;
import com.scaleamer.concurrent.lock.RedisLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
@RequestMapping("/test")
public class TestLockController {

    @Autowired
    private RedisLock redisLock;

    @RequestMapping("/fun1")
    @ResponseBody
    public String fun1()  {
        Date date = new Date();
        System.out.println(Thread.currentThread()+" BEGIN " + date);
        redisLock.lock("lock:test:1");
        System.out.println(Thread.currentThread()+" AFTER LOCK");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread()+" AFTER SLEEP");
        redisLock.unlock("lock:test:1");
        System.out.println(Thread.currentThread()+" END " + new Date());
        return "SUCCESS";
    }
}
