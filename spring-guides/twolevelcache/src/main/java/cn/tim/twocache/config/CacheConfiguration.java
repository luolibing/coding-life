package cn.tim.twocache.config;

import cn.tim.twocache.message.RedisMessageReceiver;
import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.cache.support.CompositeCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;


/**
 * Created by LuoLiBing on 15/11/26.
 * cache配置
 */
@Configuration
public class CacheConfiguration {

    /**
     * 缓存管理器
     * @return
     */
    @Primary
    @Bean
    public CacheManager cacheManager() {
        // 普通的ConcurrentMapCache  "base", "extend"
        // return new ConcurrentMapCacheManager();
        // 默认会注入StringRedisTemplate，只能保存String，如果换成其他对象无法保存到内存当中

        // 二级缓存
        CompositeCacheManager compositeCacheManager = new CompositeCacheManager(
                ehCacheCacheManager(),
                redisCacheManager());
        compositeCacheManager.setFallbackToNoOpCache(true);
        return compositeCacheManager;
    }

    /**
     * redis
     * @return
     */
    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName("192.168.0.111");
        //factory.setPort(redisPort);
        factory.setDatabase(5);
        factory.setUsePool(true);
        return factory;
    }


    /**
     * 二级redis缓存
     * @return
     */
    @Bean
    RedisTemplate<Object, Object> redisTemplate() {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        return redisTemplate;
    }


    @Bean(name = "ehCacheCacheManager")
    EhCacheCacheManager ehCacheCacheManager() {
        // 一级缓存
        EhCacheCacheManager ehCacheCacheManager = new EhCacheCacheManager();
        ehCacheCacheManager.setCacheManager(ehCacheManagerFactoryBean().getObject());
        return ehCacheCacheManager;
    }


    @Bean(name = "redisCacheManager")
    RedisCacheManager redisCacheManager() {
        return new RedisCacheManager(redisTemplate());
    }


    /**
     * 一级ehcache缓存
     * @return
     */
    @Bean
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
        EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean();
        cacheManagerFactoryBean.setShared(true);
        cacheManagerFactoryBean.setCacheManagerName("cacheManger");
        cacheManagerFactoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
        return cacheManagerFactoryBean;
    }


    /******************* redisMessage 添加订阅 *******************/
    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                            MessageListenerAdapter listenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, new PatternTopic("onekey"));
        return container;
    }


    @Bean
    MessageListenerAdapter listenerAdapter(RedisMessageReceiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

}
