package com.tim.dubbo.sample.cast;

import java.lang.reflect.Proxy;

public class CastSample {

    public static void main(String[] args) {
        A a = new A();
        C aProxy = (C) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{C.class, B.class}, new AInvokeHandler(a));
        // A没有实现B接口，但是还是可以将其通过动态代理的方式，将其强转为B接口，只是在调用B接口方法的的时候会被A的代理类给拦截住，这个地方需要特殊处理，来实现B接口方法的调用，
        // 不然会报IllegalArgumentException: object is not an instance of declaring class错误，因为检测到调用方法的主体并不是B接口的实例
        aProxy.sayHello();
        B b = (B)aProxy;
        b.sayHi();
        System.out.println(b);
    }
}
