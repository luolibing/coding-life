package socket;

import com.google.common.cache.Cache;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * Created by LuoLiBing on 15/11/24.
 */
public class Main {
    public static void main(String[] args) throws ExecutionException {
        Cache<String, Object> cache =
                com.google.common.cache.CacheBuilder.newBuilder().build();

        cache.get("user#"+1+":username", new Callable<Object>() {
            public Object call() throws Exception {
                return "tim";
            }
        });

    }
}
