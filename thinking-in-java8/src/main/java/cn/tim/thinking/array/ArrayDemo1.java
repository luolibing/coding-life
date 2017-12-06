package cn.tim.thinking.array;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by LuoLiBing on 16/12/24.
 * 数组与其他种类的容器之间的区别:
 * 效率|类型|保存基本类型的能力. 数组是一种效率最高的存储和随机访问对象引用序列的方式.
 * 数组就是一个简单的线性序列, 这使得元素访问非常快速. 但是这种速度所付出的代价就是数组对象的大小被固定.
 * 并且在生命周期中不可改变. 数组和容器都可以保证不能滥用它们, 如果越界, 都会得到一个数组越界异常.
 * 使用泛型之前, 其他容器类在处理对象时, 都将他们视作Object处理. 但是数组却可以持有某个具体类型.
 * 数组可以持有基本类型, 而泛型之前的容器则不能.
 */
public class ArrayDemo1 {

    static class BerylliumSphere {
        private static long counter;
        private final long id = counter++;

        @Override
        public String toString() {
            return "Sphere " + id;
        }
    }

    /**
     * 随着泛型,自动包装机制的使用, 容器已经可以与数组几乎一样方便地用于基本类型中了. 数组仅存的有点就是效率
     * 但是如果要解决更一般化的问题, 数组可能会受到过多的限制, 因此在这种情形下还是会使用容器.
     */
    @Test
    public void containerComparison() {
        // 数组处理方式
        BerylliumSphere[] spheres = new BerylliumSphere[10];
        for(int i = 0; i < 5; i++) {
            spheres[i] = new BerylliumSphere();
        }
        System.out.println(Arrays.toString(spheres));
        System.out.println(spheres[4]);

        // 容器
        List<BerylliumSphere> sphereList = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            sphereList.add(new BerylliumSphere());
        }
        System.out.println(sphereList);
        System.out.println(sphereList.get(4));

        int[] integers = {0, 1, 2, 3, 4, 5};
        System.out.println(Arrays.toString(integers));
        System.out.println(integers[4]);

        List<Integer> intList = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5));
        intList.add(97);
        System.out.println(intList);
        System.out.println(intList.get(4));
    }


    /**
     * 数组对象只是一个引用, 指向在堆中创建的一个真实对象. 数组对象初始化可以隐式地创建此对象, 或者用new表达式显式地用new表达式创建.
     * 只读成员length是数组对象的唯一一个可以访问得字段或方法了. 对象数组和基本类型数组在使用上几乎是相同的, 唯一的区别就是对象数组保存的是引用, 基本类型数组直接保存基本类型的值.
     */
    @Test
    public void arrayOptions() {
        BerylliumSphere[] a;
        // 创建数组的时候, 就默认将数组引用对象进行了初始化, 所有引用元素为null
        BerylliumSphere[] b = new BerylliumSphere[5];
        System.out.println("b: " + Arrays.toString(b));

        BerylliumSphere[] c = new BerylliumSphere[4];
        for(int i = 0; i < c.length; i++) {
            if(c[i] == null) {
                c[i] = new BerylliumSphere();
            }
        }

        // 聚合初始化
        BerylliumSphere[] d = {
                new BerylliumSphere(), new BerylliumSphere()
        };

        // 动态聚合初始化, 可以在任意位置创建和初始化数组对象
        a = new BerylliumSphere[] {
                new BerylliumSphere(), new BerylliumSphere()
        };

        System.out.println("a.length = " + a.length);

        a = d;
        System.out.println("a.length = " + a.length);


        int[] e;
        int[] f = new int[5];
        // 基本类型数组, 自动初始化为0
        System.out.println(Arrays.toString(f));

        int[] g = new int[4];
        for(int i = 0; i < g.length; i++) {
            g[i] = i * i;
        }

        int[] h = {11, 47, 93};
        // System.out.println(e.length); 没有初始化的数组无法调用Length
    }


    static class B {
        static void take(BerylliumSphere[] array) {
            System.out.println(Arrays.toString(array));
        }

        public static void main(String[] args) {
            take(new BerylliumSphere[] {new BerylliumSphere(), new BerylliumSphere()});
            // take({new BerylliumSphere(), new BerylliumSphere()}); 方法参数不能使用普通聚合初始化
            BerylliumSphere[] array = new BerylliumSphere[] {new BerylliumSphere(), new BerylliumSphere()};
            // array = {new BerylliumSphere(), new BerylliumSphere()}; // 在非初始化的时候不能给数组使用普通聚合初始化
            array = new BerylliumSphere[] {new BerylliumSphere(), new BerylliumSphere()}; // 这个时候必须使用动态聚合初始化
            BerylliumSphere[] array2 = {new BerylliumSphere(), new BerylliumSphere()}; // 在初始化的时候, 没有必要使用动态聚合初始化, 使用普通聚合初始化即可
        }
    }


    // 返回数组
    static class IceCream {
        private static Random rand = new Random(47);

        static final String[] FLAVORS = {
                "Chocolate", "Strawberry", "Vanilla Fudge Swirl", "Mint Chip", "Mocha Almond Fudge", "Mud pie"
        };

        public static String[] flavorSet(int n) {
            if(n > FLAVORS.length) {
                throw new IllegalArgumentException("Set too big");
            }
            String[] results = new String[n];
            // 是否被选中的元素
            boolean[] picked = new boolean[FLAVORS.length];
            for(int i = 0; i < n; i++) {
                // 找到没有匹配的
                int t;
                do {
                    t = rand.nextInt(FLAVORS.length);
                } while (picked[t]);
                results[i] = FLAVORS[t];
                picked[i] = true;
            }
            return results;
        }

        public static void main(String[] args) {
            for(int i = 0; i < 7; i++) {
                System.out.println(Arrays.toString(flavorSet(3)));
            }
        }
    }


    static void classInfo(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        System.out.println("fields: ");
        for(Field f: fields) {
            System.out.println(f.getName());
        }

        System.out.println("Methods: ");
        Method[] methods = clazz.getMethods();
        for(Method m : methods) {
            System.out.println(m.getName());
        }
    }

    @Test
    public void arrayClass() {
        int[] array = new int[10];
        classInfo(array.getClass());
    }

    public static void main(String[] args) {
        System.out.println("aaa");
    }
}
