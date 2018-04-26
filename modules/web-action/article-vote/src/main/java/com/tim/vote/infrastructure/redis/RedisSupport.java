package com.tim.vote.infrastructure.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by luolibing on 2018/4/24.
 */
@Component
public class RedisSupport {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public <T> T saveEntity(String key, T t) {
        return (T) redisTemplate.opsForValue().getAndSet(key, t);
    }

    public <T> T getEntity(String key, Class<T> clazz) {
        return (T)redisTemplate.opsForValue().get(key);
    }

    public void deleteEntity(String key) {
        redisTemplate.delete(key);
    }
}
