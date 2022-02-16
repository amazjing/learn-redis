package com.ama.redis_springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}