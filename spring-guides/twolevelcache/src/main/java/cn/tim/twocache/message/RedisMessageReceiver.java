package cn.tim.twocache.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;

/**
 * Created by LuoLiBing on 15/11/26.
 * 订阅消息
 */
@Component
public class RedisMessageReceiver implements Runnable {


    private final static Logger logger = LoggerFactory.getLogger(RedisMessageReceiver.class);


    private ObjectMapper objectMapper = new ObjectMapper();


    final static TransferQueue<String> updateQueue = new LinkedTransferQueue<>();


    public RedisMessageReceiver() {
        new Thread(this).start();
    }


    @Autowired
    CacheManager cacheManager;


    /**
     * 订阅消息
     * @param message
     */
    public void receiveMessage(String message) {
        logger.info("收到消息<" + message + ">");
        try {
            Map messageMap = parseMessage(message);
            handleMessage(messageMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 解析消息
     * @param message
     * @return
     */
    private Map parseMessage(String message) throws IOException {
        return objectMapper.readValue(message, Map.class);
    }


    /**
     * 处理消息 "{cacheKey:$cacheKey, hashKey: $hashKey, method:${method?.name()}}"
     * @param messageMap
     */
    private void handleMessage(Map messageMap) throws Exception {
        if(messageMap.get("method") != null && messageMap.get("cacheKey") != null) {
            String method = messageMap.get("method").toString();
            String cacheKey =messageMap.get("cacheKey").toString();
            String hashKey = messageMap.get("hashKey").toString();
            switch (method) {
                case "ADD_UPDATE": updateCache(hashKey, cacheKey); break;
            }
        }
    }


    /**
     * 更新一级缓存
     * @param hashKey
     * @param cacheKey
     * @throws Exception
     */
    private void updateCache(String hashKey,String cacheKey) throws Exception {
        // 现阶段只有一个缓存cacheKey
        updateQueue.add(hashKey);
    }


    @Override
    public void run() {
        while (true) {
            try {
                String key = updateQueue.take();
                // 如果此时存在多个更新
                updateQueue.remove(key);
                // executor.submit(new RedisMessageCallable(key));
                //extendInfo.evict(key);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 线程池
     */
    /*ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 5, 1,
                                      TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());*/


    /**
     * 消息处理器
     */
    /*class RedisMessageCallable implements Callable<String> {

        String key;
        RedisMessageCallable(String key) {
            this.key = key;
        }

        @Override
        public String call() throws Exception {
            // 因为对比表依赖于总表，所以如果更新，最好是两者都更新
            if (Base.CACHE_KEY.equals(key)) {
                base.updateBase();
                base.initBase();
                extend.updateExtend();
                extend.initExtend();
            } else if (Extend.CACHE_KEY.equals(key)) {
                extend.updateExtend();
                extend.initExtend();
            }
            return key;
        }
    }*/
}
