package cn.tim.thinking.concurrent21.lock;

import org.junit.Test;
import sun.misc.Unsafe;
import sun.misc.VM;
import sun.reflect.Reflection;

import java.lang.reflect.Field;

/**
 * Created by LuoLiBing on 16/10/25.
 * Unsafe类提供了一个更底层的操作并且应该在受信任的代码中使用,可以通过内存地址存取fields,
 * 如果给出的内存地址是无效的那么会有一个不确定的运行表现
 *
 * http://www.cnblogs.com/mickole/articles/3757278.html
 *
 */
public class Unsafe1 {

    private static Unsafe1 unsafe1 = new Unsafe1();

    public Unsafe1() {}

    /**
     * 获取Unsafe的实例,需要判断类加载器是否是系统类加载器,如果不是被认定为不可信代码.
     * 因为unsafe累提供了一个底层操作,例如直接内存存取
     * @return
     */
    public static Unsafe1 getUnsafe() {
        Class var0 = Reflection.getCallerClass();
        if(!VM.isSystemDomainLoader(var0.getClassLoader())) {
            throw new SecurityException("Unsafe");
        } else {
            return unsafe1;
        }
    }



    /**
     * 指定静态field的内存地址偏移量,这个值可以作为访问特定field的一种方式,这个值对于给定field是唯一的
     * @param field
     * @return
     */
    public native long objectFieldOffset(Field field);



    /**
     * 在obj对象的偏移量offset位置比较integer field字段和期望的值,如果相同则更新为update.这个操作应该是原子操作,因此提供了一种不可中断的方式更新integer字段
     * @param obj
     * @param offset
     * @param expect
     * @param update
     * @return
     */
    public native boolean compareAndSwapInt(Object obj, long offset, int expect, int update);
    // 更新long
    public native boolean compareAndSwapLong(Object obj, long offset, long expect, long update);
    // 更新object
    public native boolean compareAndSwapObject(Object obj, long offset, long expect, long update);



    /**
     * volatile可见性,因为非volatile的变量设置值时一个有序或者延迟的putInvolatile方法,所以不能保证多线程的可见性
     *
     * 设置obj对象中offset偏移地址对应的整数字段的值为指定值, 这是一个有序或者有延迟的putInVolatile方法,并且不保证值的改变被其他线程立即看到.
     * 只有field被volatile修饰并且期望被意外修改的时候使用才有用
     * @param obj
     * @param offset
     * @param value
     */
    public native void putOrderedInt(Object obj, long offset, int value);
    public native void putOrderedLong(Object obj, long offset, int value);
    public native void putOrderedObject(Object obj, long offset, Object value);



    /**
     * 设置obj对象中offset偏移地址对应的整形field字段的值为制定值.支持volatile store语义,即可见性
     * @param obj
     * @param offset
     * @param value
     */
    public native void putIntVolatile(Object obj, long offset, int value);
    public native void putLongVolatile(Object obj, long offset, long value);
    public native void putObjectVolatile(Object obj, long offset, Object value);


    /**
     * 获取obj对象中offset偏移地址对应的整形field的值,支持volatile load语义. 可见性
     * @param obj
     * @param offset
     */
    public native void getIntVolatile(Object obj, long offset);
    public native long getLongVolatile(Object obj, long offset);


    /**
     * 设置obj对象中offset偏移地址对应的long型field的值为指定值
     * @param obj
     * @param offset
     * @param value
     */
    public native void putLong(Object obj, long offset, long value);

    /**
     * 获取obj对象中offset偏移地址对应的long型field的值
     * @param obj
     * @param offset
     */
    public native void getLong(Object obj, long offset);


    /**
     * 获取给定数组中第一个元素的偏移地址,为了存取数组中的元素,这个偏移地址和arrayIndexScale方法的非0返回值一起使用
     * @param arrayClass
     * @return
     */
    public native int arrayBaseOffset(Class arrayClass);

    /**
     * 获取用户给定数组寻址的换算因子,一个合适的换算因子不能返回的时候(例如基本类型)返回0,这个返回值能够与arrayBaseOffset()一起使用去存取数组class中的元素
     * @param arrayClass
     * @return
     */
    public native int arrayIndexScale(Class arrayClass);


    /**
     * 释放被park创建的在一个线程上的阻塞,这个方法也可以被使用来中止一个先前调用park导致的阻塞,这个操作操作时不安全,因此线程必须保证是活的,这是java代码不是native代码
     * @param thread
     */
    public native void unpark(Thread thread);

    /**
     * 阻塞一个线程直到unpark出现\或者线程被中断或者timeout时间到期.如果一个unpark调用已经出现了,这里只计数.timeout为0表示永不过期,isAbsolute为true时
     * @param isAbsolute
     * @param time
     */
    public native void park(boolean isAbsolute, long time);


    private static Unsafe unsafe;

    static {
        try {
            //通过反射获取rt.jar下的Unsafe类
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
        } catch (Exception e) {
            System.out.println("Get Unsafe instance occur error"+ e);
        }
    }

    @Test
    public void compareAndSwapInt() throws NoSuchFieldException {
        Target target = new Target();
        Field arg1 = Target.class.getDeclaredField("arg1");
        long offset = unsafe.objectFieldOffset(arg1);
        System.out.println("arg1 offset=" + offset);
        boolean b = unsafe.compareAndSwapInt(target, offset, 0, 10);
        System.out.println(b);
        System.out.println(target.arg1);
    }

    class Target {
        int arg1 = 0;
        int arg2;
        String str1;
        String str2;
    }
}
