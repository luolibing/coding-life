package cn.tim.twocache.message

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component
/**
 * Created by LuoLiBing on 15/11/27.
 * redis消息发布监听器
 */
@Component
class RedisMessageListener implements ApplicationListener<RedisMessageEvent> {


    @Autowired
    CacheMessagePublish publish


    @Override
    void onApplicationEvent(RedisMessageEvent event) {
        publish.publishMessage(event.channel, event.message)
    }
}
