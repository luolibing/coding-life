package com.tim.dubbo.sample.cast;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class AInvokeHandler implements InvocationHandler {

    private A a;

    public AInvokeHandler(A a) {
        this.a = a;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("AInvokeHandle invoke method = " + method.getName());
        Class<?> clazz = method.getDeclaringClass();
        if(clazz.isAssignableFrom(B.class) && method.getName().equals("sayHi")) {
            System.out.println("SayHi B");
            return null;
        }
        return method.invoke(a, args);
    }
}
