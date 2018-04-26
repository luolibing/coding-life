package cn.tim.thinking.innerclass;

import org.junit.Test;

/**
 * Created by LuoLiBing on 16/12/20.
 * 闭包
 * 闭包是一个可调用的对象, 它记录了一些信息, 这些信息来自于创建它的作用域.
 * 通过这个定义, 可以看出内部类是面向对象的闭包,因为它不仅包含外围类对象(创建内部类的作用域)的信息,
 * 还自动拥有一个指向此外围类对象的引用, 在次作用域内,内部类有权操作所有的成员, 包括private成员.
 *
 * 回调
 * java没有类似指针的机制, 以允许回调callback. 但是java并没有指针的概念.
 */
public class ClosureAndCallbackDemo {

    /**
     * 通过内部类提供闭包的功能
     */
    interface Incrementable {
        void increment();
    }

    // 普通的实现类
    class Callee1 implements Incrementable {
        private int i = 0;

        @Override
        public void increment() {
            i++;
            System.out.println(i);
        }
    }

    static class MyIncrement {
        public void increment() {
            System.out.println("Other operation");
        }

        static void f(MyIncrement mi) {
            mi.increment();
        }
    }


    // 继承自其他类, 同时内部类实现一个接口, 返回这个内部类的引用. Callee2继承自MyIncrement已经包含了一个increment方法, 所以不能再实现Incrementable接口,这样就不能定义自己的increment()方法了
    // 通过一个内部闭包Closure回调外部类Callee2, 可以在任意时刻捕获回调引用, 然后再以后的某个时刻, Caller对象可以使用此引用回调Caller类
    class Callee2 extends MyIncrement {
        private int i = 0;

        @Override
        public void increment() {
            super.increment();
            i++;
            System.out.println(i);
        }

        // Closure类似于一个调用钩子(hook), 通过闭包closure可以调用Callee2类
        private class Closure implements Incrementable {

            @Override
            public void increment() {
                Callee2.this.increment();
            }
        }

        Incrementable getCallbackReference() {
            return new Closure();
        }
    }

    class Caller {
        private Incrementable callbackReference;

        // 需要一个Incrementable作为参数
        Caller(Incrementable c) {
            this.callbackReference = c;
        }

        void go() {
            callbackReference.increment();
        }
    }

    @Test
    public void callbacks() {
        // 正常接口实现
        Callee1 c1 = new Callee1();
        Callee2 c2 = new Callee2();
        MyIncrement.f(c2);

        Caller caller1 = new Caller(c1);
        Caller caller2 = new Caller(c2.getCallbackReference());
        caller1.go();
        caller1.go();
        caller2.go();
        caller2.go();
    }
}
