package cn.tim.redis.client;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;

/**
 * Created by LuoLiBing on 15/11/25.
 */
@Component
public class OperationsTest {

    public final static String CACHE_KEY = "base";

    public final static String HASH_KEY = "hashbase";

    public final static String RELATION = "relation";

    @Resource(name = "redisTemplate")
    private SetOperations<String, Map<String, String>> setOperations;

    @Resource(name = "redisTemplate")
    private HashOperations hashOperations;

    public Set list() {
        final Map entries = hashOperations.entries(RELATION);
        System.out.println(entries);
        return setOperations.members(CACHE_KEY);
    }

    public void add(String id, Map beanMap) {
        hashOperations.put(HASH_KEY, id, beanMap);
        setOperations.add(CACHE_KEY, beanMap);
    }

    public void delete(String id) {
        hashOperations.delete(HASH_KEY, id);
        setOperations.remove(CACHE_KEY, id);
    }

    public Map<String, Object> get(String id) {
        return null;
    }

}
