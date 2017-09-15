package cn.tim.cache;

import cn.tim.cache.entity.Book;
import cn.tim.cache.jpa.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by LuoLiBing on 15/9/30.
 */
@SpringBootApplication
@EnableCaching
public class Application {

    private final static Logger log = LoggerFactory.getLogger(Application.class);

    @Component
    static class Runner implements CommandLineRunner {

        @Autowired
        private BookRepository bookRepository;

        @Autowired
        private CacheManager cacheManager;

        @Override
        public void run(String... args) throws Exception {

            // 查看是否每次都到数据库查询
            log.info(".... Fetching books");

//            System.out.println(categoryRepository.getCategoryById("1"));
//            categoryRepository.getCategoryById("1");
            log.info("isbn-1234 -->" + bookRepository.getByIsbn("isbn-1234"));
            log.info("isbn-1234 -->" + bookRepository.getByIsbn("isbn-1234"));

            cacheManager.getCache("books").put("isbn-1234", new Book("isbn-1234", "Some book"));
            // bookRepository.update("isbn-1234", "isbn-1234测试");
            log.info("isbn-1234 -->" + bookRepository.getByIsbn("isbn-1234"));

            bookRepository.clear("isbn-1234");
            log.info("isbn-1234 -->" + bookRepository.getByIsbn("isbn-1234"));
            log.info("isbn-1234 -->" + bookRepository.getByIsbn("isbn-2345"));
            final Collection<String> cacheNames = cacheManager.getCacheNames();
            for(String cache: cacheNames) {
                System.out.println(cache);
            }
//            log.info("isbn-4567 -->" + bookRepository.getByIsbn("isbn-4567"));
//            log.info("isbn-1234 -->" + bookRepository.getByIsbn("isbn-1234"));
//            log.info("isbn-1234 -->" + bookRepository.getByIsbn("isbn-1234"));*/
        }
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


    /**
     * 序列化key
     * https://jira.spring.io/browse/DATAREDIS-354 BUG解决
     * @param jedisConnectionFactory
     * @return
     */
    @Bean
    RedisTemplate<Object, Object> redisTemplate(JedisConnectionFactory jedisConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        // key的序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        // value的序列化
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        return redisTemplate;
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
    RedisTemplate<Object, Object> redisTemplate() {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        return redisTemplate;
    }


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    KeyGenerator keyGenerator() {
        return new SimpleKeyGenerator();
    }

    /*@Bean
    public UseCacheExclusivelyInReadOnlyModeAspect readOnlyModeAspect(@Value("${app.mode.read-only: true}") boolean readOnly) {
        return new UseCacheExclusivelyInReadOnlyModeAspect(readOnly);
    }*/

}
