package cn.hotspot.memory;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Created by LuoLiBing on 17/2/6.
 */
public class ObjectFrame {

    /**
     * 尽管类实例和数组都是对象, 但是JVM创建和操作他们使用了不同的操作指令:
     * 1 创建类对象使用: new
     * 2 创建数组对象使用: newarray, anewarray, multianewarray
     * 3 方法类字段(static),和访问实例字段使用: getfield, putfield, getstatic, putstatic
     * 4 将数组元素加载到操作数堆栈: baload, caload, saload, iaload, laload, faload, daload, aaload.
     * 5 将值从操作数堆栈存储到数组中: bastore, castore, sastore, iastor, iastor, fastore, dastore, aastore.
     * 6 获取数组的长度: arraylength
     * 7 验证类实例或者数组实例的属性: instanceof, checkcast
     *
     */
    public void object() {

    }


    /**
     * 对象的创建过程
     * 当JVM遇到一条new指令时, 首先将去检查这个指令的参数是否能在常量池中定位到一个类的符号引用, 并且检查这个符号引用代表的类是否已被加载,解析和初始化过.
     * 如果没有, 则必须先执行相应的类加载过程. 在通过类加载检查后, 接下来JVM将为新生对象分配内存. 对象所需内存的大小在类加载完成后便可以完全确定, 为对象分配空间等同于把一块确定大小的内存从JAVA堆中划分出来.
     *
     * 分配内存有两种方式:
     * 1 如果JAVA堆垃圾收集器带有压缩功能, 则使用"指针碰撞"的方式, 移动指针指定大小的距离, 即可分配出对应大小的空间. Serial, ParNew带有压缩功能的收集器
     * 2 如果JAVA堆垃圾收集器没有压缩功能, 使用的内存和未被使用的内存相互交错, 则使用"空闲列表"的方式标记处未使用内存, 当需要为对象分配内存时, 查找出一块足够大的空间划分给对象即可. CMS
     *
     * 解决分配内存的并发问题:
     * 1 对分配内存空间的动作进行同步处理--采用CAS配上失败重试的方式保证更新操作的原子性
     * 2 把内存分配的动作按照线程划分在不同的空间之中进行, 即每个线程在Java堆中预先分配一小块内存, 称为本地线程分配缓冲(TLAB). 线程就在自己线程的TLAB上分配, 只有TLAB用完需要分配新的TLAB时, 才需要同步锁定. -XX:+/-UseTLAB(默认是开启TLAB的)
     *
     * 内存分配完成后, JVM将分配的内存空间初始化为零值(不包括对象头), 如果使用TLAB, 这一工作过程也可以提前至TLAB分配时进行. 这保证了对象的实例字段在JAVA代码中可以不赋初值就可以直接使用. 程序能访问到这些字段的数据类型对应的零值
     * 接下来, 虚拟机要对对象的对象头(ObjectHeader)进行必要的设置, 例如对象所属的类的实例(class), 类的元数据信息, 对象的哈希码, 对象的GC分代年龄等. 不同的运行状态, 如是否启用偏向锁等, 对象头会有不同的设置方式.
     * 上面的工作都完成之后, 从JVM的视角来看, 一个新的对象已经产生了, 但是JAVA程序的角度来看, 对象创建才刚开始, 一般来说(由字节码是否跟随invokespecial指令所决定) <init>方法还没有执行, 所有的字段都还为零.
     *
     */
    public void creation() {

    }


    /**
     * 对象的布局分三部分
     * 1 对象头(Header)
     * 2 实例数据(Instance Data)
     * 3 对齐填充(Padding)
     *
     * 对象头又分成两部分
     * 1 对象自身运行时数据(Mark Word)(32位或者64位(未开启压缩指针)), 包括哈希码(25位), GC分代年龄(4bit), 锁状态标识, 线程持有的锁, 偏向线程ID, 偏向时间戳等. 考虑到JVM空间效率, Mark Word被设计成非固定的数据结构, 会根据对象状态复用存储空间
     * 2 类型指针
     *
     */
    public void layout() {

    }


    /**
     * 对象的访问定位:
     * 通过栈上的reference数据来操作堆上的具体对象.
     * 对象的访问方式有两种
     * 1 句柄访问, JAVA堆划分出一块内存作为句柄池, reference中存储的是句柄地址, 而句柄中包含了对象实例数据与类型数据的地址信息.
     * 2 直接指针, JAVA堆对象的布局中就必须考虑如何放置访问类型数据的相关信息, reference中存储的就是对象地址.
     *
     * 优劣势
     * 使用句柄访问优势, reference中存储的是稳定的句柄地址, 对象被移动(GC需要移动)时只会改变句柄中的实例数据指针, 而reference不需要修改
     * 使用直接指针又是, 最大好处就是速度更快, 节省了一次指针定位的时间开销. 对于对象的访问在JAVA非常频繁, 因此这类开销积少成多后也十分可观.
     *
     * hotspotVM默认是使用的直接指针访问.
     *
     */
    public void vistObject() {

    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("ObjectFrame finalize");
    }


    public static void main(String[] args) throws InterruptedException {
        ObjectFrame obj = new ObjectFrame();
        MemoryUtils obj2 = new MemoryUtils();
        obj = null;
        obj2 = null;
        System.gc();
        Thread.sleep(10000);
    }


    @Test
    public void reference() {
        WeakHashMap<Integer, String> map = new WeakHashMap<>();
        for(int i = 0; i < 1000; i++) {
            map.put(i, i + "-");
        }
        System.gc();
        System.out.println(map.size());
    }


    Map<Person, Integer> map = new HashMap<>();

    @Test
    public void memoryLeak() {
        Person p = new Person();
        System.out.println(p.hashCode());
        map.put(p, 1);
        p.age = 10;
        map.put(p, 2);
        System.out.println(p.hashCode());
        System.out.println(map.size());
    }

    class Person {
        private int age;
        private String name;
    }
}
