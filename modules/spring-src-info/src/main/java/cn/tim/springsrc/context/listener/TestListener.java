package cn.tim.springsrc.context.listener;

import org.springframework.context.ApplicationListener;

/**
 * Created by luolibing on 2017/6/6.
 */
public class TestListener implements ApplicationListener<TestEvent> {

    @Override
    public void onApplicationEvent(TestEvent event) {
        event.print();
    }
}
