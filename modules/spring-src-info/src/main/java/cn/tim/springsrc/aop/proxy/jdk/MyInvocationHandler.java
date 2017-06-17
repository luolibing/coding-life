package cn.tim.springsrc.aop.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by luolibing on 2017/6/11.
 */
public class MyInvocationHandler implements InvocationHandler {

    private Object target;

    public MyInvocationHandler(Object target) {
        super();
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("proxy class = " + proxy.getClass());
        System.out.println("--- proxy before ---");
        try {
            // 调用目标类
            return method.invoke(target, args);
        } finally {
            System.out.println("--- proxy after ---");
        }
    }

    public Object getProxy() {
        return Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                target.getClass().getInterfaces(),
                this);
    }
}
