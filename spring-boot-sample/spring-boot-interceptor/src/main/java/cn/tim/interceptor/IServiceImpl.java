package cn.tim.interceptor;

import cn.tim.interceptor.annotation.MyLog;
import org.springframework.stereotype.Component;

/**
 * User: luolibing
 * Date: 2017/8/3 14:10
 */
@Component
public class IServiceImpl implements IService {

    @Handler
    @MyLog
    public void sayHello() {
        System.out.println("service hello");
    }

    @MyLog
    public void sayGood() {
        this.sayHello();
//        ((IService)AopContext.currentProxy()).sayHello();
        System.out.println("sayGood");
    }
}
