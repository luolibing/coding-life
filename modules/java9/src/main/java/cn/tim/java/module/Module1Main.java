package cn.tim.java.module;

/**
 * 模块，在运行时指定运行对应模块中的类，解决了classpath下同名类的冲突问题。
 * java --module-path modulePath -m moduleName/cn.tim.java.module.Module1Main
 * User: luolibing
 * Date: 2017/9/22 17:21
 */
public class Module1Main {

    public static void main(String[] args) {
        System.out.println("Greetings!");
    }
}
