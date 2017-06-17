package cn.tim.twocache.entity;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Component
public class ExtendInfo {

    private final static String apiUrl = "http://localhost:16384/getExtend/";

    @Resource(name = "ehCacheCacheManager")
    private EhCacheCacheManager ehCacheCacheManager;


    @Resource(name = "redisCacheManager")
    private RedisCacheManager redisCacheManager;


    @Caching(
            cacheable = {
                    @Cacheable(value = "firstCache", key = "#word", cacheManager = "ehCacheCacheManager"),
                    @Cacheable(value = "extendInfo", key = "#word", cacheManager = "redisCacheManager")
            }
    )
    public Map<String, List<Map<String, String>>> get(String word) {
        RestTemplate template = new RestTemplate();
        final ResponseEntity<Map> entity = template.getForEntity(apiUrl + word, Map.class);
        return entity.getBody();
    }


    /**
     * 更新一级缓存,更新方式应该有更好的方式
     * @param word
     */
    //@CacheEvict(value = {"firstCache"}, key = "#word", allEntries = false)
    public void evict(String word) {
        Map extendInfo = redisCacheManager.getCache("extendInfo").get(word, Map.class);
        if(extendInfo != null && !extendInfo.isEmpty()) {
            ehCacheCacheManager.getCache("firstCache").put(word, extendInfo);
        } else {
            ehCacheCacheManager.getCache("firstCache").evict(word);
        }
    }


    @CacheEvict(value = {"firstCache", "extendInfo"}, key = "#word", allEntries = false)
    public void evictFirstAndSecondCache(String word) {

    }


    @CacheEvict(value = {"firstCache", "extendInfo"}, allEntries = true)
    public void clear() {
    }

}
