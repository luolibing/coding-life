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

    public <T> void saveEntity(String redisKey, String idKey, T t) {
        redisTemplate.opsForHash().put(redisKey, idKey, t);
    }

    public Object getEntity(String redisKey, String idKey) {
        return redisTemplate.opsForHash().get(redisKey, idKey);
    }

    public void deleteEntity(String key) {
        redisTemplate.delete(key);
    }

    public boolean isInSet(String redisKey, Object value) {
        return redisTemplate.opsForSet().isMember(redisKey, value);
    }

    public long putToSet(String redisKey, Object value) {
        return redisTemplate.opsForSet().add(redisKey, value);
    }

    public boolean putToZSet(String redisKey, Object key, long score) {
        return redisTemplate.opsForZSet().add(redisKey, key, score);
    }

    public double increment(String redisKey, Object key, long score) {
        return redisTemplate.opsForZSet().incrementScore(redisKey, key, score);
    }

    public double getScore(String redisKey, Object key) {
        return redisTemplate.opsForZSet().score(redisKey, key);
    }
}
