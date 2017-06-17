package cn.tim.listener.publisher.entity;

import cn.tim.listener.publisher.event.CreateEvent;
import cn.tim.listener.publisher.event.DestroyEvent;
import cn.tim.listener.publisher.event.SendEvent;
import lombok.Data;
import org.springframework.context.ApplicationEventPublisher;

/**
 * Created by luolibing on 2017/4/12.
 */
@Data
public class Message {
    private static long counter= 0;
    private Long id = counter++;
    private String title = "message";
    private String content = "message content";

    private ApplicationEventPublisher publisher;

    public Message(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public Message() {

    }

    public Message save() {
        System.out.println("保存");
        publisher.publishEvent(new CreateEvent(this));
        return this;
    }

    public Message send() {
        System.out.println("发送");
        publisher.publishEvent(new SendEvent(this, SendChannel.rand()));
        return this;
    }

    public Message destroy() {
        System.out.println("销毁");
        publisher.publishEvent(new DestroyEvent(this));
        return this;
    }
}
