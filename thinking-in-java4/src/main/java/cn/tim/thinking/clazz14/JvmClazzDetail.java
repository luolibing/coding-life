package cn.tim.thinking.clazz14;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by LuoLiBing on 16/8/11.
 *
 */
public class JvmClazzDetail {

    /**
     * 静态块代码,以及静态变量,赋值时非常量(字符串相加)等,会在静态块中执行相应的代码
     * 静态块会先于非基本数据类型(String,以及6种数字类型)之前执行,但是静态块编译默认先于静态常量,所以当在static域中使用本类的静态常量时会报非法的向前引用错误
     *
     0: iconst_0
     1: istore_0
     2: getstatic     #15                 // Field java/lang/System.out:Ljava/io/PrintStream;
     5: ldc           #11                 // String aaaa
     7: invokevirtual #16                 // Method java/io/PrintStream.println:(Ljava/lang/String;)V
     10: ldc           #17                 // class cn/tim/thinking/clazz/JvmClazzDetail
     12: invokestatic  #18                 // Method org/slf4j/LoggerFactory.getLogger:(Ljava/lang/Class;)Lorg/slf4j/Logger;
     15: putstatic     #19                 // Field logger:Lorg/slf4j/Logger;
     18: bipush        60
     20: invokestatic  #13                 // Method java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
     23: putstatic     #20                 // Field score:Ljava/lang/Integer;
     26: return

     *
     */
    static {
        int i = 0;
        System.out.println("aaaa");
    }

    public final static Logger logger= LoggerFactory.getLogger(JvmClazzDetail.class);

    /**
     * 静态String与基本数据类型常量会直接赋值
     */
    public static String path = "/share";

    /**
     * 其他
     */
    public final static Integer score = 60;

    public final static boolean fl = true;

    public void strConcat() {
        // 会自动合并为一个常量
        String name = "aaa" + "bbb";
        // 通过变量相加,底层会调用StringBuilder进行相加
        String name2 = name + "luolibing";
    }

    public JvmClazzDetail() {
        /**
         * 默认会调用super(); 如果没有指定父类,默认继承自Object
         *  0: aload_0
         *  1: invokespecial #1                  // Method java/lang/Object."<init>":()V
         *
         *  Boolean值用0表示false,1表示true  Boolean f = true
         *  0: iconst_1
         *  1: invokestatic  #8                  // Method java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
         *
         *  boolean f = true
         *  0: iconst_1
         *  1: istore_1
         *
         * 在调用本地方法时,会默认将this作为第一个参数放入到本地参数数组当中
         *  成员变量和构造函数的执行顺序,在字节码中可以看到,在构造函数中会先调用成员变量的初始化; 然后再执行构造函数中的方法
         **/
        sum = 10;
        System.out.println(flag);
    }

    boolean flag = true;

    Boolean f = true;

    String str = "aaaa";

    Integer sum;

    public static void main(String[] args) {
        new JvmClazzDetail();
        int a = 1;
        int b = 5;
        int c = 10;
    }
}
