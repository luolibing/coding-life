package cn.tim.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * User: luolibing
 * Date: 2017/8/1 11:16
 */
@Component
public abstract class Parent {

    @Autowired
    private Service1 service1;

    public final void handle() {
        service1.sayGood();
        doHandle();
        service1.sayGoodBye();
    }

    public abstract void doHandle();
}
