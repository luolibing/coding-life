package cn.tim.thinking.innerclass;

import cn.tim.thinking.innerclass.a.InterfaceA;
import cn.tim.thinking.innerclass.c.SubOuter;
import org.junit.Test;

/**
 * Created by LuoLiBing on 16/12/18.
 */
public class UpcastDemo {

    /**
     * 内部类与向上转型
     * 当将内部类向上转型为其基类或者一个接口的时候, 内部类就有了用武之地. 这是因为内部类--某个接口的实现--能够完全不可见, 并且不可用. 所得到的只是指向基类或接口的引用.所以能够很方便地隐藏实现细节.
     *
     */
    interface Destination {
        String readLabel();
    }

    interface Contents {
        int value();
    }

    /**
     * 内部类设置为private或者protected, 或者默认包访问权限,  这样客户端程序员想了解或者访问这些成员, 都将会受到限制.
     * 实际上, 甚至不能向下转型成private内部类(protected内部类, 除非是继承自它的子类或者同包), 因为不能访问其类.
     * private内部类给类的设计者提供了一种途径,通过这种方式可以完全阻止任何依赖于类型的编码, 并且完全隐藏了实现的细节.
     */
    class Parcel4 {

        // 私有private的内部类, 除当前类以外都不能访问到.
        private class PContents implements Contents {

            private int i = 11;

            @Override
            public int value() {
                return i;
            }
        }

        // protected的内部类, 除PDestination的子类以及处于同一个包下的类能访问到,其他的都不能访问.
        protected class PDestination implements Destination {

            private String label;

            private PDestination(String s) {
                this.label = s;
            }

            @Override
            public String readLabel() {
                return label;
            }
        }

        // 方法返回的都是内部类的基类或者是接口, 而接口往往是public的
        public Destination destination(String s) {
            return new PDestination(s);
        }

        public Contents contents() {
            return new PContents();
        }
    }

    @Test
    public void parcel4() {
        Parcel4 x = new Parcel4();
        Contents contents = x.contents();
        Destination jack = x.destination("jack");

        // x.new PContents();  无法进行手动创建对象, 因为PContents类是私有的, 只在外部类中课件
    }


    @Test
    public void x() {
        SubOuter x = new SubOuter();
        InterfaceA i = x.interfaceA();
        i.f();
    }

    @Test
    public void in() {
        InterfaceInnerDemo.Test t = new InterfaceInnerDemo.Test();
        t.howdy();
    }
}
