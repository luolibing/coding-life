package com.tim.springboot.source.config;

/**
 * User: luolibing
 * Date: 2017/9/1 16:07
 */
public class B {

    private A a;

    public B(A a) {
        this.a = a;
    }

    public void say() {
        System.out.println("say B");
        a.say();
    }
}
