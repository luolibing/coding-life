package cn.tim.thinking.collection.complex17;

import cn.tim.thinking.array.TestArrayData;
import org.junit.Test;

import java.util.*;

/**
 * Created by LuoLiBing on 16/12/26.
 * Java5新增的容器API
 * 1 Queue接口, LinkedList已经为实现该接口做了修改, 及其实现PriorityQueue优先队列, 以及各种风格的BlockingQueue阻塞队列
 * 2 ConcurrentMap接口及其实现ConcurrentHashMap,多线程机制
 * 3 CopyOnWriteArrayList和CopyOnWriteArraySet
 * 4 EnumSet和EnumMap
 * 5 Collections
 *
 *
 */
public class CollectionDemo1 {

    /**
     * 填充容器
     */
    class StringAddress {
        private String s;
        public StringAddress(String s) {
            this.s = s;
        }

        @Override
        public String toString() {
            return super.toString() + " " + s;
        }
    }


    /**
     * 用对单个对象的引用来填充Collection的方式:
     * 1 使用Collections.nCopies()
     * 2 使用Collections.fill()
     */
    @Test
    public void fillingLists() {
        List<StringAddress> list = new ArrayList<>(Collections.nCopies(4, new StringAddress("Hello")));
        System.out.println(list);
        // fill直接将new StringAddress()替换掉这个list的所有元素
        Collections.fill(list, new StringAddress("World!"));
        System.out.println(list);
    }


    /**
     * 使CollectionData继承自ArrayList, 这样CollectionData也是一个List容器
     * 泛型遍历方法可以减少在使用类时所必需的类型检查数量.
     * @param <T>
     */
    static class CollectionData<T> extends ArrayList<T> {
        // 将Generator适配到ArrayList, 这是一个适配器的案例
        public CollectionData(TestArrayData.Generator<T> gen, int quantity) {
            // 初始化的时候直接将获取到的数据保存到自身List当中
            for(int i = 0; i < quantity; i++) {
                add(gen.next());
            }
        }

        public static <T> CollectionData<T> list(TestArrayData.Generator<T> gen, int quantity) {
            return new CollectionData<>(gen, quantity);
        }
    }

    class Government implements TestArrayData.Generator<String> {

        String[] foundation = ("strange women lying in ponds distributing swords is no basis for a system of government").split(" ");

        private int index;

        @Override
        public String next() {
            return foundation[index++];
        }
    }


    /**
     * Collection都具有将另一个Collection作为参数进行构造的方法, 在构造方法中调用addAll()方法.
     * LinkedHashSet元素的顺序与他们的插入顺序相同, 因为LinkedHashSet维护了保持插入顺序的链表
     */
    @Test
    public void collectionDataTest() {
        Set<String> set = new LinkedHashSet<>(new CollectionData<>(new Government(), 10));
        set.addAll(CollectionData.list(new Government(), 15));
        System.out.println(set);
    }

    @Test
    public void collectionDataGeneration() {
        System.out.println(new ArrayList<>(CollectionData.list(new TestArrayData.RandomGenerator.Integer(), 10)));
        System.out.println(new HashSet<>(CollectionData.list(new TestArrayData.RandomGenerator.Boolean(), 10)));
    }

    @Test
    public void stamp() {
        System.out.println(System.currentTimeMillis());
    }
}
