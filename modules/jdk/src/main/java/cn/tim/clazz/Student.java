package cn.tim.clazz;

/**
 * Created by LuoLiBing on 16/7/25.
 */
public class Student extends Person {

    public final static Integer num = 50;

    static {
        System.out.println("static init student");
    }

    {
        System.out.println("instant init student");
    }
}
