package cn.tim.springsrc.context.listener;

import org.springframework.context.ApplicationEvent;

/**
 * Created by luolibing on 2017/6/6.
 */
public class TestEvent extends ApplicationEvent {

    private String msg;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public TestEvent(String source) {
        super(source);
        this.msg = source;
    }

    public void print() {
        System.out.println("msg=" + msg);
    }

}
