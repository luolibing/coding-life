package cn.tim.quartz.database.job.execute;

import org.springframework.stereotype.Component;

/**
 * User: luolibing
 * Date: 2017/9/15 15:12
 */
@Component
public class MyTask {

    public void task1() {
        System.out.println("execute task1");
    }
}
