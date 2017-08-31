package com.tim.springboot.docker.compose.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by luolibing on 2017/8/31.
 */
@Service
public class BookService {

    @Autowired
    private RedisTemplate redisTemplate;

    public Long nextId(String key) {
        return redisTemplate.opsForValue().increment(key, 1L);
    }
}
