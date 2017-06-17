package cn.tim.twocache.message

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component
/**
 * Created by LuoLiBing on 15/11/26.
 * 消息发布方
 */
@Component
class CacheMessagePublish {


    @Autowired
    private StringRedisTemplate redisTemplate;


    /**
     * 消息通道名
     */
    public final static String CHANNEL_NAME = "onekey";


    public void publishMessage(String channel, String message) {
        redisTemplate.convertAndSend(channel, message)
    }
}
