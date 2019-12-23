package com.dongtian.paymentsystem.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
@Component
public class RedisUtils {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 功能描述:(使用key 查找redis信息)
     */
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }
    /*
    * 功能描述:往redis中设置值
    *
    */
    public void set(String key, Object value, Long timeOut) {
        if (value != null) {
            if (value instanceof String) {
                String setValue = (String) value;
                stringRedisTemplate.opsForValue().set(key, setValue);
            }
            // TODO 封装其他类型
            // 设置有效期
            if (timeOut != null)
                stringRedisTemplate.expire(key, timeOut, TimeUnit.SECONDS);

        }

    }
}
