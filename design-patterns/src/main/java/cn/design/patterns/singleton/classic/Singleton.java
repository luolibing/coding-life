package cn.design.patterns.singleton.classic;

/**
 * Created by LuoLiBing on 16/5/17.
 */
public class Singleton {

    private Singleton() {}

    private static Singleton instance = null;

    public static Singleton getInstance() {
        if(instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

    public void sayHello() {
        System.out.println("hello!!! singleton");
    }

    public static void main(String[] args) {
        Singleton instance = Singleton.getInstance();
        instance.sayHello();
    }
}
