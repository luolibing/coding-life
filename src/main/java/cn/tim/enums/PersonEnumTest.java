package cn.tim.enums;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by LuoLiBing on 16/9/5.
 */
public class PersonEnumTest {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {

        final Method getName = Person.class.getDeclaredMethod("getName", int.class);

        System.out.println(getName.invoke(null, 1));
    }
}
