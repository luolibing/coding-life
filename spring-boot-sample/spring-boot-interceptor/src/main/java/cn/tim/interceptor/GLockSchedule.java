package cn.tim.interceptor;

import cn.tim.interceptor.annotation.GLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * User: luolibing
 * Date: 2017/8/8 13:44
 */
@Component
public class GLockSchedule {

    @GLock(key = "'GLockSchedule1'")
    @Scheduled(fixedRate = 5000)
    public void scheduleTask() {
        System.out.println("timer");
    }
}
