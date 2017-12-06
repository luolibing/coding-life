package cn.tim.thinking.initialize5;

/**
 * Created by LuoLiBing on 16/8/14.
 */
public class Person {

    static Window window = new Window();

    public static void print() {
        System.out.println("person print");
    }

    static Apple peel(Apple apple) {
        System.out.println(apple.getName());
        return apple;
    }
}
