package com.ama.redis_springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * RedisController测试类
 *
 * @Version 0.0.1
 * @Author WenZhe Wang
 * @Date 2022/2/16 10:59
 */
@RestController
@RequestMapping(value = "/redisTest")
public class RedisTestController {

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping(value = "/redis")
    public String testRedis() {
        //设置值到redis
        redisTemplate.opsForValue().set("name", "lucy");
        //从redis中获取值
        String name = (String) redisTemplate.opsForValue().get("name");

        return name;
    }

    @GetMapping("testLock")
    public void testLock() {
        //设置UUID
        String uuid = UUID.randomUUID().toString();

        //1获取锁，setne

        //增加UUID
        Boolean lock = redisTemplate.opsForValue().setIfAbsent("lock", uuid, 3, TimeUnit.SECONDS);

        //2获取锁成功、查询num的值
        if (lock) {
            Object value = redisTemplate.opsForValue().get("num");
            //2.1判断num为空return
            if (StringUtils.isEmpty(value)) {
                return;
            }
            //2.2有值就转成成int
            int num = Integer.parseInt(value + "");
            //2.3把redis的num加1
            redisTemplate.opsForValue().set("num", ++num);

            //2.4释放锁，del
            //判断UUID是否一致
            if (uuid.equals((String) redisTemplate.opsForValue().get("lock"))) {
                this.redisTemplate.delete("lock");
            }

        } else {
            //3获取锁失败、每隔0.1秒再获取
            try {
                Thread.sleep(100);
                testLock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}