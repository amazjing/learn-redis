package com.ama.jedis;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;

/**
 * Jedis测试
 *
 * @Version 0.0.1
 * @Author WenZhe Wang
 * @Date 2022/2/15 15:43
 */
public class JedisDemo1 {

    public static void main(String[] args) {
        //创建Jedis对象
        //host:虚拟机iP地址
        Jedis jedis = new Jedis("192.168.218.131", 6379);

        //测试
        String value = jedis.ping();
        System.out.println(value);
    }

    /**
     * 操作Key String字符串
     */
    @Test
    public void demo1() {

        //创建Jedis对象
        //host:虚拟机iP地址
        Jedis jedis = new Jedis("192.168.218.131", 6379);

        //添加
        jedis.set("name", "lucy");
        //获取
        String name = jedis.get("name");
        System.out.println("name===" + name);

        //添加多个key-value
        jedis.mset("k1", "v1", "k2", "v2");
        //获取多个key-value
        List<String> mget = jedis.mget("k1", "k2");
        //遍历输出
        mget.stream().forEach(System.out::println);


        Set<String> keys = jedis.keys("*");
        //遍历输出所有key
        keys.stream().forEach(System.out::println);
    }

    /**
     * 操作List集合
     */
    @Test
    public void demo2() {

        //创建Jedis对象
        //host:虚拟机iP地址
        Jedis jedis = new Jedis("192.168.218.131", 6379);

        //添加数据
        jedis.lpush("key1", "lucy", "mary", "jack");
        //从左到右获取key1的所有value值
        List<String> valuesL = jedis.lrange("key1", 0, -1);
        //遍历输出
        valuesL.stream().forEach(System.out::println);
    }

    /**
     * 操作Set集合
     */
    @Test
    public void demo3() {

        //创建Jedis对象
        //host:虚拟机iP地址
        Jedis jedis = new Jedis("192.168.218.131", 6379);

        //添加数据
        jedis.sadd("names", "lucy", "mary");
        //获取数据
        Set<String> names = jedis.smembers("names");
        //遍历输出
        names.stream().forEach(System.out::println);
    }

    /**
     * 操作hash
     */
    @Test
    public void demo4() {

        //创建Jedis对象
        //host:虚拟机iP地址
        Jedis jedis = new Jedis("192.168.218.131", 6379);

        //添加数据
        jedis.hset("users", "age", "20");
        //获取数据
        String hget = jedis.hget("users", "age");
        //输出
        System.out.println("hget====" + hget);
    }

    /**
     * 操作zset
     */
    @Test
    public void demo5() {

        //创建Jedis对象
        //host:虚拟机iP地址
        Jedis jedis = new Jedis("192.168.218.131", 6379);

        //添加数据
        jedis.zadd("china1", 100d, "shanghai");
        //获取数据(0,-1:代表获取所有值)
        Set<String> china = jedis.zrange("china1", 0, -1);
        china.stream().forEach(System.out::println);
    }
}