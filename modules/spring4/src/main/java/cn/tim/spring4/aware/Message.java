package cn.tim.spring4.aware;

import org.springframework.context.ApplicationEvent;

/**
 * Created by LuoLiBing on 16/6/17.
 */
public class Message extends ApplicationEvent {

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public Message(Object source) {
        super(source);
    }


}
