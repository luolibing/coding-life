package cn.tim.thinking.clazz14.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Objects;

/**
 * Created by LuoLiBing on 16/10/8.
 */
public class SimpleMethodSelectorProxyDemo {

    static class SimpleMethodSelector implements InvocationHandler {

        private Object realObject;

        public SimpleMethodSelector(Object realObject) {
            this.realObject = realObject;
        }

        // 动态代理方法,可以根据方法名或者参数决定是否需要改变其执行行为
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if(Objects.equals(method.getName(), "interesting")) {
                System.out.println("Proxy detected the interesting method");
            }
            return method.invoke(realObject, args);
        }
    }

    interface SomeMethods {
        void boring1();
        void boring2();
        void interesting();
        void boring3();
    }

    static class Implemention implements SomeMethods {

        @Override
        public void boring1() {
            System.out.println("boring1");
        }

        @Override
        public void boring2() {
            System.out.println("boring2");
        }

        @Override
        public void interesting() {
            System.out.println("interesting");
        }

        @Override
        public void boring3() {
            System.out.println("boring3");
        }
    }

    public static void main(String[] args) {
        Implemention implemention = new Implemention();
        SomeMethods someMethods = (SomeMethods) Proxy.newProxyInstance(
                SomeMethods.class.getClassLoader(),
                new Class[] {SomeMethods.class},
                new SimpleMethodSelector(implemention)
        );
        someMethods.boring1();
        someMethods.boring2();
        someMethods.interesting();
        someMethods.boring3();
    }
}
