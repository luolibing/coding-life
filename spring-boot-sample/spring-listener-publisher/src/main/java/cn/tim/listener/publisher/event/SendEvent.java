package cn.tim.listener.publisher.event;

import cn.tim.listener.publisher.entity.Message;
import cn.tim.listener.publisher.entity.SendChannel;
import org.springframework.context.ApplicationEvent;

/**
 * Created by luolibing on 2017/4/12.
 */
public class SendEvent extends ApplicationEvent {
    public final Message message;

    public final SendChannel sendChannel;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public SendEvent(Message source, SendChannel sendChannel) {
        super(source);
        this.message = source;
        this.sendChannel = sendChannel;
    }
}
