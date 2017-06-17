package cn.tim.thinking.exception12;

import org.junit.Test;

/**
 * Created by LuoLiBing on 16/9/23.
 *
 * Throwable这个类被用来表示任何可以作为异常被抛出的类
 * Throwable分位两种类型: Error和Exception
 * Error用来表示编译时和系统错误(常见的有AssertionError断言错); Exception是可以被抛出的基本类型
 *
 * 运行时异常,有很多运行时异常java会自动抛出,例如NullPointerException等.运行时异常不需要再方法声明中定义出来,也可以定义出来,但是并不会强制使用try Catch
 * RuntimeException,不受检查异常,这种异常属于错误,将会被自动捕获. 不捕获运行时异常,该异常能直达Main函数,输出报告给System.error,退出前调用printStackTrace
 * 只能在代码中忽略RuntimeException及其子类的异常,其他类型异常处理由编译器强制实施.究其原因,RuntimeException代表的是编程错误
 */
public class ThrowableTest {

    public void f1() {
        throw new RuntimeException("错误");
    }

    @Test
    public void test1() {
        f1();
    }

    public static void main(String[] args) {
        new ThrowableTest().test1();
    }


    @Test
    public void test2() {
        tryCatchFinally1();
    }

    /**
     * try catch finally分别抛异常, 最终会把finally中的异常抛出, 因为最终要执行finally
     */
    public static void tryCatchFinally1() {
        try {
            throw new RuntimeException("异常1");
        } catch (Exception e) {
            throw new RuntimeException("异常2");
        } finally {
            throw new RuntimeException("异常3");
        }
    }


    /**
     * 因为在try之前已经将值保存到returnvalue中, 所以finally在进行改变也不会再对returnvalue有影响
     */
    @Test
    public void test3() {
        int i = tryCatchFinally2();
        System.out.println(i);
    }

    public static int tryCatchFinally2() {
        int i = 0;
        try {
            i++;
            return i;
        } finally {
            i = 4;
            // 而如果在finally中return, 会重新设置returnvalue,并且返回
            // return i;
        }
    }

    @Test
    public void test4() {
        Person person = tryCatchFinally3();
        System.out.println(person);
    }


    /**
     * 而如果返回的是引用对象, 这个地方finally里面能修改到对应的值
     * @return
     */
    public static Person tryCatchFinally3() {
        Person person = new Person(1, "luolibing");
        try {
            return person;
        } finally {
            person.name = "liuxiaoling";
            // 而如果在finally中return, 会重新设置returnvalue,并且返回
            // return i;
        }
    }

    static class Person  {
        private long id;
        private String name;

        public Person(long id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
