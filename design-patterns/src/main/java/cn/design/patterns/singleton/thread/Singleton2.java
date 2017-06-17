package cn.design.patterns.singleton.thread;

/**
 * Created by LuoLiBing on 16/6/3.
 */
public class Singleton2 {

    /**
     * volatile关键词确保,当uniqueInstance变量被初始化成Singleton时,多个线程能正确地处理uniqueInstance变量
     */
    private volatile static Singleton2 uniqueInstance;

    private Singleton2() {}

    public static Singleton2 getInstance() {
        if(uniqueInstance == null) {
            synchronized (Singleton2.class) {
                if(uniqueInstance == null) {
                    uniqueInstance = new Singleton2();
                }
            }
        }
        return uniqueInstance;
    }
}
