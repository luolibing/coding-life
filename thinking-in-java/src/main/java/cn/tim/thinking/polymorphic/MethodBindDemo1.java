package cn.tim.thinking.polymorphic;

import org.junit.Test;

/**
 * Created by LuoLiBing on 16/12/15.
 * public static void tune(Instrument i) {
 *
 * }
 * 编译器怎样才能知道这个Instrument引用所指向的对象是Wind对象,而不是其他对象呢, 实际上, 编译器无法知道. 这有赖于方法绑定.
 *
 * 方法调用绑定:
 * 将一个方法调用同一个方法主体关联起来被称作绑定.
 * 在程序执行前进行绑定叫做前期绑定. 由编译器和连接程序实现. public static void tune(Instrument i) { i.play(C_NOTE); }就是一个前期绑定
 * 在运行时根据对象的类型进行绑定叫做后期绑定, 后期绑定也叫动态绑定和运行时绑定.
 *
 * java除了static方法和final(private方法属于final方法)之外,其他所有的方法都是后期绑定. final可以关闭动态绑定, 告诉编译器不需要对其进行动态绑定.
 * 多态的目的,将改变的事物与未变的事物分离开来的重要技术.
 */
public class MethodBindDemo1 {


    /**
     * 多态的缺陷: private方法被自动认为是final方法, 而且对导出类是屏蔽的.
     * 只有非private方法才可以被覆盖, 虽然基类和导出类具有相同名字的private方法,但是也不会进行覆盖, 所以最好不要采用一样的名字,避免产生混淆.
     */
    static class PrivateOverride {
        private void f() {
            System.out.println("private f()");
        }

        public static void main(String[] args) {
            PrivateOverride po = new Derived();
            po.f();
        }
    }

    static class Derived extends PrivateOverride {
        public void f() {
            System.out.println("public f()");
        }
    }


    /**
     * 多态的缺陷: 域与静态方法
     */
    class Super {
        public int field = 0;

        public int getField() {
            return field;
        }
    }

    class Sub extends Super {
        public int field = 1;

        public int getField() {
            return field;
        }

        public int getSuperField() {
            return super.field;
        }
    }

    @Test
    public void fieldAccess() {
        Super s = new Sub();
        // 0, 1 当把Sub对象转型为Super引用时, 任何域访问操作都将由编译器解析, 所以是前期绑定, 因此不是多态的
        // Super.field和Sub.field分配了不同的存储空间. 这样Sub实际包含了亮哥称为field的域; 自己的和Super处得到的. 在引用Sub中的field时所产生的默认域并非Super.field. 因此为了获得基类的field, 必须得使用super.field;
        System.out.println("sup.field = " + s.field + ", sup.getFiled() = " + s.getField());
        Sub x = new Sub();
        // 1, 1
        System.out.println("sup.field = " + x.field + ", sup.getFiled() = " + x.getField() + ", sup.getSuperField() = " + x.getSuperField());
    }


    /**
     * 静态方法是与类关联, 而并非与单个对象相关联. 所以并不具备多态性
     */
    static class StaticSuper {
        public static String staticGet() {
            return "Base staticGet()";
        }

        public String dynamicGet() {
            return "Base dynamicGet()";
        }
    }

    static class StaticSub extends StaticSuper {
        public static String staticGet() {
            return "Derived staticGet()";
        }

        public String dynamicGet() {
            return "Derived dynamicGet()";
        }
    }

    @Test
    public void staticPolymorphism() {
        StaticSuper sup = new StaticSub();
        System.out.println(sup.dynamicGet());
        System.out.println(sup.staticGet());
    }


    /**
     * 协变返回类型, 它表示在导出类的被覆盖方法可以返回基类方法的返回类型的某种导出类
     *
     */
    class Grain {
        @Override
        public String toString() {
            return "Grain";
        }
    }

    class Wheat extends Grain {
        @Override
        public String toString() {
            return "Wheat";
        }
    }

    class Mill {
        Grain process() {
            return new Grain();
        }
    }

    /**
     * Wheat继承自Grain
     * WheatMill继承自Mill
     */
    class WheatMill extends Mill {

        // 父类这个地方返回的是Grain, 子类这个地方返回Grain的子类, 这样就称作协变
        // Java5与之前的版本区别在这个地方, 之前的版本这个地方必须返回父类声明的类Grain类
        @Override
        Wheat process() {
            return new Wheat();
        }
    }

    @Test
    public void covariantReturn() {
        Mill m = new Mill();
        Grain g = m.process();
        System.out.println(g);

        m = new WheatMill();
        g = m.process();
        System.out.println(g);
    }
}
