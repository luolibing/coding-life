package com.tim.javassist.simple1;

/**
 * Created by luolibing on 2019/4/1.
 */
public class B {

    public void sayHello() {
        System.out.println("B sayHello");
    }

    public static void main(String[] args) {
        System.out.println("run main b");
    }

    public void setName(String name) {
        System.out.println("set name " + name);
    }
}
