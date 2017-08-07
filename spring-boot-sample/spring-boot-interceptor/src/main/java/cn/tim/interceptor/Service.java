package cn.tim.interceptor;

import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Component;

/**
 * User: luolibing
 * Date: 2017/8/3 14:10
 */
@Component
public class Service {

    @Handler
    protected void sayHello() {
        System.out.println("service hello");
    }

    public void sayGood() {
        ((Service)AopContext.currentProxy()).sayHello();
        System.out.println("sayGood");
    }
}
