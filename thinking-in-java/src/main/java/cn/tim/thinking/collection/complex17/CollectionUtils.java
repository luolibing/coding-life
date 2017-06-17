package cn.tim.thinking.collection.complex17;

import org.junit.Test;

import java.util.*;

/**
 * Created by LuoLiBing on 17/1/4.
 */
public class CollectionUtils {

    static class Parent {
        private static int counter = 0;

        private int id = counter ++;

        @Override
        public String toString() {
            return "Parent" + id;
        }
    }

    static class Sub extends Parent {
        private static int counter = 0;

        private int id = counter ++;

        @Override
        public String toString() {
            return "Sub" + id;
        }
    }


    /**
     * Collections.checkedList()
     *
     * 当将list的泛型去掉时, 这个时候list可以添加任何类型的元素
     * 而Collections.checkedList()对插入的元素进行判断, 当插入元素不属于对应的类型时抛出异常.
     */
    @Test
    public void check() {
        List list = Collections.checkedList(new ArrayList<>(), Parent.class);
        list.add(new Sub());
        list.add(new Parent());

        // 故意省略掉泛型, 这个时候就可以添加其他的元素了.
        list = new ArrayList<>();
        list.add(new Parent());
        list.add("aaa");
    }


    /**
     * Collections.max()
     *
     * 返回集合中最大的值
     */
    @Test
    public void max() {
        Integer max = Collections.max(Arrays.asList(1, 5, 2, 4, 10, 9));
        System.out.println(max);

        // 加入自定义的Comparator
        max = Collections.max(Arrays.asList(1, 5, 2, 4, 10, 9), (f, s) -> f < s ? 1 : (f.equals(s) ? 0 : -1));
        System.out.println(max);
    }


    /**
     * Collections.indexOfSubList()
     *
     * 在一个list中搜索子list的开始位置, 如果找不到时返回-1
     */
    @Test
    public void indexOfSubList() {
        int index = Collections.indexOfSubList(Arrays.asList(1, 5, 2, 4, 5, 2), Arrays.asList(5, 2));
        System.out.println(index);
    }


    /**
     * Collections.replaceAll()
     *
     * 替换所有
     */
    @Test
    public void replaceAll() {
        List<Integer> list = Arrays.asList(1, 5, 2, 4, 5, 2);
        Collections.replaceAll(list, 2, 200);
        System.out.println(list);
    }


    /**
     * Collections.reverse() 倒序
     * Collections.reverseOrder() 倒序排序
     *
     */
    @Test
    public void reverse() {
        List<Integer> list = Arrays.asList(1, 5, 2, 4, 5, 2);
        Collections.reverse(list);
        System.out.println(list);

        // 倒序Comparator
        Collections.sort(list, Collections.reverseOrder());
        System.out.println(list);
    }


    /**
     * Collections.rotate()
     *
     * 将list当做一个环, 将其旋转
     * distance正数, 顺时针旋转; 负数, 逆时针旋转
     */
    @Test
    public void rotate() {
        List<Integer> list = Arrays.asList(1, 5, 2, 4, 5, 2);
        Collections.rotate(list, 3);
        System.out.println(list);
    }


    /**
     * Collections.shuffle()
     *
     * 随机打乱list集合
     */
    @Test
    public void shuffle() {
        List<Integer> list = Arrays.asList(1, 5, 2, 4, 5, 2);
        Collections.shuffle(list, new Random(47));
        System.out.println(list);
    }


    /**
     * Collections.sort()
     *
     * 排序
     */
    @Test
    public void sort() {
        List<Integer> list = Arrays.asList(1, 5, 2, 4, 5, 2);
        Collections.sort(list);
        System.out.println(list);
    }


    /**
     * Collections.copy()
     *
     * 复制
     */
    @Test
    public void copy() {
        Parent p1 = new Parent();
        Parent p2 = new Parent();
        Sub s1 = new Sub();
        Sub s2 = new Sub();
        List<Parent> parents = new ArrayList<>();
        parents.add(p1);
        parents.add(p2);
        parents.add(s1);
        parents.add(s2);

        ArrayList<Parent> target = new ArrayList<>(20);
        for(int i = 0; i < 20; i++) {
            target.add(new Parent());
        }
        // 当dest中元素数量小于src元素数量时, 会抛出异常
        Collections.copy(target, parents);
        System.out.println(target);

        // copy同样只是复制引用.
        p1.id = 100;
        System.out.println(target);
    }


