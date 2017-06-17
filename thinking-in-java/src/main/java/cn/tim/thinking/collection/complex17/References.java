package cn.tim.thinking.collection.complex17;

import org.junit.Test;

import java.lang.ref.*;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by LuoLiBing on 17/1/5.
 * 持有引用
 * https://www.oschina.net/question/12_8662
 *
 */
public class References {

    /**
     * Reference类: 定义了所有引用对象的公共操作, 这些引用对象实现得与垃圾回收紧密相关.
     *
     * 一个引用实例的四个内部状态
     *
     * 1 Active(活动状态)
     *  受到垃圾回收特殊处理,收集器检测到指示对象的可达性已经改变到适当状态之后的一段时间,
     *  它会将实例的状态更改为Pending(未定的)或者Inactive(无效的), 具体取决于实例是否在创建队列时注册到队列中.
     *  在前一种情况下, 它还将实例添加到挂起引用列表. 新创建的实例为"active(活动)"状态
     *
     *  2 Pending(未定的)
     *   等待引用列表中的一个元素, 等待引用处理程序线程入队. 未注册的实例从不处于这个状态.
     *
     *  3 Enqueued(排队)
     *   实例在创建时注册的队列元素. 当一个实例从它的ReferenceQueue中删除时, 它被设置为Inactive(无效)状态. 未注册的实例从不处于这个状态.
     *
     *  4 Inactive(无效)
     *   没有更多的事情, 一个实例一旦成为无效状态, 它的状态不会再变化.
     *
     * 状态编码按照如下方式:
     *
     * Active   queue = ReferenceQueue 已经注册的实例队列, 没有注册为ReferenceQueue.NULL, 则next = null
     * Pending  queue = ReferenceQueue 已经注册的实例队列, next = this
     * Enqueued queue = ReferenceQueue.ENQUEUED; next = 队列中的实例, 或者队尾实例
     * Inactive queue = ReferenceQueue.NULL; next = this;
     *
     * 如果使用这种方案, GC只需检查next字段, 以确定引用实例是否需要特殊处理: 如果next=null, 则实例处于(Active)活动状态; 如果next!=null, 则GC应该正常处理实例.
     * 为了确保并发GC可以发现活动引用对象而不干扰可能对这些对象应用enqueue()方法的应用程序线程, GC应该通过discovered字段链接被发现的对象. discovered的字段还用于链接挂起的list中的引用对象.
     *
     * lock字段
     * 用于与GC同步的对象, 收集器必须在每个收集周期开始时获取此锁. 因此保持此锁的任何代码尽可能快地完成, 不分配任何新对象并避免调用用户代码是至关重要的(JVM暂停, 以方便在标记阶段不会发生对象的分配和用户代码的调用).
     *
     * pending字段
     * 等待排队的引用列表. GC将引用添加到此pending列表, 而引用处理程序线程删除它们. 此列表受lock字段对象的保护. 该列表使用discovered字段链接其元素
     *
     */


    /***
     * 强引用, 软引用, 弱引用, 虚引用
     * Reference的子类: 由强到弱, 对应不同级别的"可到达性"
     * 强引用                不会被GC回收, 并且没有实际的对应类型. 普通的引用例如Object obj = new Object()就是一个强引用
     * SoftReference        内存敏感的高速缓存(内存不足时才会被回收), 软引用一般用作不限大小的cache
     * WeakReference        规范映射, 不妨碍GC回收映射的"键或值". "规范映射"中的对象实例可以在程序的多处被同时使用, 以节省空间(一旦被GC发现, 将会释放WeakReference所引用的对象)
     * PhantomReference     调度回收前的清理工作.(虚引用, 一旦发现, 会将PhantoReference对象插入ReferenceQueue队列, 而此时所指向的对象并没有被GC回收, 要等到ReferenceQueue被你真正的处理后才会被回收)
     * 当GC正在考察的对象只能通过某个Reference对象才"可获得"时, 上述这些不同的派生类为垃圾收集器提供了不同级别的间接性指示.
     * 使用SoftReference和WeakReference, 可以选择是否要将他们放入到ReferenceQueue.
     *
     * 对象"可获得(reachable可到达)", 是指这个对象可在程序中的某处找到.
     * 1 这意味着你在栈中有一个普通的引用, 而它正指向此对象
     * 2 也可能是你的引用指向某个对象, 而那个对象含有另一个引用指向正在讨论的对象; 也可能有更多的中间链接.
     * 如果一个对象是"可获得的", GC就不能释放它, 因为它仍然为你的程序所用. 如果一个对象不是"可获得的", 那么你的程序将无法使用到它, 所以将其回收是安全的.
     * 如果想继续持有某个对象的引用, 同时又希望能够允许GC释放它, 这时就应该使用Reference对象. 这样可以继续使用该对象, 而在内存消耗殆尽的时候又允许释放该对象.
     *
     */


    static class VeryBig {
        private final static int SIZE = 10000;

        private long[] la = new long[SIZE];

        private String ident;

        public VeryBig(String id) {
            this.ident = id;
        }

        @Override
        public String toString() {
            return ident;
        }

        @Override
        protected void finalize() throws Throwable {
            System.out.println("Finalizing " + ident);
        }
    }


    static class ReferenceObj {

        /**
         * ReferenceQueue, 是一个当weak, soft, phantom的referent被GC回收后, 提供事件回调的接口. 需要在实例化的时候传入构造函数.
         *
         * 回调过程:
         * GC回收referent后(phantom在回收前, finalize后), 将reference enqueue到RQ中, 程序通过调用RQ的remove()方法来感知reference被GC回收的事件, remove方法是阻塞的, 当没有referent的时候会被挂起.
         *
         */
        private static ReferenceQueue<VeryBig> queue = new ReferenceQueue<>();

