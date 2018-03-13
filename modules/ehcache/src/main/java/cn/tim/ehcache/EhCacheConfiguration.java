package cn.tim.ehcache;

import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.core.config.DefaultConfiguration;
import org.ehcache.jsr107.EhcacheCachingProvider;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.Caching;
import java.util.HashMap;
import java.util.Map;

/**
 * User: luolibing
 * Date: 2018/3/13 19:26
 */
@Configuration
@EnableCaching
public class EhCacheConfiguration {

    /**
     * 一级ehcache缓存
     * @return
     */
//    @Bean
//    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
//        EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean();
//        cacheManagerFactoryBean.setShared(true);
//        cacheManagerFactoryBean.setCacheManagerName("cacheManger");
//        cacheManagerFactoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
//        return cacheManagerFactoryBean;
//    }
//
//    @Bean(name = "ehCacheCacheManager")
//    public EhCacheCacheManager ehCacheCacheManager() {
//        // 一级缓存
//        EhCacheCacheManager ehCacheCacheManager = new EhCacheCacheManager();
//        ehCacheCacheManager.setCacheManager(ehCacheManagerFactoryBean().getObject());
//        return ehCacheCacheManager;
//    }

    @Bean
    public CacheManager cacheManager() {
        final EhcacheCachingProvider provider = (EhcacheCachingProvider) Caching.getCachingProvider();

        final Map<String, CacheConfiguration<?, ?>> caches = new HashMap<>();
        caches.put("roleWrite", getCache());

        final DefaultConfiguration configuration = new DefaultConfiguration(caches, provider.getDefaultClassLoader());
        return new JCacheCacheManager(provider.getCacheManager(provider.getDefaultURI(), configuration));
    }
    private CacheConfiguration<?, ?> getCache() {
        final ResourcePoolsBuilder res = ResourcePoolsBuilder.heap(100);
        // Spring does not allow anything else than Objects...
        final CacheConfigurationBuilder<Object, Object> newCacheConfigurationBuilder = CacheConfigurationBuilder
                .newCacheConfigurationBuilder(Object.class, Object.class, res);
        return newCacheConfigurationBuilder.build();
    }
}
