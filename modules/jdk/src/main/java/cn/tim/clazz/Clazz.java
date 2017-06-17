package cn.tim.clazz;

import org.junit.Test;
import sun.misc.VM;

/**
 * Created by LuoLiBing on 16/7/25.
 */
public class Clazz {

    /**
     * Class对象,只允许jvm进行初始化,构造函数为private
     */
    @Test
    public void toGenericString() {
        /**
         * InnerClass.class,调用class方法并不会触发class的加载
         */
        System.out.println(Person.class.toGenericString());
        System.out.println(Person.class.toString());
        System.out.println(Person.class.toString());
        //System.out.println(Person.num);
        try {
            final Person person = Person.class.newInstance();
            System.out.println(person);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Class.forName(string) 加载类的一种简单方式
     * 类的载入包括三部分  1 loading(将class文件载入到类加载器)   2 链接linking处理类中对其他类引用的事情   3 initializing初始化调用静态块等等
     * @throws ClassNotFoundException
     */
    @Test
    public void classForName() throws ClassNotFoundException {
        Class.forName("cn.tim.clazz.Person");
//        System.out.println(Thread.currentThread().getContextClassLoader().getResource(""));
        // Class.forName(className,false,ClassLoader),false表示是否初始化initialize
        Class.forName("cn.tim.clazz.Person", false, Thread.currentThread().getContextClassLoader());
    }

    /**
     * VM.maxDirectMemory()获取最大的内存,默认缓存为64MB
     */
    @Test
    public void vm() {
        System.out.println(VM.maxDirectMemory() / 1024 / 1024 / 1024.0);
        System.out.println(VM.getFinalRefCount());
        System.out.println(VM.isBooted());
    }

}
