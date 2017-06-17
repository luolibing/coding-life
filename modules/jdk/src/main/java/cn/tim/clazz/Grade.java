package cn.tim.clazz;

/**
 * Created by LuoLiBing on 16/7/25.
 */
public class Grade {

    static {
        System.out.println("static init Grade");
    }

    {
        System.out.println("instant init Grade");
    }
}
