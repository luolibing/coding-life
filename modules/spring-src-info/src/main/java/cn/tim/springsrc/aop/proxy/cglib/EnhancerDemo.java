package cn.tim.springsrc.aop.proxy.cglib;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by luolibing on 2017/6/12.
 */
public class EnhancerDemo {

    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(EnhancerDemo.class);
        enhancer.setCallback(new MethodInterceptorImpl());

        EnhancerDemo enhancerDemo = (EnhancerDemo) enhancer.create();
        enhancerDemo.test();
        System.out.println(enhancerDemo);
    }


    public void test() {
        System.out.println("EnhancerDemo test()");
    }

    private static class MethodInterceptorImpl implements MethodInterceptor {

        @Override
        public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
            System.out.println("before invoke " + method);
            Object result = methodProxy.invokeSuper(o, args);
            System.out.println("after invoke " + method);
            return result;
        }
    }
}
