package cn.tim.thinking.concurrent21;

/**
 * Created by LuoLiBing on 16/10/10.
 * 每个java application都有一个单独的Runtime实例用来与运行时环境进行对接
 * Runtime只能通过Runtime.getRuntime进行获取
 * Runtime类
 *  getRuntime()        Runtime类是单例类
 *  exit(status)        0表示正常退出,非0表示异常退出
 */
public class RuntimeDemo {

    public final static String str = "abc";

    static {
        System.out.println("abc");
    }

}

class Test {
    public static void main(String[] args) {
        System.out.println(RuntimeDemo.str);
    }
}
