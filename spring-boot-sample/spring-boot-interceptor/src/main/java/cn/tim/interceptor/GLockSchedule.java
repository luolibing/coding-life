package cn.tim.interceptor;

import cn.tim.interceptor.annotation.GLock;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * User: luolibing
 * Date: 2017/8/8 13:44
 */
@Component
public class GLockSchedule {

    @Autowired
    private IService iService;

    @Autowired
    private AopService aopService;

    @GLock(key = "'GLockSchedule1'")
    @Scheduled(fixedRate = 5000)
    public void scheduleTask() {
        System.out.println("timer");
        iService.sayHello();
    }

    @Scheduled(fixedRate = 5000)
    public void scheduleTask1() {
        aopService.sayGood();
        ((GLockSchedule) AopContext.currentProxy()).execute();
    }

    public void execute() {
        System.out.println("execute");
    }
}
