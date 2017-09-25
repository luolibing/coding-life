package cn.tim.clazz;

import org.junit.Test;

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

    @Test
    public void compactString() {
        String str = "world";
        System.out.println(str.getBytes().length);
    }
}
