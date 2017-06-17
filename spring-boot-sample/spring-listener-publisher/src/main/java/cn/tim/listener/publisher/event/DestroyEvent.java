package cn.tim.listener.publisher.event;

import cn.tim.listener.publisher.entity.Message;
import org.springframework.context.ApplicationEvent;

/**
 * Created by luolibing on 2017/4/12.
 */
public class DestroyEvent extends ApplicationEvent {
    public final Message message;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public DestroyEvent(Message source) {
        super(source);
        this.message = source;
    }
}
