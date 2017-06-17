package cn.tim.thinking.initialize5;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;

/**
 * Created by LuoLiBing on 16/8/15.
 */
public class TerminationCondition {

    public static void main(String[] args) {
        Book novel = new Book(true);
        novel.checkIn();
        // 没有引用,忘记了cleanup,对象没有适当清理的部分,程序就存在很隐晦的缺陷,
        // finalize并不总是被调用,finalize()可以用来发现这种情况. 调用的顺序是反过来的,先调用book的finalize,然后是Tank
        new Tank(true);
        new Book(true);
        // 强制进行终结动作.
        System.gc();

        final List<GarbageCollectorMXBean> garbageCollectorMXBeans = ManagementFactory.getGarbageCollectorMXBeans();
        for(GarbageCollectorMXBean b: garbageCollectorMXBeans) {
            System.out.println(b.getName());
        }
    }
}
