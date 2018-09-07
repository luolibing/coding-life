package com.tim.dubbo.sample.cast;

public interface B {

    default void sayHi() {
        System.out.println("SayHi B");
    }
}
