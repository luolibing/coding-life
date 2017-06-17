package cn.tim.cache.cache;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.stereotype.Controller;

import java.lang.reflect.Method;

/**
 * Created by LuoLiBing on 15/12/9.
 * key提供
 */
@Controller
public class MyKeyGenerator implements KeyGenerator {

    @Override
    public Object generate(Object target, Method method, Object... params) {
        return null;
    }


    /**
     * Generate a key based on the specified parameters.
     */
    public static Object generateKey(Object... params) {
        if (params.length == 0) {
            return SimpleKey.EMPTY;
        }
        if (params.length == 1) {
            Object param = params[0];
            if (param != null && !param.getClass().isArray()) {
                return param;
            }
        }
        return new SimpleKey(params);
    }

}
