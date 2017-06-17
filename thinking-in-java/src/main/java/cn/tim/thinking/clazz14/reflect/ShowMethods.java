package cn.tim.thinking.clazz14.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

/**
 * Created by LuoLiBing on 16/9/28.
 *
 */
class ShowMethods {

    private static Pattern p = Pattern.compile("\\w+\\.");

    public static void main(String[] args) {
        printMethods(Class.class);
        ShowMethods showMethods = create(ShowMethods.class, new String[]{"luolibing"}, String.class);
        System.out.println(showMethods.name);
    }

    public static void printMethods(Class clazz) {
        Method[] methods = clazz.getMethods();
        Constructor[] ctors = clazz.getConstructors();
        for(Method method : methods) {
            System.out.println(p.matcher(method.toString()).replaceAll(""));
        }

        System.out.println();
        // 非public类编译器不会自动添加上默认的构造函数
        for(Constructor ctor : ctors) {
            System.out.println(p.matcher(ctor.toString()).replaceAll(""));
        }
    }

    private static <T> T create(Class<T> clazz, Object[] params, Class<?> ... paramTypes) {
        try {
            Constructor<T> constructor = clazz.getConstructor(paramTypes);
            return constructor.newInstance(params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String name;

    public ShowMethods(String name) {
        this.name = name;
    }
}
