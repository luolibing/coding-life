package cn.tim.redis.client;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by LuoLiBing on 15/11/24.
 */
@Component
public class ZSetRedis {

    @Resource(name = "redisTemplate")
    private ZSetOperations<String, Map<String, Object>> zSetOperations;


    @Cacheable(value = "baseSet")
    public Set<Map<String, Object>> list() {
        return zSetOperations.range("baseSet", 0, 10);
    }

    @Caching(evict = {
            @CacheEvict(value="baseSet", allEntries=true),
            @CacheEvict(value="allBaseSet", allEntries=true)
    })
    /*@CacheEvict(value = {"baseSet", "allBaseSet"}, key = "#id", allEntries = true, beforeInvocation = false)*/
    public Map<String, Object> add(Long id, Map<String, Object> beanMap) {
        return beanMap;
    }

    @CacheEvict(value = {"baseSet", "allBaseSet"}, key = "#id", allEntries = true, beforeInvocation = false)
    public void delete(Long id) {

    }

    @Cacheable(value = "baseSet", key = "#id")
    public Map<String, Object> get(Long id) {
        return null;
    }


    private Set<Map<String, Object>> init() {
        Set<Map<String, Object>> set = new HashSet<>();
        Map<String, Object> beanMap = new HashMap<>();
        beanMap.put("id", 1L);
        beanMap.put("name", "aaaaaaaaaa");
        set.add(beanMap);
        beanMap = new HashMap<>();
        beanMap.put("id", 2L);
        beanMap.put("name", "bbbbbbbbbb");
        set.add(beanMap);
        beanMap = new HashMap<>();
        beanMap.put("id", 3L);
        beanMap.put("name", "cccccccccc");
        set.add(beanMap);

        return set;
    }
}
