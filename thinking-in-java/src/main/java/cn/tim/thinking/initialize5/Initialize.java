package cn.tim.thinking.initialize5;

import org.junit.Test;

/**
 * Created by LuoLiBing on 16/8/5.
 * 程序名字的运用
 * 1 当创建一个对象时,相当于给此对象分配到的存储空间取一个名字
 * 2 所谓的方法则是给某个动作取一个名字.
 *
 * 相同的词可以表达多种不同的含义--相当于被重载
 *
 */
public class Initialize {

    /**
     * 先被初始化为null,然后在构造函数中进行初始化
     */
    private String name;

    /**
     * 编译器可以创建一个默认的无参构造器,如果已经创建了一个构造器,则编译器不会再创建默认的无参构造器
     */
    private Initialize(String s) {
        this.name = s;
        System.out.println("name=" + name);
        name = "luolibing";
        System.out.println("name=" + name);
        System.out.println("init");
    }

    public Initialize() {
        // 构造方法只能调用一个,而且必须处于构造方法起始处
        //this("default");
    }

    // 直接被初始化
    private String school = "school";

    public String getName() {
        return name;
    }

    @Test
    public void init1() {
        for(int i=0; i< 10; i++) {
            System.out.println("name=" + new Initialize("s").getName());
        }
        System.out.println(this.getName());
        System.out.println(getName());
    }

    public void f(String s, int i) {
        System.out.println(s + i);
    }

    public void f(int i, String s) {
        System.out.println(i + s);
    }

    /**
     * 只是返回值不一样不能进行重载
     * @return
     */
//    public String f(String s, int i) {
//        return s;
//    }

    public void s(char x) {
        System.out.println("chars");
    }

    public void s(byte x) {
        System.out.println("bytes");
    }

    public void s(short x) {
        System.out.println("shorts");
    }

    public void s(int x) {
        System.out.println("ints");
    }

    public void s(long x) {
        System.out.println("longs");
    }

    public void s(float x) {
        System.out.println("floats");
    }

    public void s(double x) {
        System.out.println("doubles");
    }

    public void s1(long s) {
        System.out.println("longs1");
    }

    public void s2(int s) {
        System.out.println("ints2");
    }

    /**
     * 当传入的数据类型小于方法中声明的形式参数类型,实际数据类型就会被提升.char类型会被提升到int类型
     */
    @Test
    public void sTest() {
        Initialize initialize = new Initialize("s");
        // 已经创建了一个构造器,编译器不会再创建默认的构造器
        //new Initialize();
        initialize.s(10);
        initialize.s1(10);
        long l = 20L;
        // 实参类型较大需要窄化,向下转型
        initialize.s2((int) l);

//        final LocalDateTime now = LocalDateTime.now();
//        System.out.println(now);
//        System.out.println(now.plusDays(1));

        System.out.println((long) 1);

        /**
         * 在调用getName方法时,getName方法需要区分是哪个对象调用的该方法,编译器会暗自把所操作对象的引用作为第一个参数传递给peel,所以上述方法的调用就变成了
         * Initialize.getName(instance)
         */
        System.out.println(getName());
    }

    @Test
    public void fTest() {
        Initialize initialize = new Initialize("luo").first().second();
        System.out.println(initialize.getName());
    }

    public Initialize first() {
        name = name + "first";
        return this;
    }

    public Initialize second() {
        name = name + "second";
        return this;
    }

    int i;

    boolean flag;

    /**
     * 所有变量使用前必须进行恰当的初始化,成员变量,系统会进行默认的初始化工作
     * 例如int为0,boolean为false,Object对象类型为null
     *
     * 构造器初始化
     * i会先被自动初始化,然后才是构造器初始化,自动初始化在构造器初始化之前进行.所以会先被置为0,然后被置为10
     */
    @Test
    public void test1() {
        int j;
        System.out.println(i);
        i = 10;
        System.out.println(flag);
        // j++; j没有被初始化,编译错误
    }
}
