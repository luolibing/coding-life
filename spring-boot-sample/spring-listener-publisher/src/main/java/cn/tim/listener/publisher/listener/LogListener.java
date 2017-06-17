package cn.tim.listener.publisher.listener;

import cn.tim.listener.publisher.event.CreateEvent;
import cn.tim.listener.publisher.event.DestroyEvent;
import cn.tim.listener.publisher.event.SendEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Created by luolibing on 2017/4/12.
 */
@Slf4j
@Component
public class LogListener {

    @EventListener(value = {CreateEvent.class, SendEvent.class, DestroyEvent.class})
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println("LogListener threadId : " + Thread.currentThread().getId());
        if(event instanceof CreateEvent) {
            log.info("Message create event!! message = {}", ((CreateEvent)event).message);
        } else if(event instanceof SendEvent) {
            log.info("Message send event!! message = {}, sendChannel = {}",
                    ((SendEvent)event).message, ((SendEvent)event).sendChannel.desc);
        } else if(event instanceof DestroyEvent) {
            log.info("Message destroy event!! message = {}", ((DestroyEvent)event).message);
        }
    }
}
