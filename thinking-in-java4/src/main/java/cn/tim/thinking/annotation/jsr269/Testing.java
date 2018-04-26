package cn.tim.thinking.annotation.jsr269;

import javax.annotation.PostConstruct;

/**
 * Created by LuoLiBing on 17/1/14.
 */
public class Testing {

    @ToBeTested(group = "A")
    public void m1() {
        System.out.println("Testing.m1()");
    }

    @ToBeTested(group = "B", owner = "QQ")
    public void m2() {
        System.out.println("Testing.m2()");
    }

    @PostConstruct
    public void m3() {
        System.out.println("Testing.m3()");
    }
}
