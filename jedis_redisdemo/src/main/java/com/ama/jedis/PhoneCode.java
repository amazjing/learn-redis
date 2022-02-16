package com.ama.jedis;

import redis.clients.jedis.Jedis;

import java.util.Random;

/**
 * @program: com.ama
 * @description: 手机验证码
 * @author: Wang WenZhe
 * @create: 2022-02-15 19:34
 **/
public class PhoneCode {
    public static void main(String[] args) {
        //模拟验证码发送
        //verifyCode("1359089898");

        //检验验证码
        getRedisCode("1359089898", "515062");
    }

    /**
     * 生成6位数字验证码
     *
     * @return 6位数字验证码
     */
    public static String getCode() {
        Random random = new Random();
        String code = "";
        for (int i = 0; i < 6; i++) {
            int rand = random.nextInt(10);
            code += rand;
        }
        return code;
    }

    /**
     * 每个手机每天只能发送三次，验证码放到redis中，设置过期时间
     *
     * @param phone 手机号
     */
    public static void verifyCode(String phone) {
        //连接redis
        //host:虚拟机iP地址
        Jedis jedis = new Jedis("192.168.234.128", 6379);

        //拼接key
        //手机发送次数key
        String countKey = "VerifyCode" + phone + "count";

        //验证码key
        String codeKey = "VerifyCode" + phone + "code";

        //根据Key获取value
        String count = jedis.get(countKey);
        if (count == null) {
            //没有发送次数，第一次发送
            //设置value发送次数是1
            jedis.setex(countKey, 24 * 60 * 60, "1");
        } else if (Integer.parseInt(count) <= 2) {
            //发送次数+1
            //对countKey键的value值进行+1;
            jedis.incr(countKey);
        } else if (Integer.parseInt(count) > 2) {
            //发送三次，不能再发送
            System.out.println("今天发送次数已经超过三次");
            jedis.close();
            return;
        }

        //获取验证码
        String vCode = getCode();
        //发送验证码放到redis里面，设置过期时间2分钟
        jedis.setex(codeKey, 120, vCode);
    }

    /**
     * 验证码校验
     *
     * @param phone 手机号
     * @param code  验证码
     */
    public static void getRedisCode(String phone, String code) {
        //连接redis
        Jedis jedis = new Jedis("192.168.234.128", 6379);
        //验证码key
        String codeKey = "VerifyCode" + phone + "code";
        //从redis获取验证码
        String redisCode = jedis.get(codeKey);
        if (redisCode == null) {
            System.out.println("验证码已过期");
            return;
        }
        //判断
        if (redisCode.equals(code)) {
            System.out.println("成功");
        } else {
            System.out.println("失败");
        }
        jedis.close();
    }

}
