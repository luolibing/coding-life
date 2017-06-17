package cn.tim.repeat.config;

import com.google.common.cache.CacheBuilder;
import org.springframework.cache.CacheManager;
import org.springframework.cache.guava.GuavaCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * Created by LuoLiBing on 16/10/24.
 */
@Configuration
public class CacheManageConfiguration {

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        GuavaCache tokenExpirableCache =
                new GuavaCache("tokens",
                        CacheBuilder.newBuilder().expireAfterAccess(10, TimeUnit.MINUTES).build());
        cacheManager.setCaches(Arrays.asList(tokenExpirableCache));
        return cacheManager;
    }
}
