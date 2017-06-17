package cn.tim.thinking.initialize5;

import java.util.Arrays;

/**
 * Created by LuoLiBing on 16/8/23.
 * 对象的创建过程
 * 1 即使没有显示地使用static关键字,构造器实际上也是静态方法.当首次创建对象时,或者类的静态方法/静态域首次被访问时,jvm必须查找类路径,定位class文件
 * 2 载入class文件,有关静态初始化的所有动作都会执行.静态初始化值在class对象首次加载的时候进行一次
 * 3 当用new创建对象的时候,首先将在堆上为对象分配足够的存储空间
 * 4 这块存储空间会被清0,自动的将对象中所有基本类型设置为默认值,引用类型置为null
 * 5 执行所有出现于字段定义处的初始化动作
 * 6 执行构造器
 *
 */
public class Instance {

    public static int a;

    public static int b = 10;

    /**
     * 数组初始化可以直接用大括号表示,存储空间的分配等价于使用new,将由编译器负责
     * 数组元素会自动被初始化为默认值
     */
    int[] arr = new int[2];

    /**
     *
     */
    int[] arr1 = {1, 2, 3,};

    String[] strs = {"luo", "li", "bing"};

    private int count = createCount();

    static {
        System.out.println("before static init a=" + a);
        a = 10;
        System.out.println("after static init a=" + a);
    }

    Mug mug1;
    Mug mug2;

    // 非静态实例初始化,该语法支持匿名内部类
    {
        mug1 = new Mug(1);
        mug2 = new Mug(2);
        System.out.println("mug1 & mug2 inited");
        System.out.println("arr1=" + arr1.length);
        for(String str: strs) {
            System.out.println("str=" + str);
        }
    }

    public Instance() {
        System.out.println(Arrays.toString(arr));
        arr[0] = 1;
        arr[1] = 2;
        System.out.println(Arrays.toString(arr));
        System.out.println("before construct init count=" + count);
        count = 100;
        System.out.println("after construct init count=" + count);
    }

    public Instance(String str) {
        System.out.println(str);
    }

    public int createCount() {
        System.out.println("before declare init count=" + count);
        try {
            return 10;
        } finally {
            System.out.println("after declare init count=" + 10);
        }
    }

    /**
     * 调用Instant的静态方法main函数会自动进行静态初始化
     * @param args
     */
    public static void main1(String[] args) {
        System.out.println("before main");
        System.out.println("b = " + b);
        System.out.println("b = " + a);
        new Instance();
    }

    public void exception1() throws Exception {
        try {
            throw new Exception();
        } finally {
            System.out.println("exception1");
        }
    }

    public static void main2(String... args) {
        try {
            new Instance().exception1();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("main exception");
        }
    }

    public static void main3(String... args) {
        System.out.println(args.length);
        System.out.println(args.getClass());
        for(String arg : args) {
            System.out.println("arg= " + arg);
        }
    }

    /**
     * 可变参数列表不依赖于自动包装机制,这里使用的是基本数据类型
     *
     * @param nums
     */
    public static void inint(int...nums) {
        System.out.println(nums.getClass());
    }

    public static void main4(String[] args) {
        Instance[] instances = new Instance[3];
        Instance[] instances1 = {new Instance("aaa"), new Instance("bbb"), };
        main3("aaa", "bbb", "ccc");
        main3(new String[]{"aaa", "bbb"});
        main3();
        inint(1, 2);
    }

    static void f(long...num) {
        System.out.println("long=" + num.getClass());
    }

    static void f(int...num) {
        System.out.println("int=" + num.getClass());
    }


    public static void main(String[] args) {
        // 不适用参数调用f()的时候会报编译错误, 如果用的基本类型, 会优先使用低级的方法
        f();
    }

}

class Mug {
    Mug(int marker) {
        System.out.println("Mug (" + marker + ")");
    }
    void f(int marker) {
        System.out.println("f(" + marker + ")");
    }
}
