package cn.tim.web.cycle;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * User: luolibing
 * Date: 2017/11/2 13:59
 */
@Component
public class A1 {

    private B1 b1;

    public A1(@Lazy B1 b1) {
        this.b1 = b1;
    }

    public void sayHello() {
        System.out.println("a1 say Hello");
    }
}
