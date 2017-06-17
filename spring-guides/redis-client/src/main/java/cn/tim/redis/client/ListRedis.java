package cn.tim.redis.client;

import java.util.Map;
import java.util.Set;

/**
 * Created by LuoLiBing on 15/11/23.
 */
public interface ListRedis {

    Set<Map<String, Object>> list();

    Map<String, Object> add(Long id, Map<String, Object> beanMap);

    void delete(Long id);

    Map<String, Object> get(Long id);
}
