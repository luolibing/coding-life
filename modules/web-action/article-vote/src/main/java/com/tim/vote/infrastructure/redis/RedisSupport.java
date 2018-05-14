package com.tim.vote.infrastructure.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Created by luolibing on 2018/4/24.
 */
@Component
public class RedisSupport {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void multi() {
        redisTemplate.multi();
    }

    public void exec() {
        redisTemplate.exec();
    }

    public <T> void saveEntity(String redisKey, String idKey, T t) {
        redisTemplate.opsForHash().put(redisKey, idKey, t);
    }

    public void putToHash(String redisKey, String fieldName, Object filedValue) {
        redisTemplate.opsForHash().put(redisKey, fieldName, filedValue);
    }

    public Object getFromHash(String redisKey, String filedName) {
        return redisTemplate.opsForHash().get(redisKey, filedName);
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

    public boolean putToSet(String redisKey, Object value) {
        return redisTemplate.opsForSet().add(redisKey, value) == 1;
    }

    public boolean putToZSet(String redisKey, Object key, long score) {
        return redisTemplate.opsForZSet().add(redisKey, key, score);
    }

    public double increment(String redisKey, Object key, long score) {
        return redisTemplate.opsForZSet().incrementScore(redisKey, key, score);
    }

    public Long incrementHash(String redisKey, Object key, long score) {
        return redisTemplate.opsForHash().increment(redisKey, key, score);
    }

    public double getScore(String redisKey, Object key) {
        return redisTemplate.opsForZSet().score(redisKey, key);
    }

    public Set<Object> members(String redisKey) {
        return redisTemplate.opsForSet().members(redisKey);
    }

    public Set<ZSetOperations.TypedTuple<Object>> range(String redisKey, boolean asc, long start, long end) {
        if(asc) {
            return redisTemplate.opsForZSet().rangeWithScores(redisKey, start, end);
        }
        return redisTemplate.opsForZSet().reverseRangeWithScores(redisKey, start, end);
    }

    public long delHashEntity(String key, String[] fields) {
        return redisTemplate.opsForHash().delete(key, fields);
    }

    public long findCountOfZset(String key) {
        return redisTemplate.opsForZSet().size(key);
    }
}
