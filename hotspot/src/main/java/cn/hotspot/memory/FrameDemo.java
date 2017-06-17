package cn.hotspot.memory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LuoLiBing on 17/1/21.
 */
public class FrameDemo {

    public FrameDemo() {
        /**
         * public cn.hotspot.module2.FrameDemo();
             descriptor: ()V
             flags: ACC_PUBLIC
             Code:
                 stack=4, locals=1, args_size=1
                     0: aload_0                           // 将this加载到操作数栈顶
                     1: invokespecial #1                  // Method java/lang/Object."<init>":()V
                     4: aload_0                           // 加载this
                     5: getstatic     #2                  // 获取静态常量字段
                     8: dup                               // 复制上一个字段counter并且压入到栈顶
                     9: iconst_1                          // 将常量1压入栈顶, 当取-1~5入栈时使用iconst指令, 取值-128~127时采用bipush指令, 取值-32768~33767采用sipush, 其余采用ldc取值
                     10: iadd                             // 对两个操作数栈上的值进行+算数运算, 然后计算结果存入到操作数栈顶.
                     11: putstatic     #2                  // Field counter:I 将结果保存到运行时常量池里的一个静态字段
                     14: putfield      #3                  // Field id:I 将结果保存到运行时常量池里的一个字段. 从操作数栈获取两个操作数, count++结果和this, 再传入要保存的字段#3, id字段. 等同于this.id = (counter+1)
                     17: return
         */
    }

    private static int counter = 0;

    private int id = counter ++;

    public static int funa(int base) {
        return funa1() * base;

        /**
         *
         0: invokestatic  #4                  // Method funa1:()I, 执行funa1()方法, 并且将结果压入到栈顶
         3: iload_0                           // 加载局部变量0, base
         4: imul                              // 获取到堆栈两个操作数, 执行乘法操作, 将结果压入堆栈
         5: ireturn                           // 返回结果, 堆栈栈顶pop出栈顶结果, 然后将其压入到调用方法的操作栈中退出
         *
         */
    }

    public static int funa1() {
        return 10;
    }

    public static int funb() {
        return 100;
    }

    public static int func() {
        return func();
    }

    public static long fund(int a, int b, List<String> c) {
        long ret = 10000L;
        int j = 100;
        return ret;
    }

    public static List<String> fune() {
        return new ArrayList<>();
    }

    private int f;

    public void funf() {
        int f = 100;
        funa(f);
    }

    public static void main(String[] args) {
        int a = funa(10);
        int b = funb();
        int c = func();
        System.out.println(a+b + c);
    }
}
