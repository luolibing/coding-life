package cn.tim.thinking.clazz14.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by LuoLiBing on 16/10/8.
 * 动态代理
 * 动态创建代理并动态地处理对所代理方法的调用.在动态代理商所做的所有调用都会被重定向到单一的调用处理器上,它的工作是揭示调用的类型并确定相应的对策
 */
public class SimpleDynamicProxyDemo {

    /**
     * 业务处理
     */
    static class DynamicProxyHandler implements InvocationHandler {

        private AtomicInteger counter = new AtomicInteger(0);

        private Object proxied;

        public DynamicProxyHandler(Object proxied) {
            this.proxied = proxied;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("proxy: " + proxy.getClass()
                    + ", method: " + method + ",args: "
                    + Arrays.toString(args));
            // 这个地方不能直接使用代理对象,不然会导致死循环
            System.out.println("count= " + counter.incrementAndGet());
            Object result = method.invoke(proxied, args);
            System.out.println("return= " + result);
            return result;
        }
    }

    public static void consumer(SimpleProxyDemo.Interface iface) {
        iface.doSomething();
        iface.somethingElse("bonobo");
    }

    public static void main(String[] args) {
        SimpleProxyDemo.RealObject realObject = new SimpleProxyDemo.RealObject();
        consumer(realObject);
        System.out.println();
        // 动态代理, 使用Proxy.newProxyInstance()方法创建出代理对象
        SimpleProxyDemo.Interface proxy = (SimpleProxyDemo.Interface) Proxy.newProxyInstance(
                SimpleProxyDemo.Interface.class.getClassLoader(),
                new Class[] {SimpleProxyDemo.Interface.class},
                new DynamicProxyHandler(realObject));
        consumer(proxy);
    }
}
