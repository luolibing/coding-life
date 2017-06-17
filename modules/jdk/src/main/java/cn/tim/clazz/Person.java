package cn.tim.clazz;

/**
 * Created by LuoLiBing on 16/7/25.
 */
public class Person implements PersonInterface {

    public final static Integer num = 50;

    public final static String className = Student.class.getName();

    static {
        System.out.println("static init person");
    }

    {
        System.out.println("instant init person");
    }


    public static void main(String[] args) throws ClassNotFoundException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Class<?> clazz1 = classLoader.loadClass("cn.tim.clazz.Grade");
        Class<?> clazz2 = classLoader.loadClass("cn.tim.clazz.Grade");
        System.out.println(clazz1.equals(clazz2));

        Class.forName("");
    }
}
