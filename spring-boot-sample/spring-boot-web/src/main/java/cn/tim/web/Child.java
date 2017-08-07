package cn.tim.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * User: luolibing
 * Date: 2017/8/1 11:16
 */
@Component
public class Child extends Parent {

    @Autowired
    private Service2 service2;

    @Override
    public void doHandle() {
        System.out.println("do handle");
        service2.say2();
    }
}
