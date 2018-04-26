package cn.tim.web.cycle;

import cn.tim.aspect.api.annotation.MyLog;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * User: luolibing
 * Date: 2017/11/2 13:59
 */
@Component
public class B1 {

    private A1 a1;

    public B1(@Lazy A1 a1) {
        this.a1 = a1;
    }

    @MyLog
    public void sayHello() {
        a1.sayHello();
    }
}
