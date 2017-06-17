package cn.tim.thinking.innerclass;

import org.junit.Test;

/**
 * Created by LuoLiBing on 16/12/18.
 * 内部类
 * 可以将一个类的定义放在另一个类的定义内部, 这就是内部类
 */
public class InnerClassDemo {

    class Parcel1 {
        class Contents {
            private int i = 11;

            public int value() {
                return i;
            }
        }

        class Destination {
            private String label;

            Destination(String whereTo) {
                this.label = whereTo;
            }

            String readLabel() {
                return label;
            }
        }

        public void ship(String dest) {
            Contents c = new Contents();
            Destination d = new Destination(dest);
            System.out.println(d.readLabel());
        }
    }


    /**
     * 内部类的使用方式:
     * 把类的定义置于外围类的里面, 在这里实际的区别只是内部类的名字是嵌套在Parcel1里面的.
     */
    @Test
    public void parcel1() {
        Parcel1 p = new Parcel1();
        p.ship("Tasmania");
    }


    static class Parcel2 {
        static class Contents {
            private int i = 11;

            public int value() {
                return i;
            }
        }

        class Destination {
            private String label;

            Destination(String whereTo) {
                this.label = whereTo;
            }

            String readLabel() {
                return label;
            }
        }

        // 外部类返回内部类方法
        public Destination to(String s) {
            return new Destination(s);
        }

        public static Contents createContents() {
            return new Contents();
        }

        public Contents contents() {
            return new Contents();
        }

        public void ship(String dest) {
            Contents c = new Contents();
            Destination d = new Destination(dest);
            System.out.println(d.readLabel());
        }
    }


    /**
     * 内部类的第二种典型使用方式:
     * 外部类将有一个方法, 该方法返回一个指向内部类的引用, 这个时候需要使用OutClassName.InnerClassName来指明这个对象的类型.
     */
    @Test
    public void parcel2() {
        Parcel2 p = new Parcel2();
        p.ship("Tasmania");

        Parcel2 p2 = new Parcel2();
        Parcel2.Contents contents = p2.contents();
        Parcel2.Destination borneo = p2.to("Borneo");
        Parcel2.Contents contents1 = Parcel2.createContents();
    }

    class Outer {

        private String data;

        public Outer(String d) {
            this.data = d;
        }

        class Inner {
            @Override
            public String toString() {
                return data;
            }
        }

        public Inner inner() {
            return new Inner();
        }
    }

    @Test
    public void outer() {
        Outer.Inner inner = new Outer("aa").inner();
        Outer.Inner inner1 = new Outer("bbb").inner();
        System.out.println(inner);
        System.out.println(inner1);
    }


    /***
     * 链接到外部类
     * 到目前为止,内部类似乎还只是一种名字隐藏和组织代码的模式. 但是还有一些更吸引人的特性.
     * 当生成一个内部类的对象时, 此对象与制造它的外围对象(enclosing object)之间就有一种联系, 所以它能访问其其外围对象的所有成员,而不需要任何特殊条件.
     * 此外, 内部类还拥有其外围类的所有元素的访问权. 这是如何做到的呢?
     * 当某个外围类的对象创建了一个内部类对象的时候, 此内部类对象必定会秘密地捕获一个指向那个外围类对象的引用(静态方法除外). 然后再你访问此外围类成员的时候, 就是用哪个引用来选择外围类的成员.
     *
     * 迭代器接口
     *
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

        public Selector selector() {
            return new SequenceSelector();
        }
    }


    @Test
    public void sequence() {
        Sequence sequence = new Sequence(10);
        for(int i = 0; i < 10; i++) {
            sequence.add(Integer.toString(i));
        }
        // Selector类似于迭代器
        Selector selector = sequence.selector();
        while (!selector.end()) {
            System.out.println(selector.current() + " ");
            selector.next();
        }
        System.out.println(((Sequence.SequenceSelector)selector).sequence());
    }


    class StringHolder {
        private String data;

        public StringHolder(String d) {
            this.data = d;
        }

        @Override
        public String toString() {
            return data;
        }
    }

    @Test
    public void stringHolder() {
        Sequence sequence = new Sequence(10);
        for(int i = 0; i < 10; i++) {
            sequence.add(new StringHolder("hello" + i));
        }
        // 获取迭代器
        Selector selector = sequence.selector();
        while (!selector.end()) {
            System.out.println(selector.current() + " ");
            selector.next();
        }
    }


    /**
     * 使用.this和.new
     * 当内部类需要生成对外部类对象的引用, 可以使用外部类的名字.this.  OutClass.this;
     * 当需要在外部类的外部创建某个内部类的对象时, 必须在new表达式中提供对其他外部类对象的引用, 外部对象名.new.  outObj.new InnerClass()
     * 在拥有外部类对象之前是不可能创建内部类对象的. 这是因为内部类对象会暗暗地链接到创建它的外部类对象上. 但是如果创建的是嵌套类(静态内部类), 那么就不需要外部类对象的引用.
     *
     */
    class DotThis {
        void f() {
            System.out.println("DoThis.f()");
        }

        public class Inner {

            // Outer.this 返回外部类的引用
            public DotThis outer() {
                return DotThis.this;
            }
        }

        public Inner inner() {
            return new Inner();
        }
    }

    @Test
    public void dotThis() {
        DotThis x = new DotThis();
        DotThis.Inner inner = x.inner();
        DotThis x1 = inner.outer();
        x1.f();

        // 创建内部类对象.
        DotThis.Inner inner1 = x.new Inner();
    }


    class Parcel3 {
        class Contents {
            private int i = 11;
            public int value() {
                return i;
            }
        }

        class Destination {
            private String label;
            Destination(String whereTo) {
                this.label = whereTo;
            }

            String readLabel() {
                return label;
            }
        }
    }

    @Test
    public void parcel3() {
        Parcel3 x = new Parcel3();
        Parcel3.Contents contents = x.new Contents();
        Parcel3.Destination tasmania = x.new Destination("Tasmania");
    }

    @Test
    public void p4() {
        Outer o = new Outer("outer");
        Outer.Inner inner = o.new Inner();
    }

    @Test
    public void p5() {
        Exercise7 x = new Exercise7();
        Exercise7.Inner inner = x.inner();
        inner.change("luominghao");
    }


    class OuterB {
        class Inner {
            private int age = 10;

            private void f() {
                System.out.println("Inner.f(), age = " + age);
            }
        }

        public void g() {
            Inner inner = new Inner();
            inner.age = 100;
            inner.f();
        }
    }

    @Test
    public void outerB() {
        OuterB x = new OuterB();
        x.g();
    }



    interface U {
        void a();
        void b();
        void c();
    }

    class A {
        public void d() {
            System.out.println("A.d()");
        }

        public U makeU() {
            return new U() {
                @Override
                public void a() {
                    System.out.println("A.makeU().a()");
                }

                @Override
                public void b() {
                    System.out.println("A.makeU().b()");
                }

                @Override
                public void c() {
                    System.out.println("A.makeU().c()");
                }
            };
        }
    }

    class B {
        private U[] us;

        private int index = 0;

        public B(int size) {
            us = new U[size];
        }

        public void add(U u) {
            us[index++] = u;
        }

        public void remove(int i) {
            us[i] = null;
        }

        public void forEach() {
            for(U u: us) {
                u.a();
                u.b();
                u.c();
            }
        }
    }

    @Test
    public void a() {
        A a = new A();
        B b = new B(10);
        for(int i = 0; i < 10; i++) {
            b.add(a.makeU());
        }

        b.forEach();

        for(int i = 0; i < 10; i++) {
            b.remove(i);
        }
    }

}
