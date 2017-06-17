package cn.tim.listener.publisher.listener;

import cn.tim.listener.publisher.event.CreateEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Created by luolibing on 2017/4/12.
 */
@Component
public class CreateMessageListener {

    @EventListener(CreateEvent.class)
    public void onApplicationEvent(CreateEvent event) {
        System.out.println("CreateMessageListener threadId : " + Thread.currentThread().getId());
        System.out.println("saveMessage [" + event.message + "]" );
    }
}
