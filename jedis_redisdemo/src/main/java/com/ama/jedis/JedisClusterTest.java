package com.ama.jedis;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

/**
 * @program: com.ama
 * @description: Redis集群
 * @author: Wang WenZhe
 * @create: 2022-02-27 11:30
 **/
public class JedisClusterTest {
    public static void main(String[] args) {
        Set<HostAndPort> set = new HashSet<HostAndPort>();
        set.add(new HostAndPort("192.168.189.7", 6379));
        JedisCluster jedisCluster = new JedisCluster(set);
        jedisCluster.set("b1", "value1");
        System.out.println(jedisCluster.get("b1"));
        jedisCluster.close();
    }
}