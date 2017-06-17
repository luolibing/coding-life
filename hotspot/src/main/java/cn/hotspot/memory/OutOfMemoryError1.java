package cn.hotspot.memory;

import org.junit.Test;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LuoLiBing on 17/2/7.
 * java虚拟机规范中, 除了PC程序计数器外, 其他运行时区域都有发生OOM异常的可能.
 */
public class OutOfMemoryError1 {

    static class OOMObject {

    }

    /**
     * VM Args: -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
     * 堆内存溢出
     * 不断创建新对象, 并且保证GC Roots到对象之间可达避免垃圾回收机制清楚对象, 最终会产生堆内存溢出.
     *
     */
    @Test
    public void heapOOM() {
        List<OOMObject> list = new ArrayList<>();
        while (true) {
            list.add(new OOMObject());
        }
    }


    /**
     *
     * 虚拟机栈和本地方法栈溢出
     * 对于HotSpot并不区分虚拟机栈和本地方法栈, 虽然-Xoss参数存在, 但没啥用处. 栈容量通过-Xss参数设置.
     * 关于虚拟机栈和本地方法栈, JVM规范描述了两种异常
     * 1 如果线程请求的栈深度大于虚拟机所允许的最大深度, 将抛出StackOverflowError异常
     * 2 如果虚拟机在扩展栈时无法申请到足够的内存空间, 则抛出OutOfMemoryError异常.
     * VM Args: -Xss256k
     */
    @Test
    public void stackOOM() {
        try {
            stackOOM();
        } catch (Exception e) {
            System.out.println("stack length: " + stackLength);
            throw e;
        }
    }


    @Test
    public void dontStop() {
        while (true) {
            new Thread(() -> {
                while (true) {

                }
            }).start();
        }
    }

    private int stackLength = 1;

    public void stackLeak() {
        stackLength ++;
        stackLeak();
    }


    /**
     * 运行时常量池
     * 使用-XX:PermSize和-XX:MaxPermSize限制方法区大小, 从而简洁限制其中常量池的容量.
     * 但是在JAVA8中, 使用了MetaSpace本地内存来代替持久区, 所以再使用-XX:PermSize设置会得到警告不再支持, 这个时候可以使用MaxMetaspaceSize来设置元空间来设置大小.
     */
    @Test
    public void runtimeConstantPoolOOM1() {
        List<String> list = new ArrayList<>();
        int i = 0;
        while (true) {
            list.add(String.valueOf(i++).intern());
        }
    }

    @Test
    public void runtimeConstantPoolOOM2() {
        // jdk1.6中运行两个都是false, 在jdk1.7中运行为true, false
        String str1 = new StringBuilder("计算机").append("班").toString();
        // jdk1.6中intern()会把首次遇到的字符串实例复制到永久代, 返回的也是永久代字符串实例的引用, 而StringBuilder创建的字符串实例在Java堆上
        // jdk1.7中Intern()不再复制实例, 只是在常量池中记录首次出现的实例引用. 所以会返回true
        System.out.println(str1.intern() == str1);

        // java已经出现过,不符合首次出现, 所以会返回false
        String str2 = new StringBuilder("ja").append("va").toString();
        System.out.println(str2.intern() == str2);
    }

    /**
     * 使用CGLIB代理方式, 撑爆MetaSpace
     * -XX:MaxMetaspaceSize=6M
     */
    @Test
    public void javaMethodAreaOOM() {
        while (true) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(OOMObject.class);
            enhancer.setUseCache(false);
            enhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy) -> methodProxy.invokeSuper(o, objects));
            enhancer.create();
        }
    }


    /**
     * DirectByteBuffer分配内存也会抛出OOM, 但是它抛出异常时并没有真正向操作系统申请分配内存, 而是通过计算得知内存无法分配, 于是手动抛出异常
     * 真正申请分配内存的方法是unsafe.allocateMemory()
     * @throws IllegalAccessException
     */
    @Test
    public void directMemoryOOM() throws IllegalAccessException {
        Field unsafeField = Unsafe.class.getDeclaredFields()[0];
        unsafeField.setAccessible(true);
        Unsafe unsafe = (Unsafe) unsafeField.get(null);
        while (true) {
            unsafe.allocateMemory(1024 * 1024);
        }
    }
}
