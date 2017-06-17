package cn.tim.redis;

import cn.tim.redis.client.ListRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Created by LuoLiBing on 15/11/9.
 */
@SpringBootApplication
@RestController
@EnableCaching
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    private ListRedis listRedis;

    /*@Autowired
    private ZSetRedis listRedis;*/

//    @Autowired
//    private OperationsTest listRedis;

    @RequestMapping(value = "/list")
    public Set<Map<String, Object>> list() {
        return listRedis.list();
    }

    @RequestMapping(value = "/add/{id}/{name}")
    public String add(@PathVariable Long id, @PathVariable String name) {
        Map<String, Object> beanMap = new HashMap<>();
        beanMap.put("id", id);
        beanMap.put("name", name);
        listRedis.add(id, beanMap);
        return "Y";
    }


    @RequestMapping(value = "/delete/{id}")
    public String delete(@PathVariable Long id) {
        listRedis.delete(id);
        return "Y";
    }

    @RequestMapping(value = "/get/{id}")
    public Map<String, Object> get(@PathVariable Long id) {
        return listRedis.get(id);
    }

    /**
     * 缓存管理器
     * @return
     */
    @Bean
    public CacheManager cacheManager() {
        // 普通的ConcurrentMapCache
        // return new ConcurrentMapCacheManager("books", "categorys");
        // 默认会注入StringRedisTemplate，只能保存String，如果换成其他对象无法保存到内存当中
        List<String> cacheNames = new ArrayList<>();
        cacheNames.add("baseSet");
        cacheNames.add("allBaseSet");
        EhCacheCacheManager cacheManager = new EhCacheCacheManager();
        cacheManager.setCacheManager(ehCacheManagerFactoryBean().getObject());
        return cacheManager;
        //return new RedisCacheManager(redisTemplate(), cacheNames);
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


    /********************* 一级缓存 **********************/
    /**
     * ehcache一级缓存
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

}
