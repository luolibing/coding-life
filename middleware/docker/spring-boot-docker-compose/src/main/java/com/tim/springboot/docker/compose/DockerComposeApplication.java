package com.tim.springboot.docker.compose;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by luolibing on 2017/8/31.
 */
@EnableCaching
@SpringBootApplication
public class DockerComposeApplication {

    public static void main(String[] args) {
        SpringApplication.run(DockerComposeApplication.class, args);
    }


    /**
     * 缓存管理器
     * @return
     */
    @Bean
    public CacheManager cacheManager() {
        // 普通的ConcurrentMapCache
        //return new ConcurrentMapCacheManager("books", "categorys");
        // 默认会注入StringRedisTemplate，只能保存String，如果换成其他对象无法保存到内存当中
        RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate());
        Map<String, Long> expires = new HashMap<>();
        expires.put("books", 1000L * 60);
        expires.put("categorys", 1000L * 60 * 10);
        redisCacheManager.setExpires(expires);
        return redisCacheManager;
    }


    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        //factory.setHostName("localhost");
        //factory.setPort(redisPort);
        factory.setUsePool(true);
        return factory;
    }

    /**
     * 能保存到内存当中的对象必须是实现了Serializable接口
     * @return
     */
    @Bean
    RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        return redisTemplate;
    }
}
