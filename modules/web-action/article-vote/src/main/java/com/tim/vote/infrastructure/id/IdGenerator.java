package com.tim.vote.infrastructure.id;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by luolibing on 2018/4/24.
 */
@Component
public class IdGenerator {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public boolean initIdKey(String idKey, long initValue) {
        return redisTemplate.opsForValue().setIfAbsent(idKey, initValue);
    }

    public Long getNextId(String idKey) {
        return redisTemplate.opsForValue().increment(idKey, 1);
    }

    public void resetId(String idKey) {
        redisTemplate.delete(idKey);
    }

    public String generateKey(String keyPre, String id) {
        return keyPre + "pre" + id;
    }
}
