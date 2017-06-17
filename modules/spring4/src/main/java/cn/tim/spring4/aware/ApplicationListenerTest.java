package cn.tim.spring4.aware;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Created by LuoLiBing on 16/6/17.
 */
@Component
public class ApplicationListenerTest implements ApplicationListener<Message> {

    @Override
    public void onApplicationEvent(Message event) {
        System.out.println("handle --- > " + event.getSource());
    }
}
