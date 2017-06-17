package cn.tim.thinking.collection.simple11;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by LuoLiBing on 16/9/7.
 * LRU算法实现,借助于LinkedHashMap来实现
 */
public class LRULinkedHashMap<K, V> extends LinkedHashMap<K, V> {

    private final static int LRU_MAX_CAPACITY = 1024;

    /**
     * LRU的容量大小
     */
    private int capacity;

    public LRULinkedHashMap() {
        super();
    }

    public LRULinkedHashMap(int initialCapacity, float loadFactor, boolean isLRU) {
        super(initialCapacity, loadFactor, isLRU);
        capacity = LRU_MAX_CAPACITY;
    }

    public LRULinkedHashMap(int initialCapacity, float loadFactor, boolean isLRU, int lruCapacity) {
        super(initialCapacity, loadFactor, isLRU);
        capacity = lruCapacity;
    }

    /**
     * 父类提供的钩子方法,由子类决定最终的行为
     * @param eldest
     * @return
     */
    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        // 设置一个容量,当大于这个容量时,会自动删除链表的队头元素
        return (size() > capacity);
    }

    public static void main(String[] args) {
        Map<Integer, Integer> map = new LRULinkedHashMap<>(5, 0.75f, true, 10);
        for(int i = 0; i< 100; i++) {
            map.put(i, i*2);
        }
        map.forEach((k, v) -> System.out.println(k + ":" + v));
    }
}
