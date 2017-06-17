package cn.tim.thinking.multiplex;

import java.util.Random;

/**
 * Created by LuoLiBing on 16/12/15.
 * 初始化及类的加载
 * java类的代码在初次使用时才加载,初次使用包括创建类的第一个对象,和访问static域或static方法时. static初始化, 所有的static对象和static代码都会在加载时依据程序中的顺序依次初始化
 *
 * 组合和继承:
 * 组合一般是讲现有类型作为新类型底层实现的一部分来加以复用, 而继承复用的是接口.
 * 使用继承时,由于导出类具有基类接口, 因此可以向上转型为基类. 这对于多态至关重要
 * 一般优先选择组合(或者代理), 在必要的时候才使用继承. 组合比继承更灵活.
 * 程序开发应该是一个增量的过程.
 *
 */
public class InitClassLoaderDemo1 {

    static class Insect {
        private int i = 9;
        protected int j;

        Insect() {
            System.out.println("i = " + i + ", j = " + j);
            j = 39;
        }

        private int x2 = printInit("Insect.x2 initialized");

        private static int x1 = printInit("static Insect.x1 initialized");

        static int printInit(String x) {
            System.out.println(x);
            return 47;
        }
    }

    static class Beetle extends Insect {

        public final static Random rand = new Random(47);

        public final static int x = 100;

        public final static String name = "luo";

        // rand不能定义在xx的后面
        public final static int xx = rand.nextInt();

        private int k = printInit("Beetle.k initialized");

        {
            System.out.println("init block");
        }

        private int z = printInit("Beetle.z initialized");

        public Beetle() {
            super();
            System.out.println("k = " + k);
            System.out.println("j = " + j);
        }

        static {
            System.out.println("static block initialized");
        }

        private static int x2 = printInit("static Beetle.x2 initialized");

        /**
         * 初始化顺序:
         * static Insect.x1 initialized
         * static Beetle.x2 initialized
         * Beetle constructor
         * i = 9 , j = 47
         * Beetle.k initialized
         * k = 47
         * j = 39
         *
         * 对于同一个类
         * 静态字段(包括静态块), 按照代码顺序进行初始化. 在类静态字段(编译期常量除外)静态方法被调用时进行静态初始化
         * 先实例变量, 后构造函数的顺序进行初始化
         *
         * 基类与子类
         * 静态字段先父类, 后子类; 通过extend得知父类,所以先对父类进行静态初始化
         * 构造函数, 先初始化父类, 然后再子类实例成员初始化, 最后才是子类构造函数剩下部分
         * public Child() {
         *     // 子类的实例成员都为null, 通过二进制0来实现
         *     super();
         *     // 然后调用子类的实例成员初始化
         *     this.x = 10; // 最后才是构造函数剩下的部分
         * }
         *
         * @param args
         */
    }

    public static void main1(String[] args) {
        System.out.println(Beetle.x);
        System.out.println(Beetle.name);
        System.out.println(Beetle.xx);
        System.out.println("Beetle constructor");
        new Beetle();
    }


    public static void main(String[] args) {
        int a = 1;
        int b = 2;
        b = a + 0 * (a = b);
    }
}