    /**
     * Collections.swap()
     *
     * 交换位置
     */
    @Test
    public void swap() {
        List<Integer> list = Arrays.asList(1, 5, 2, 4, 5, 2);
        Collections.swap(list, 0, 3);
        System.out.println(list);
    }


    /**
     * Collections.fill()
     *
     * 填充列表, 只是复制引用, list有几个元素就覆盖几个
     */
    @Test
    public void fill() {
        List<Parent> parents = new ArrayList<>();
        parents.add(new Parent());
        parents.add(new Parent());
        Collections.fill(parents, new Parent());
        System.out.println(parents);
    }


    /**
     * Collections.nCopies()
     *
     * 复制填充集合, 同样也是复制引用
     */
    @Test
    public void nCopies() {
        List<Integer> list = Collections.nCopies(10, 10);
        System.out.println(list);
    }


    /**
     * Collections.disjoint()
     *
     * 判断两个集合是否有交集, 如果没有返回true
     */
    @Test
    public void disjoint() {
        boolean flag = Collections.disjoint(Arrays.asList(1, 5, 3), Arrays.asList(2, 4, 6));
        System.out.println(flag);
    }


    /**
     * Collections.frequency()
     *
     * 返回一个list中等于某个值的元素个数, 是使用==比较, 而不是使用equals()
     */
    @Test
    public void frequency() {
        int count = Collections.frequency(Arrays.asList(1, 5, 2, 4, 6, 7, 2), 2);
        System.out.println(count);

        ArrayList<Parent> target = new ArrayList<>(20);
        Parent p = new Parent();
        target.add(p);
        for(int i = 0; i < 20; i++) {
            target.add(new Parent());
        }

        count = Collections.frequency(target, p);
        System.out.println(count);
    }


    /**
     * Collections.emptyList()
     *
     * emptyList()生成不可以add()和set()的List
     */
    @Test
    public void empty() {
        List<Object> list = Collections.emptyList();
//        list.add("aaa");
        list.remove("aaa");
    }


    /**
     * Collections.singleton()
     *
     * 生成只有一个元素的集合
     */
    @Test
    public void singleton() {
        Set<Integer> s = Collections.singleton(1);
    }


    /**
     * Collections.unmodifiableList()
     *
     * 产生不可变的只读集合
     */
    @Test
    public void unmodifiable() {
        List<Integer> list = Collections.unmodifiableList(Arrays.asList(1, 2, 3));
        list.add(4);
    }


    /**
     * Collections.binarySearch()
     *
     * 对排好序的集合使用二分查找法
     */
    @Test
    public void search() {
        List<Integer> list = Arrays.asList(1, 5, 2, 4, 10, 2, 39, 25, 40, 3);
        Collections.sort(list);
        System.out.println(Collections.binarySearch(list, 10));

        List<String> strs = Arrays.asList("a", "b", "c", "z", "x", "d", "e");
        System.out.println(Collections.binarySearch(strs, "c", String.CASE_INSENSITIVE_ORDER));
    }


    /**
     * Collections.synchronizedMap()
     *
     * 产生同步的容器
     */
    @Test
    public void synchronizedColl() {
        Collections.synchronizedList(new ArrayList<>());
        Collections.synchronizedMap(new HashMap<>());
        Collections.synchronizedSet(new HashSet<>());
    }


    /**
     * 快速报错
     *
     * 在容器中添加了一个modCount字段来实现类似版本号的字段.
     * 生成迭代器时, 会将modCount复制到迭代器的expectedModCount中.
     * 当容器大小改变时, 会将modCount自增. 然后在迭代的时候判断迭代器的expectedModCount是否等于modCount, 如果不相等就会抛出ConcurrentModificationException异常.
     * ConcurrentHashMap, CopyOnWriteArrayList和CopyOnWriteArraySet都使用了可以避免ConcurrentModificationException的技术.
     */
    @Test
    public void failFast() {
        Collection<String> c = new ArrayList<>();
        Iterator<String> it = c.iterator();
        c.add("An object");
        try {
            it.next();
        } catch (ConcurrentModificationException e) {
            e.printStackTrace();
        }
    }
}
