package cn.tim.listener.publisher.listener;

import cn.tim.listener.publisher.event.CreateEvent;
import cn.tim.listener.publisher.event.DestroyEvent;
import cn.tim.listener.publisher.event.SendEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;

import java.util.Random;

/**
 * Created by luolibing on 2017/4/12.
 * 捣乱的猴子
 */
//@Component
public class MonkeyListener {

    private Random rand = new Random(47);

    @EventListener(value = {CreateEvent.class, SendEvent.class, DestroyEvent.class})
    public void onApplicationEvent(ApplicationEvent event) {
        if(rand.nextBoolean()) {
            throw new RuntimeException("猴子成功拔掉网线");
        }
    }
}
