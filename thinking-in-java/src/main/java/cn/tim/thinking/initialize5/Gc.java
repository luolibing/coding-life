package cn.tim.thinking.initialize5;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Created by LuoLiBing on 16/8/15.
 * 垃圾回收器只知道释放那些经由new分配的内存,所以它不知道非new获得的特殊内存区域,所以无法释放这一块内存.
 * 为了应对这种情况,java允许使用finalize()方法.
 * finalize()方法的原理:
 * 一旦垃圾回收器准备好释放对象占用的存储空间,将首先调用其finalize()方法,并且在下一次垃圾回收动作发生时,才会真正回收对象占用的内存.
 *
 * java里的对象并非总是被垃圾回收
 * 1 对象可能不被垃圾回收
 * 2 垃圾回收并不等于析构
 * 3 垃圾回收只与内存有关
 *
 * finalize方法的应用场景
 * 由于分配内存时可能采用了类似c语言中分配内存的做法(例如本地方法),而非java中通常做法
 * 所以清理的时候也得使用本地方法才能释放这一块内存,否则存储空间得不到释放,从而内存泄漏
 *
 * finalize方法确实不是进行普通的倾全力工作的合适场所,只是特殊情况下才进行调用
 *
 * 无论是垃圾回收还是终结,都不保证一定会发生,如果jvm并未面临内存耗尽的情形,它是不会浪费时间去执行垃圾回收以恢复内存的.
 */
public class Gc {

    private Unsafe unsafe;

    private long address;

    private int id = 0;

    public Gc(int id) throws IllegalAccessException {
        this.id = id;
        Field unsafeField = Unsafe.class.getDeclaredFields()[0];
        unsafeField.setAccessible(true);
        this.unsafe = (Unsafe) unsafeField.get(null);
    }

    /**
     * java虚拟机中,堆的实现像一个传送带,每分配一个新对象,它就往前移动一格.这样对象存储空间的分配速度非常快.java堆指针只是简单地移动到尚未分配的区域.
     * 垃圾回收一面回收空间,一面使堆中的对象紧凑排列,这样堆指针就可以很容易移动到更靠近传送带的开始处,也就尽量避免了页面错误.通过垃圾回收器对对象重新排列,
     * 实现了一种高速的\有无线空间可供分配的堆模型.
     *
     * 垃圾回收的方式:
     * 1 引用计数,每个对象都含有一个引用计数,当有引用连接至对象时,引用计数+1,反之则-1.gc遍历全部对象,发现计数为0时,释放空间.无法解决循环引用.
     *      一般不用,只是用来说明垃圾回收的工作方式
     * 2 停止-复制,从堆栈和静态存储存储区开始,遍历所有引用,找出所有活的对象.停止复制,会先暂停程序的运行,然后将所有存活的对象从当前堆复制到另一个堆,剩下的全都是垃圾.要是没什么垃圾,甚至没有垃圾停止复制效率会很低,这个时候回自适应切换成标记清扫
     *   标记-清扫,同样是从堆栈和静态存储区开始,遍历所有引用,找到一个存活对象进行标记,全部标记完成之后再进行清理,剩下的堆空间是不连续的,要是碎片很多,又会切换回停止-复制模式
     *   这就是"只适应"技术,可以称其为:"自适应的\分代的\停止-复制\标记-清扫式垃圾回收器"
     *
     * 并发标记清除(Concurrent Mark-Sweep 简称CMS)和新的垃圾优先(Garbage First 简称G1)收集器
     *
     * 内存区域
     * PSYoungGen   年轻代
     *  eden space
     *  from space
     *  to space
     *
     * ParOldGen    老年代
     *  Object space
     *
     * MetaSpace PermGen
     *  class space
     *
     * 年轻代收集        当eden满时会出发一次年轻代收集
     * 完全收集         当老年代空间不够时,会出发一次完全收集,压缩
     * 安全点           线程不可能为了GC说停就停,他们会给GC流出特定的位置-安全点.常用的安全点是方法被调用的地方.
     *
     * 并发标记清除
     * 1    某种世界停转(简称STW)的暂停是不可避免的
     * 2    GC子系统绝对不能漏掉存活对象,这样做会导致JVM垮掉
     * 3    只有所有的应用线程都为整体收集暂停下来,才能保证收集所有的垃圾
     *
     * 垃圾优先 G1
     * G1的核心思想是暂停目标,也就是程序在执行时能为GC暂停多长时间.
     * G1把堆分成大小相同的区域,不区分年轻区和老年区,暂停时,对象呗撤到其他区域,清空的区域被放回到空白区自由列表上.
     *
     *
     * @throws IllegalAccessException
     */
    public void allocateMemory() throws IllegalAccessException {
        this.address = unsafe.allocateMemory(1000);
        System.out.println(address);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println(id + "gc finalize memory:" + address);
        if(address != 0) {
            unsafe.freeMemory(address);
        }
    }

}
