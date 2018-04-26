package cn.tim.thinking.innerclass;

import org.junit.Test;

/**
 * Created by LuoLiBing on 16/12/20.
 * 为什么需要内部类
 * 使用内部类实现接口或者继承自类与外部类实现或继承有什么区别:
 * 外部类不是总能享用到接口带来的方便, 有时需要用到接口的实现.
 * 内部类最吸引人的原因:  每个内部类都能独立地继承自一个实现, 所以无论外围类是否已经继承了某个实现, 对于内部类都没有影响.
 * 如果没有内部类提供的,可以继承多个具体的或抽象的类的能力,一些设计与编程问题就很难解决. 内部类使得多重继承的解决方案变得完整.
 */
public class WhyInnerClassDemo {


    /**
     * 一个类实现多个接口, 普通类和内部类实现的不同
     *
     */
    interface A {}

    interface B {}

    // 普通类实现了A, B接口
    class X implements A, B {}

    // Y 本身只实现了一个A接口, 但是因为Y可以返回实现B的内部类实现, 所以Y等同于实现了A, B接口
    class Y implements A {
        B makeB() {
            // 匿名内部类
            return new B() {};
        }
    }

    static class MultiInterfaces {
        static void takeA(A a) {}
        static void takeB(B b) {}
    }

    @Test
    public void multi1() {
        X x = new X();
        Y y = new Y();
        MultiInterfaces.takeA(x);
        MultiInterfaces.takeB(x);
        MultiInterfaces.takeA(y);
        // Y并没有实现B接口, 所以无法直接传入y, 而是使用y的makeB方法, 返回一个B接口的内部类实现
        MultiInterfaces.takeB(y.makeB());
    }


    /**
     * 如果拥有的是抽象类或具体的类, 而不是接口, 那么久只能使用内部类才能实现多重继承
     */
    class D {}
    abstract class E {}
    class Z extends D/**, E 无法多重继承D, E**/ {

        // 通过一个返回继承自E的匿名内部类来实现类似多重继承
        E makeE() {
            return new E() {};
        }
    }

    static class MultiImplementation {
        static void takesD(D d) {}
        static void takesE(E e) {}
    }

    @Test
    public void multi2() {
        Z x = new Z();
        MultiImplementation.takesD(x);
        MultiImplementation.takesE(x.makeE());
    }


    /**
     * 使用内部类还可以获得其他一些特性
     * 1 内部类可以有多个实例, 每个实例都有自己的状态信息, 并且与其外围类对象的信息相互独立.
     * 2 在单个外围类中, 可以让多个内部类以不同的方式实现同一个接口, 或继承同一个类.
     * 3 创建内部类对象的时刻并不依赖于外围类对象的创建
     * 4 内部类并没有令人迷惑的"is-a"关系, 它就是一个独立的实体
     */


    interface Selector {
        boolean end();
        Object current();
        void next();
    }


    // 迭代器模式:  例如sequence是一个ArrayList, SequenceSelector就是ArrayList中的Iter迭代器实现
    class Sequence {
        private Object[] items;

        private int next = 0;

        public Sequence(int size) {
            items = new Object[size];
        }

        public void add(Object x) {
            if(next < items.length) {
                items[next++] = x;
            }
        }

        // 内部类直接引用外部类的元素items
        private class SequenceSelector implements Selector {

            private int i = 0;

            @Override
            public boolean end() {
                return i == items.length;
            }

            @Override
            public Object current() {
                return items[i];
            }

            @Override
            public void next() {
                if(i < items.length) {
                    i++;
                }
            }

            public Sequence sequence() {
                return Sequence.this;
            }
        }

        public Selector reverseSelector() {
            return new Selector() {
                private int i = items.length - 1;

                @Override
                public boolean end() {
                    return i < 0;
                }

                @Override
                public Object current() {
                    return items[i];
                }

                @Override
                public void next() {
                    i--;
                }
            };
        }

        public Selector selector() {
            return new SequenceSelector();
        }
    }

    @Test
    public void reverseSelector() {
        Sequence sequence = new Sequence(10);
        for(int i = 0; i < 10; i++) {
            sequence.add("item" + i);
        }
        forEach(sequence.selector());
        System.out.println();
        forEach(sequence.reverseSelector());
    }

    static void forEach(Selector selector) {
        while (!selector.end()) {
            System.out.println(selector.current());
            selector.next();
        }
    }

    @Test
    public void test() {
        Integer i = 10000;
        Integer j = 10000;
        System.out.println(i == j);
    }
}
