package cn.tim.thinking.multiplex;

import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by LuoLiBing on 16/12/14.
 * final关键字: 这是无法改变的
 */
public class FinalDemo1 {

    /**
     * final数据, 恒定不变的数据
     * 例如
     * 1 一个永不改变的编译时常量
     * 2 一个在运行时被初始化的值,而你不希望它被改变
     *
     * 编译期常量可以将该常量值代入任何可能用到它的计算式中,也就是说在编译时执行计算式, 这减轻了一些运行时的负担. 在java中, 这类常量必须是基本数据类型,并且以关键词final表示.
     * 一个既是static又是final的域只占据一段不能改变的存储空间.
     * 对于基本类型,final使数值恒定不变; 而用于对象引用,final使引用恒定不变, 一旦引用被初始化指向一个对象, 就无法再把它指向另一个对象. 然后对象其自身却是可以被修改的.
     * final型的静态和非静态的区别在于初始化: 非静态会在对象每次初始化的时候初始化,  静态会在类被加载的时候进行初始化(编译期常量).
     *
     */
    static class Value {
        int i; // 包访问权限

        public Value(int i) {
            this.i = i;
        }
    }

    static class FinalData {
        private static Random rand = new Random(47);

        private String id;

        public FinalData(String id) {
            this.id = id;
        }

        // 编译期常量
        private final int valueOne = 9;

        // 编译期常量
        private final static int VALUE_TWO = 99;

        public final static int VALUE_THREE = 39;

        // 并不是所有的final类型数据都可以在编译时可以知道它的值
        private final int i4 = rand.nextInt(20);

        static final int INT_5 = rand.nextInt(20);

        private Value v1 = new Value(11);

        private final Value v2 = new Value(22);

        private final static Value VAL_3 = new Value(33);

        private final int[] a = {1, 2, 3};

        public static void main(String[] args) {
            FinalData f1 = new FinalData("fd1");
            // f1.valueOne++; final型基本类型不能改变
            f1.v2.i++; // final的对象类型本身可以改变,只是引用不可以改变
            f1.v1 = new Value(0);

            // f1.v2 = new Value(1); final对象类型的引用不可以改变
            for(int i = 0; i < f1.a.length; i++) {
                f1.a[i]++; // 数组类型本身也是一个对象, 所以对象本身也可以改变
            }

            // f1.a = new int[5]; 数组的引用不能改变
            System.out.println(f1);
            FinalData f2 = new FinalData("fd2");
            System.out.println(f2);
        }

        @Override
        public String toString() {
            return "FinalData{" +
                    "id='" + id + '\'' +
                    ", valueOne=" + valueOne +
                    ", i4=" + i4 +
                    ", v1=" + v1 +
                    ", v2=" + v2 +
                    ", a=" + Arrays.toString(a) +
                    '}';
        }
    }


    static class B {

    }

    static class A {
        final int age = getAge();

        private final int i;

        private final B b;

        private final int j;

        private final static String school;

        {
            j = 10;
        }

        static {
            school = "aaa";
        }

        final static String name = getName();

        A() {
            this.i = 1;
            this.b = new B();
        }

        public int getAge() {
            System.out.println("getAge");
            return 20;
        }

        public static String getName() {
            System.out.println(school);
            System.out.println("getName");
            return "jack";
        }
    }

    @Test
    public void a() {
        A a = new A();
        new A();
    }

    /**
     * final参数: 在参数列表中以声明的方式将参数指明为final. 意味着你无法在方法中更改参数引用所指向的对象
     *
     */
    class Gizmo {
        public void spin() {}
    }

    class FinalArguments {
        void with(final Gizmo g) {
            // g = new Gizmo(); g是final类型, g的引用不能更改
        }

        void without(Gizmo g) {
            g = new Gizmo();
            g.spin();
        }

        void f(final int i) {
            // i ++; final类型的i, 所以不能更改引用 i = i + 1;
        }

        int g(final int i) {
            return i + 1;
        }
    }

    @Test
    public void finalArguments() {
        FinalArguments x = new FinalArguments();
        x.without(null);
        x.with(null);
    }


    /**
     * final方法: 使用final方法的原因
     * 1 把方法锁定,防止任何继承类修改, 重载. 这是出于设计的考虑
     * 2 效率(hotspot虚拟机已经将这些优化去掉), 将一个方法指明为final, 就是同意编译器将针对该方法的所有调用都转为内嵌调用.
     * 非final方法的调用过程(将参数压入栈, 跳至方法代码处并执行, 然后跳回并清理栈中的参数,处理返回值),
     * 并且以方法体中的实际代码的副本来替代方法调用.这将消除方法调用的开销.
     *
     * private和final, private方法都隐含着final的意思, 因为private方法无法覆盖, 即使覆盖也不会产生任何作用.
     * 覆盖只有在某方法是基类的接口的一部分时才会出现(public非final方法)
     */
    class WithFinals {
        private final void f() {
            System.out.println("WithFinals.f()");
        }

        private void g() {
            System.out.println("WithFinals.g()");
        }

        public void k() {
            System.out.println("WithFInals.k()");
        }

        public final void i() {
            System.out.println("WithFinals.i()");
        }

        public void j() {
            System.out.println("WithFinals.j()");
        }
    }

    class OverridingPrivate extends WithFinals {
        private final void f() {
            System.out.println("OverridingPrivate.f()");
        }
    }


    @Test
    public void withFinals() {
        WithFinals x = new OverridingPrivate();
        // 在子类中定义父类同名的private方法,并不能达到覆盖的效果, 虽然可以这样调用, 但是x调用的还是父类的private方法, 仅仅是子类具有父类同名方法而已.
        // 如果子类将父类的方法覆盖为private方法, 编译器会报错
        x.f();
    }


    /**
     * final类
     * 当某个类定义为final时,表明你打算让其他类继承自这个类. 既然类为final,其中的所有方法隐式为final型,因为无法继承这个类. 字段还是遵守final规则
     *
     */
    class SmallBrain {}

    final class Dinosaur {
        int j = 7;
        int i = 10;
        SmallBrain x = new SmallBrain();
        void f() {}
    }

    // 无法继承 class Futher extends Dinosaur {}

    @Test
    public void smallBrain() {
        Dinosaur x = new Dinosaur();
        x.i++;
        x.j++;
        x.f();
        x.x = new SmallBrain();
    }
}
