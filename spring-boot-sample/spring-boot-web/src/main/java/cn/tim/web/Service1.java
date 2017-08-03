package cn.tim.web;

import org.springframework.stereotype.Component;

/**
 * User: luolibing
 * Date: 2017/8/1 11:16
 */
@Component
public class Service1 {
    public void sayGood() {
        System.out.println("good!");
    }

    public void sayGoodBye() {
        System.out.println("goodBye!");
    }
}
