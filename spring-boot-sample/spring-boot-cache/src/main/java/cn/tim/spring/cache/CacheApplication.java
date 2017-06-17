package cn.tim.spring.cache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Arrays;

import static cn.tim.spring.cache.constant.CachingKeys.CACHES;

/**
 * User: luolibing
 * Date: 2017/4/21 10:58
 * Email: 397911353@qq.com
 */
@EnableCaching
@SpringBootApplication
public class CacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(CacheApplication.class, args);
    }

//    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(CACHES);
    }

    @Bean
    public CacheManager redisCacheManager(RedisTemplate redisTemplate) {
        return new RedisCacheManager(redisTemplate, Arrays.asList(CACHES));
    }

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(factory);
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }
}
