package cn.design.patterns.singleton.thread;

/**
 * Created by LuoLiBing on 16/6/3.
 */
public class Singleton1 {

    private final static Singleton1 uniqueInstance = new Singleton1();

    private Singleton1() {}

    public static Singleton1 getInstance() {
        return uniqueInstance;
    }
}
