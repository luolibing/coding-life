package cn.tim.thinking.innerclass;

import org.junit.Test;

/**
 * Created by LuoLiBing on 16/12/18.
 * 匿名内部类
 */
public class AnonymousDemo {

    interface Destination {
        String readLabel();
    }

    interface Contents {
        int value();
    }


    class Parcel7 {

        // 匿名内部类, 如果使用的是直接new接口, 只能使用默认构造器来初始化
        public Contents contents() {
            return new Contents() {
                private int i = 11;

                @Override
                public int value() {
                    return i;
                }
            }; // 需要用分号结尾
        }
    }

    @Test
    public void parcel7() {
        Parcel7 x = new Parcel7();
        Contents c = x.contents();
    }

    // 使用非默认构造函数初始化,只需要简单地传递合适的参数给基类的构造器即可.
    class Wrapping {
        private int i;
        public Wrapping(int i) {
            this.i = i;
        }

        public int value() {
            return i;
        }
    }

    class Parcel8 {
        public Wrapping wrapping(int x) {
            // 通过传递参数给对应的构造器, 虽然Wrapping是一个普通的类, 但是这个地方却把它当成接口(基类)来使用, 这里覆盖了基类的value()方法
            // 这里也是一个匿名的内部类, 并且继承自Wrapping()类, 使用了带参数的构造器
            return new Wrapping(x) {
                @Override
                public int value() {
                    System.out.println(super.getClass());
                    return super.value() * 47;
                }
            }; // 这个分号,并不是内部类定义的结束, 而是标记这个表达式结束, 只是碰巧这个地方是内部类的定义而已
        }
    }


    /**
     * 在定义匿名内部类是, 如果希望它使用一个在外部定义的对象,那么编译器会要求其参数引用是final, java8以前必须显式定义为final, java8之后的版本不强制要求显式定义为final,但是必须为不可变的类型
     */
    class Parcel9 {
        public Destination destination(final String dest) {
            // 匿名内部类直接使用了外部定义的一些引用, 这个时候需要dest必须是final型的
            return new Destination() {
                private String label = dest;

                @Override
                public String readLabel() {
                    return label;
                }
            };
        }
    }

    @Test
    public void parcel9() {
        Parcel9 x = new Parcel9();
        Destination d = x.destination("luo");
        System.out.println(d.readLabel());
    }


    /**
     * 如何给匿名类定义带参数的构造器, 由于匿名内部类没有名字, 所以没办法命名构造器, 只能使用默认构造器, 但是可以通过实例初始化, 达到这么一个效果
     */
    static abstract class Base {
        public Base(int i) {
            System.out.println("Base Constructor, i = " + i);
        }

        public abstract void f();
    }

    // 匿名内部类, 带参数的构造器
    static class AnonymousConstructor {
        public static Base getBase(int i) {
            // 这个地方如果i,没在内部类里面使用,并不要求必须是final类型, 但是如果在内部使用, 则必须是final类型的.
            return new Base(i) {
                private int index;

                // 实例初始化的时机效果就是构造器, 但是不能重载多个实例初始化方法,因此仅有一个这样的构造器
                {
                    System.out.println("Inside instance initializer");
                    index = i * 10;
                }

                @Override
                public void f() {
                    System.out.println("AnonymousConstructor.f(), index = " + index);
                }
            };
        }
    }

    @Test
    public void anonymousConstructor() {
        Base base = AnonymousConstructor.getBase(47);
        base.f();
    }

    /**
     * 匿名内部类与正规继承相比有些受限, 匿名内部类既可以扩展类,也可以实现接口, 但是两者不能兼备.
     */


    class A {
        private String name;

        public A(String n) {
            this.name = n;
        }

        public void f() {
            System.out.println("A.f(), name = " + name);
        }
    }

    class B {
        public A a(String n) {
            return new A(n) {
                @Override
                public void f() {
                    System.out.println(super.getClass());
                    System.out.println("subClass from " + super.getClass() + " , name " + super.name);
                }
            };
        }
    }

    @Test
    public void a() {
        B b = new B();
        A a = b.a("luolibing");
        a.f();
    }
}