        public static void checkQueue() {
            Reference<? extends VeryBig> inq = queue.poll();
            if(inq != null) {
                System.out.println("In queue: " + inq.get());
            }
        }

        public static void main(String[] args) {
            int size = 10;
            LinkedList<SoftReference<VeryBig>> sa = new LinkedList<>();
            for(int i = 0; i < size; i++) {
                // 在对象被回收时, 会将引用加入到queue中, 以便回调, 这里SoftReference并没有被回收
                sa.add(new SoftReference<>(new VeryBig("Soft" + i), queue));
                System.out.println("Just created: " + sa.getLast());
                checkQueue();
            }

            LinkedList<WeakReference<VeryBig>> wa = new LinkedList<>();
            for(int i = 0; i < size; i++) {
                wa.add(new WeakReference<>(new VeryBig("Weak" + i), queue));
                System.out.println("Just created: " + wa.getLast());
                checkQueue();
            }

            SoftReference<VeryBig> s = new SoftReference<>(new VeryBig("Soft"));

            // WR引用的作用, 一般是为referent提供一个被回收的凭据, 结合ReferenceQueue可以在第一时间得到reference被回收的事件, 从而做一些额外的clean操作.
            WeakReference<VeryBig> w = new WeakReference<>(new VeryBig("Weak"));

            System.gc();
            System.out.println("after system.gc()");

            // Phantom虚引用会在当除了虚引用没有其他任何引用时, 将虚引用放入ReferenceQueue当中, 以便程序在另一边通过queue的remove/poll方法, 感知reference被GC回收的事件
            LinkedList<PhantomReference<VeryBig>> pa = new LinkedList<>();
            for(int i = 0; i < size; i++) {
                pa.add(new PhantomReference<>(new VeryBig("Phantom" + i), queue));
                System.out.println("Just created: " + pa.getLast());
                // Phantom Reference不允许通过get()获取到对象自身, 而且虚引用会在当除了虚引用之后没有其他引用的时候, 直接入队
                checkQueue();
            }

            // 高速缓存softReference在System.gc()后, 并没有被回收掉
            for(SoftReference<VeryBig> ref : sa) {
                System.out.println(ref.get());
            }

            // WeakReference却已经被回收了
            for(WeakReference<VeryBig> ref : wa) {
                System.out.println(ref.get());
            }

            System.out.println(s.get());
        }
    }


    public void test() {
        VeryBig veryBig = new VeryBig("ref1");
    }

    @Test
    public void reference() {
        test();
        System.gc();
        System.out.println("aaaa");
    }


    @Test
    public void phantomReference() throws InterruptedException {
        // ReferenceQueue队列
        ReferenceQueue referenceQueue = new ReferenceQueue();
        Object object = new Object() {
            @Override
            public String toString() {
                return "Referented Object";
            }

//            @Override
//            protected void finalize() throws Throwable {
//                System.out.println("Referented Object finalize");
//            }
        };

        Object data = new Object() {
            @Override
            public String toString() {
                return "Data";
            }

            @Override
            protected void finalize() throws Throwable {
                System.out.println("Data Object finalize");
            }
        };

        Thread th = new Thread(() -> {
            try {
                Reference reference = referenceQueue.remove();
                System.out.println(reference + " event fired.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        th.setDaemon(true);
        th.start();

        HashMap map = new HashMap();
        Reference reference = null;
        System.out.println("Testing PhantomReference");
        // 虚引用, GC遍历引用关系时, 如果发现虚引用除phantom外没有其他任何引用, GC会将phantom referent放入到Reference Queue当中. GC在回收对象前会先调用对象自身的finalize()方法, 然后再回调enqueue, 最后再进行回收
        reference = new PhantomReference(object, referenceQueue);

        // 将reference与data在map中进行关联
        map.put(reference, data);

        // 通过虚引用无法获取到引用的对象
        System.out.println(reference.get());
        System.out.println(map.get(reference));
        System.out.println(reference.isEnqueued());
        System.gc();

        System.out.println(reference.get());
        System.out.println(map.get(reference));
        // 因为虚引用Object还有一个obj指向, 所以不会被回收
        System.out.println(reference.isEnqueued());

        // 将对象与引用解除关联
        object = null;
        data = null;

        System.gc();
        System.out.println(reference.get());
        System.out.println(map.get(reference));
        Thread.sleep(15000);
        // 如果没有实现finalize()方法, 这个地方会直接返回true. 而如果实现了finalize()方法, 再调用enqueue(), 最后再进行回收, (这个地方会返回false, 但是不知道晚上没有入队)!!!!
        System.out.println(reference.isEnqueued());
    }


    /**
     * 回调
     * GC回收referent后(phantom是在回收前, finalize后), 将reference enqueue到RQ当中. 然后程序可以通过RQ的remove方法来感知GC回收事件. remove()方法是阻塞的. poll是非阻塞的.
     * @throws InterruptedException
     */
    @Test
    public void referenceQueueTest() throws InterruptedException {
        ReferenceQueue q = new ReferenceQueue();
        // 这个地方并不能使用"Jack", 而应该使用new String("Jack");
        String str = new String("Jack");
        WeakReference wr = new WeakReference(str, q);

        Thread th = new Thread(() -> {
            try {
                Reference reference = q.remove();
                System.out.println(reference + " event fired");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        th.setDaemon(true);
        th.start();
        System.out.println("Reference Queue is listening.");

        // 去掉强引用关联
        str = null;
        System.out.println("Ready to gc");
        System.gc();

        Thread.sleep(4000);
        // 引用被回收了
        System.out.println("wr.get: " + wr.get());
    }
}
