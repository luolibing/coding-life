package cn.hotspot.classloader;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
        public static int c = 10;

        static  {
            B.c = 20;
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

        System.out.println(B.c);
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

    class MyClassLoader extends ClassLoader {
        @Override
        public Class<?> loadClass(String name) throws ClassNotFoundException {
            System.out.println("load 玩玩" + name);
            return null;
        }
    }

    private Map<String, Object> parallLockMap = new ConcurrentHashMap<>();

    private Object getClassLoadLock(String name) {
        Object newLock = new Object();
        // 当不存在的时候进行保存，存在的话，返回当前的lock对象
        Object lock = parallLockMap.putIfAbsent(name, newLock);
        // 为空，表明没有被占用过
        if(lock == null) {
            lock = newLock;
        }
        return lock;
    }

    class CustomerClassLoader extends ClassLoader {
        @Override
        protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
            synchronized (getClassLoadLock(name)) {
                Class<?> clazz = findLoadedClass(name);
                if(clazz == null) {
                    ClassLoader parent = getParent();
                    try {
                        if (parent != null) {
                            // clazz = parent.loadClass(name, false);
                        } else {
                            // clazz = findBootstrapClassOrNull(name);
                        }
                    } catch (Exception e) {

                    }
                }

                if(resolve) {
                    resolveClass(clazz);
                }
                return clazz;
            }
        }
    }

    class ClassLoader1 extends ClassLoader {
        @Override
        public Class<?> loadClass(String name) throws ClassNotFoundException {
            return super.loadClass(name, true);
        }
    }

    @Test
    public void load() throws ClassNotFoundException {
        new MyClassLoader().loadClass("java.lang.Object");
    }

    @Test
    public void notLinkingLoad() throws ClassNotFoundException {
        Class<?> clazz = new ClassLoader1().loadClass("cn.hotspot.gc.ReferenceCountingGC");
        System.out.println(clazz);
    }
}
