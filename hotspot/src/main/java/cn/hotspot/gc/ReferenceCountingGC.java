package cn.hotspot.gc;

/**
 * Created by LuoLiBing on 17/2/8.
 * GC垃圾回收的三个问题
 * 1 哪些内存需要回收
 * 2 什么时候回收
 * 3 如何回收
 *
 * 程序计数器, 虚拟机栈, 本地方法栈这三个区域的生命周期与线程生命周期相同. 栈中的栈帧的生命周期与方法调用一样, 对应着入栈与出栈. 这些区域内存分配和回收具有确定性.
 * 而方法区和java堆则不一样, 一个接口的多个实现需要的内存可能不一样, 一个方法的不同分支内存也可能不一样, 只有运行时才知道需要创建那些对象, 这部分内存的分配和回收都是动态的.
 *
 * 判断对象是否存活的依据
 * 1 引用计数算法, 简单易实现, 但是解决不了循环引用的问题
 * 2 可达性分析算法, 通过GC Roots对象向下搜索, 走过的路径叫做引用链, 当一个对象到GC Roots没有任何引用链相连, 就说GC Roots到这个对象不可达, 不可达对象即可判定为可回收对象.
 *   GC Roots对象包含以下几种
 *   1 局部变量表中的引用的对象
 *   2 方法区中类静态属性引用的对象
 *   3 方法区中常量引用的对象
 *   4 本地方法栈中JNI(native)引用的对象
 *
 * 引用(Reference)分类
 * 1 强引用
 * 2 软引用
 * 3 弱引用
 * 4 虚引用
 *
 * 回收方法区
 * 方法区回收"性价比"低
 * 回收的内容包括: 废弃常量和无用的类. 判断方式与Java堆类似也是判断是否有引用Reference指向对应的对象.
 * 判断废弃常量相对简单, 而要判定无用的类条件相对苛刻.
 *
 * 无用的类的判定条件, 同时满足一下3个条件, 才可以(可能不回收)进行回收.
 * 是否对类进行回收, HotSpot提供了-Xnoclassgc参数进行控制, 还可以使用-verbose:class以及 -XX:+TraceClassLoading, -XX:+TraceClassUnLoading(FastDebug版本)查看类加载卸载日志
 * 1 该类的所有实例都已经被回收
 * 2 加载该类的ClassLoader已经被回收
 * 3 该类对应的java.lang.Class对象没有在任何地方被引用, 无法在任何地方通过反射访问该类的方法.
 * 大量使用反射, 动态代理, CGLIB等ByteCode框架, 动态生成JSP以及OSGI这类频繁自定义ClassLoader的场景都需要具备类卸载的功能.
 *
 */
public class ReferenceCountingGC {

    public Object instance = null;

    private static final int _1MB = 1024 * 1024;

    private byte[] bigSize = new byte[2 * _1MB];

    public static void main(String[] args) {
        ReferenceCountingGC objA = new ReferenceCountingGC();
        ReferenceCountingGC objB = new ReferenceCountingGC();
        objA.instance = objB;
        objB.instance = objA;

        objA = null;
        objB = null;
        System.gc();
    }

    static class FinalizeEscapeGC {

        private static FinalizeEscapeGC SAVE_HOOK = null;

        public void isAlive() {
            System.out.println("yes, i am still alive :");
        }

        @Override
        protected void finalize() throws Throwable {
            super.finalize();
            System.out.println("finalize method executed!");
            SAVE_HOOK = this;
        }

        public static void main(String[] args) throws InterruptedException {
            SAVE_HOOK = new FinalizeEscapeGC();

            // 将SAVE_HOOK置为null, 回收
            SAVE_HOOK = null;
            System.gc();

            Thread.sleep(500);
            // 第一次因为覆盖了finalize()方法, 并且还没调用过所以能够成功逃脱
            if (SAVE_HOOK != null) {
                SAVE_HOOK.isAlive();
            } else {
                System.out.println("no, i am dead:");
            }


            SAVE_HOOK = null;
            System.gc();

            Thread.sleep(500);
            // 第二次因为finalize()方法已经调用过, 所以这次会被直接回收
            if (SAVE_HOOK != null) {
                SAVE_HOOK.isAlive();
            } else {
                System.out.println("no, i am dead:");
            }
        }
    }
}
