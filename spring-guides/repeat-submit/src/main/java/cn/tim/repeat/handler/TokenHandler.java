package cn.tim.repeat.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Created by LuoLiBing on 16/10/24.
 */
@Component
public class TokenHandler {

    private Cache cache;

    @Autowired
    public TokenHandler(CacheManager cacheManager) {
        cache = cacheManager.getCache("tokens");
    }

    public String generate() {
        String token = UUID.randomUUID().toString();
        cache.put(token, true);
        return token;
    }

    public void evict(String token) {
        cache.evict(token);
    }

    public Object get(String token) {
        return cache.get(token);
    }

}
