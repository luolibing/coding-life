package cn.tim.thinking.clazz14;

import org.junit.Test;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LuoLiBing on 16/8/8.
 * 字节码解析
 */
public class JvmClazz {

    /**
     * 常量定义
     * descriptor: Lcn/tim/thinking/clazz/JvmClazz;
     * flag: ACC_PUBLIC, ACC_STATIC, ACC_FINAL
     */
    public final static JvmClazz instance = new JvmClazz();

    public List list = new ArrayList<>();

    /**
     * 如果没有定义构造器,编译器会自动添加上默认的构造器
     *    Code:
             stack=3, locals=1, args_size=1
                 0: aload_0
                 1: invokespecial #1                  // Method java/lang/Object."<init>":()V   调用父类Object的初始化方法init,包含构造函数
                 4: aload_0
                 5: new           #2                  // class java/util/ArrayList              操作码new只为新实例分配内存
                 8: dup                                                                         dup复制栈顶上的元素
                 9: invokespecial #3                  // Method java/util/ArrayList."<init>":()V初始化方法
                 12: putfield      #4                  // Field list:Ljava/util/List;           保存刚刚创建的实例
                 15: return


     // 保存了字节码和源码之间的关系
         LineNumberTable:
         line 19: 0
         line 20: 4

         // 描述了栈桢中局部变量表和源代码中定义的变量之间的关系
         LocalVariableTable:
         Start  Length  Slot  Name   Signature
         0       5     0  this   Lcn/tim/thinking/clazz/JvmClazz;
     */
    public JvmClazz() {
        super();
    }

    /**
     static {};
         descriptor: ()V
         flags: ACC_STATIC
         Code:
             stack=2, locals=0, args_size=0
                 0: new           #7        分配内存空间                  // class cn/tim/thinking/clazz/JvmClazz
                 3: dup                     复制栈顶元素
                 4: invokespecial #8        执行#8初始化方法init                  // Method "<init>":()V
                 7: putstatic     #13       保存刚刚创建的实例到常量池对象#13  JvmClazz.instance                        // Field instance:Lcn/tim/thinking/clazz/JvmClazz;
                 10: getstatic     #10      将#10System.out Ljava/io/PrintStream置于栈顶                      // Field java/lang/System.out:Ljava/io/PrintStream;
                 13: ldc           #14      加载常量#14 aaa                 // String aaaa
                 15: invokevirtual #12      执行指定方法                 // Method java/io/PrintStream.println:(Ljava/lang/String;)V
                 18: return
             LineNumberTable:
                 line 17: 0
                 line 68: 10
                 line 69: 18
     */
    static {
//        System.out.println("aaaa");
    }

    /**
     * 类型描述符
     * J    long
     * L<类型名称>  引用类型Ljava/lang/String
     * Z    boolean
     * [    array-of
     */
    public void type() {
        int i = 1;
        long l = 2;
        JvmClazz jvmClazz = new JvmClazz();
        boolean b = true;
        int[] arr = new int[]{1,2,3,4};
    }


    /**
     * 常量池: Constant pool(javap -v)
     * Constant pool:
     #1 = Methodref          #7.#29         // java/lang/Object."<init>":()V
     #2 = Long               2l
     #4 = Class              #30            // cn/tim/thinking/clazz/JvmClazz
     #5 = Methodref          #4.#29         // cn/tim/thinking/clazz/JvmClazz."<init>":()V
     #6 = Fieldref           #4.#31         // cn/tim/thinking/clazz/JvmClazz.instance:Lcn/tim/thinking/clazz/JvmClazz;
     #7 = Class              #32            // java/lang/Object
     #8 = Utf8               instance
     #9 = Utf8               Lcn/tim/thinking/clazz/JvmClazz;
     #10 = Utf8               <init>
     #11 = Utf8               ()V
     #12 = Utf8               Code
     #13 = Utf8               LineNumberTable
     #14 = Utf8               LocalVariableTable
     #15 = Utf8               this
     #16 = Utf8               type
     #17 = Utf8               i
     #18 = Utf8               I
     #19 = Utf8               l
     #20 = Utf8               J
     #21 = Utf8               jvmClazz
     #22 = Utf8               b
     #23 = Utf8               Z
     #24 = Utf8               arr
     #25 = Utf8               [I
     #26 = Utf8               <clinit>
     #27 = Utf8               SourceFile
     #28 = Utf8               JvmClazz.java
     #29 = NameAndType        #10:#11        // "<init>":()V
     #30 = Utf8               cn/tim/thinking/clazz/JvmClazz
     #31 = NameAndType        #8:#9          // instance:Lcn/tim/thinking/clazz/JvmClazz;
     #32 = Utf8               java/lang/Object
     */
    public void constantPool() {
        this.type();
        System.out.println("constantPool");
    }

    public String add(String name) {
        String str = "luo" + name;
        return "aaa" + "bbb";
    }


    public static void main(String[] args) throws InterruptedException {
        System.out.println();
        new JvmClazz().add("libing");
        Runtime rs = Runtime.getRuntime();
        System.out.println("Free memory before gc=" + rs.freeMemory());
        rs.gc();
        System.out.println("Free memory after  gc=" + rs.freeMemory());

        final LocalDate localDate = LocalDate.parse("2016-08-12", DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        System.out.println(LocalDate.now().isAfter(localDate));
    }

    /**
     * 操作码:
     * 名称  参数  堆栈布局  描述
     * getfield i1 i2
     *
     * 操作码类型:
     * 加载和储存操作码
     * load     从局部变量加载值到栈上
     * ldc      从池中加载常量到栈上
     * store    把值从进程中的栈中移走,存到局部变量中
     * dup      复制栈顶部的值
     * getfield 从栈顶部对象的常量池中得到指定位置的域
     * putfield 把值放入对象在常量池中指定位置的域上
     *
     * 数学运算操作码
     * add
     * sub
     * div
     * mul
     * (cast)
     *
     * 执行操作控制码
     * if       如果符合特定条件,则跳转到特定分支的偏移处
     * goto     无条件地跳转到分支偏移处
     * jsr      跳到本地子流程中,并把返回地址(下一个操作码的便宜地址)放到栈中
     * ret      返回到索引的局部变量所指向的偏移地址
     * tableswitch 实现switch
     * lookupswitch实现switch
     *
     * 调用操作码
     * invokestatic     调用静态方法
     * invokevirtual    调用一个常规的实例方法
     * invokeinterface  调用一个接口方法
     * invokespecial    调用一个特殊的实例方法
     * invokedynamic    动态调用
     *
     * 平台操作操作码
     * new          为新对象分配内存,类型由指定位置的常量确定
     * monitorenter 锁住对象
     * monitorexit  解锁对象
     *
     * 创建对象的字节码模式
     * new dup invokespecial <init>
     *
     * 字节码快捷方式
     * aload_0
     * dstore_2
     */
    public void operate() {
        int sum = 0;
        for (int i = 0; i < 10; i++) {
            System.out.println(i);
            sum += i;
        }

        if (sum > 10) {
            System.out.println(">10");
        }
        synchronized (this) {
            add("luolibing");
        }
    }

    public void arrayForEach(int i) {
        i++;
    }

    @Test
    public void gc() {
        List<GarbageCollectorMXBean> gcList = ManagementFactory.getGarbageCollectorMXBeans();
        for(GarbageCollectorMXBean b : gcList) {
            System.out.println(b.getName());
            System.out.println(b.getObjectName());
        }
    }
}
