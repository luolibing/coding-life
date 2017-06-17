package cn.tim.twocache.message

import org.springframework.context.ApplicationEvent

/**
 * Created by LuoLiBing on 15/11/27.
 */
class RedisMessageEvent extends ApplicationEvent {

    String channel
    String message

    RedisMessageEvent(String channel, String message) {
        super(message)
        this.message = message
        this.channel = channel
    }
}
