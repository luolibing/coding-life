package cn.tim.function.pro;

import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * javap -verbose cn/tim/function/pro/FunctionalPro1
 */
public class FunctionalPro1 {

    @FunctionalInterface
    public interface MyFunction<T> {
        void print(T t);
    }

    public void sayHello(String name, MyFunction<String> action) {
        action.print(name);
    }

    private static void lambda$0(String a) {
        System.out.println("print " + a);
    }

    final class $Lambda$1 implements MyFunction {
        @Override
        public void print(Object x) {
            lambda$0((String)x);
        }
    }

    /**
     * javap -p cn/tim/function/pro/FunctionalPro1
     * lambda会自动生成一个静态的私有方法
     * private static java.lang.Integer lambda$square$0(java.lang.Integer);
     */
    @Test
    public void function1() {
        sayHello("aaaaaaaaa", (t)-> {
            System.out.println("aa");
        });
        Stream.of(this.getClass().getDeclaredMethods())
                .map(Method::getName)
                .forEach(System.out::println);

        Class<?>[] clazz = FunctionalPro1.class.getClasses();
        System.out.println(Arrays.toString(clazz));
    }
}
