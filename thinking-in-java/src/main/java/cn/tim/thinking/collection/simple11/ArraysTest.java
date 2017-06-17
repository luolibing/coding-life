package cn.tim.thinking.collection.simple11;

import org.junit.Test;

import java.util.*;
import java.util.stream.IntStream;

/**
 * Created by LuoLiBing on 16/8/26.
 * Java容器类库中的两种主要类型,区别在于容器中每个槽保存的元素个数
 * Collection在每个槽中只能保存一个元素
 * List: 以特定的顺序保存一组元素
 * Set: 元素不能重复
 * Queue: 只允许在容器的一端插入,从另一端移除
 *
 * Map在每个槽内保存两个对象,即键与值
 *
 * ArrayList\linkedList
 * HashSet\TreeSet\LinkedHashSet
 * HashMap\TreeMap\LinkedHashMap
 *
 *
 */
public class ArraysTest {

    @Test
    public void arrays() {
        // Arrays.
        List<Integer> list = Arrays.asList(1, 2, 3, 5);
        list.set(0, 5);
        // Arrays.asList创建出的list,底层是数组,定长列表
        // list.remove(1);
        // list.add(5);
    }

    @Test
    public void collections() {
        Collection<Integer> collection = new ArrayList<>(Arrays.asList(1, 2, 3, 4));
        collection.addAll(Arrays.asList(5, 6, 7));

        // 构建一个不包含元素的collection,然后调用Collections.addAll方式很方便,因此它是首选方式.
        Collections.addAll(collection, 11, 12, 13);
    }

    /**
     * Arrays.toString()打印数组,一般的集合可以直接使用toString()
     */
    @Test
    public void print() {
        System.out.println(Arrays.toString(new String[] {"aaa", "bbb", "ccc"}));
        fill(new ArrayList<String>());
        fill(new LinkedList<String>());
        fill(new HashSet<String>());
        fill(new TreeSet<String>());
        fill(new LinkedHashSet<String>());
        fill(new HashMap<String, String>());
        fill(new TreeMap<String, String>());
        fill(new LinkedHashMap<String, String>());
    }

    static Collection fill(Collection<String> collection) {
        collection.add("rat");
        collection.add("cat");
        collection.add("dog");
        collection.add("dog");
        System.out.println(collection.getClass().getName() + ":" + collection);
        return collection;
    }

    static Map fill(Map<String, String> map) {
        map.put("rat", "Fuzzy");
        map.put("cat", "Rags");
        map.put("dog", "Bosco");
        map.put("dog", "Spot");
        System.out.println(map.getClass().getName() + ":" + map);
        return map;
    }


    private static final Object[] EMPTY_ELEMENTDATA = {};

    public static Object[] getData() {
        return EMPTY_ELEMENTDATA;
    }

    public static void print1() {
        System.out.println(EMPTY_ELEMENTDATA);
    }


    @Test
    public void accessRandom1() {
        List<Integer> list = createRandomList();
        long startTime = System.nanoTime();
        for(int i = 0; i< list.size(); i++) {
            System.out.println(list.get(i));
        }
        long endTime = System.nanoTime();
        System.out.println("time=" + (endTime - startTime));
    }


    @Test
    public void accessRandom2() {
        List<Integer> list = createRandomList();
        long startTime = System.nanoTime();
        for(Integer i : list) {
            System.out.println(i);
        }
        long endTime = System.nanoTime();
        System.out.println("time=" + (endTime - startTime));
    }

    private List<Integer> createRandomList() {
        List<Integer> list = new ArrayList<>(1_000_000);
        IntStream.range(0, 1_000_000).forEach(list::add);
        return list;
    }
}
