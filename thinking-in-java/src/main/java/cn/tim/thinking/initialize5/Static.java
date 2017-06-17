package cn.tim.thinking.initialize5;

import java.nio.file.Paths;
import java.nio.file.Path;

/**
 * Created by LuoLiBing on 16/8/14.
 */
public class Static {

    static {
        createPath();
    }

    private final static String path = "/share";

    public static Path createPath() {
        /**
         * 静态方法不能调用非静态方法,但是实例方法可以调用静态方法,要是在代码中出现大量的static方法,就该重新考虑自己的设计了.
         */
        return Paths.get(path);
    }

    public String getName() {
        return createPath().toString();
    }

    /**
     * 静态初始化只有在必要时刻才会进行.如果不创建对象也不引用静态对象,那么静态变量永远不会被创建.
     * 初始化的顺序是先静态对象(如果他们尚未因前面的对象创建过程而被初始化),而后是非静态对象.
     * @param args
     */
    public static void main(String[] args) {
        Person.print();
    }
}
