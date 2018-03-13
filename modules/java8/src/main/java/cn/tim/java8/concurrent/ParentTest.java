package cn.tim.java8.concurrent;

import java.util.Vector;

/**
 * User: luolibing
 * Date: 2018/3/7 9:56
 */
public class ParentTest {
    public static void main(String[] args) {
        Parent<Object> parent = new Parent<>();
        Parent<Object>.Child ch1 = parent.iterator();
        Vector<Object> v1 = ch1.getObjectCollection();
        Parent<Object>.Child ch2 = parent.iterator();
        Vector<Object> v2 = ch2.getObjectCollection();
        System.out.println(v1 == v2);
        int a = 10, b = 20;
        a = b + (b = a) * 0;
        System.out.println("a = " + a + ", b = " + b);
    }
}
