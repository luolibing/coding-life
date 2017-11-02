package cn.tim.web.cycle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * User: luolibing
 * Date: 2017/11/2 14:06
 */
@Component
public class A2 {

    @Autowired
    private B2 b2;

    @PostConstruct
    public void init() {
        this.b2.setA2(this);
    }

    public void sayHello() {
        System.out.println();
    }
}
