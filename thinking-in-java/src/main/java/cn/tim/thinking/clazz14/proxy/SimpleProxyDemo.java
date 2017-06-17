package cn.tim.thinking.clazz14.proxy;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by LuoLiBing on 16/10/8.
 * 简单代理
 */
public class SimpleProxyDemo {

    interface Interface {
        void doSomething();
        void somethingElse(String arg);
    }

    // 真实对象
    static class RealObject implements Interface {

        @Override
        public void doSomething() {
            System.out.println("doSomething");
        }

        @Override
        public void somethingElse(String arg) {
            System.out.println("somethingElse " + arg);
        }
    }

    /**
     * 代理对象, 真正工作的是真正对象
     * 代理对象和被代理对象实现同一接口
     * 代理对象持有被带代理对象实体
     */
    static class SimpleProxy implements Interface {

        private Interface proxied;

        private AtomicInteger counter = new AtomicInteger(0);

        public SimpleProxy(Interface proxied) {
            this.proxied = proxied;
        }

        @Override
        public void doSomething() {
            System.out.println("SimpleProxy doSomething");
            proxied.doSomething();
            System.out.println("count= " + counter.incrementAndGet());
        }

        @Override
        public void somethingElse(String arg) {
            System.out.println("SimpleProxy somethingElse " + arg);
            proxied.somethingElse(arg);
            System.out.println("count= " + counter.incrementAndGet());
        }
    }

    public static void consumer(Interface iface) {
        iface.doSomething();
        iface.somethingElse("bonobo");
    }

    public static void main(String[] args) {
        consumer(new RealObject());
        System.out.println();
        consumer(new SimpleProxy(new RealObject()));
    }
}
