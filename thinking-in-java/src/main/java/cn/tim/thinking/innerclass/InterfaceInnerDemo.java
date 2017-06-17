package cn.tim.thinking.innerclass;

/**
 * Created by LuoLiBing on 16/12/19.
 * 接口内部的类
 */
public interface InterfaceInnerDemo {

    void howdy();

    // 接口内部的类, 自动就是public和static的
    // 嵌套类, 继承外部类接口.
    // 如果你想要创建某些公共代码, 使得他们可以被某个接口的所有不同实现所共用,那么使用接口内部的嵌套类会显得很方便.
    class Test implements InterfaceInnerDemo {

        @Override
        public void howdy() {
            System.out.println("Howdy");
        }

        public static void f() {
            System.out.println("Test.f()");
        }

        public static void main(String[] args) {
            new Test().howdy();
        }
    }
}


class InterfaceInnerDemoImpl implements InterfaceInnerDemo {

    @Override
    public void howdy() {
        // 可以直接创建嵌套类的对象, 还可以直接调用.
        Test test = new Test();
        Test.f();
    }
}

class TestBed {
    public void f() {
        System.out.println("f()");
    }

    public static class Tester {
        public static void main(String[] args) {
            TestBed x = new TestBed();
            x.f();
        }
    }
}

interface OuterInterface {
    void f();

    class Outer implements OuterInterface {

        @Override
        public void f() {
            System.out.println("Outer.f()");
        }

        public static void main(String[] args) {
            Outer x = new Outer();
            x.f();
        }
    }
}

// 嵌套类调用了外部接口的方法, 然后通过传入外部接口的实现类.
interface Interface1 {
    void f();

    void g();

    class Outer {

        public static void k(Interface1 i) {
            System.out.println("Interface1.f()");
            i.f();
            System.out.println("Interface1.g()");
            i.g();
        }
    }
}

class Interface1Impl implements Interface1 {

    @Override
    public void f() {
        System.out.println("Interface1Impl.f()");
    }

    @Override
    public void g() {
        System.out.println("Interface1Impl.g()");
    }

    public static void main(String[] args) {
        Interface1.Outer.k(new Interface1Impl());
    }
}


/**
 * 从多层嵌套类中访问外部类的成员, 一个内部类被嵌套多少层并不重要, 它能透明地访问所有它锁嵌入的外围类的所有成员
 *
 */
class MNA {
    private void f() {}

    class A {
        private void g() {}

        public class B {
            void h() {
                // 多层嵌套类中访问外部类成员,并不需要任何条件
                g();
                f();
            }
        }
    }

    public static void main(String[] args) {
        MNA mna = new MNA();
        A a = mna.new A();
        A.B b = a.new B();
        b.h();
    }
}
