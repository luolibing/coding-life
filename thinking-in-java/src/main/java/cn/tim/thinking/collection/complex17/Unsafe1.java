package cn.tim.thinking.collection.complex17;

import org.junit.Test;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Created by LuoLiBing on 17/3/17.
 */
public class Unsafe1 {

    @Test
    public void test1() throws NoSuchFieldException, IllegalAccessException {
        Field unsafeField = Unsafe.class.getDeclaredFields()[0];
        unsafeField.setAccessible(true);
        Unsafe unsafe = (Unsafe) unsafeField.get(null);
    }
}
