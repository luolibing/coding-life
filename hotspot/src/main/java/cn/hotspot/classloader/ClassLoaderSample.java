package cn.hotspot.classloader;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luolibing on 2019/4/25.
 */
public class ClassLoaderSample {

    public static class A {
        static {
            System.out.println("A");
        }

        // 静态变量还是不一样的
        public static int a = 10;

        // 常量系统会优化
        public static final int b = 10;
    }

    public static class B extends A {
        static  {
            System.out.println("B");
        }
    }

    public static void main(String[] args) {
        // 被动引用
        System.out.println(A.class);
        System.out.println(A.b);
        A[] array = new A[10];

        // 只会加载静态变量所在类
        System.out.println(B.b);
        System.out.println(B.a);

    }

    @Test
    public void test() throws InterruptedException {
        int MB = 1024 * 1024;
        List list = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            list.add(new int[10 * MB]);
        }

        for(int i = 0; i< 5; i++) {
            list.remove(0);
        }
        System.gc();

        Thread.sleep(100000);

    }
}
