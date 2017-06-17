package cn.tim.redis.client;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by LuoLiBing on 15/11/23.
 */
@Component
public class SimpleListRedis implements ListRedis {

//    @Autowired
//    private RedisTemplate redisTemplate;

    @Cacheable(value = "allBase")
    @Override
    public Set<Map<String, Object>> list() {
        return init();
    }

//    @Caching(evict = {
//            @CacheEvict(value="baseSet", allEntries=true),
//            @CacheEvict(value="allBaseSet", allEntries=false)
//    })
    /*@CacheEvict(value = {"baseSet", "allBaseSet"}, key = "#id", allEntries = true, beforeInvocation = false)*/
    @Override
    @CacheEvict(value = {"base", "allBase"}, key = "#id", allEntries = false)
    public Map<String, Object> add(Long id, Map<String, Object> beanMap) {
        return beanMap;
    }

    @CacheEvict(value = {"base", "allBase"}, key = "#id", allEntries = false, beforeInvocation = false)
    @Override
    public void delete(Long id) {

    }

    @Cacheable(value = "base", key = "#id")
    @Override
    public Map<String, Object> get(Long id) {
        return null;
    }


    private Set<Map<String, Object>> init() {
        Set<Map<String, Object>> set = new LinkedHashSet<>();
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
