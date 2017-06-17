package cn.tim.thinking.collection.complex17;

import cn.tim.thinking.io.TextFileDemo1;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;

/**
 * Created by LuoLiBing on 16/12/28.
 * 理解Map
 */
public class MapDemo1 {

    /**
     * 映射表(关联数组)的基本思想是它维护的是键值对关联, 因此可以通过键来查找值
     * 标准的Java类库中包含Map的集中基本实现
     * HashMap, TreeMap, LinkedHashMap, WeakHashMap, ConcurrentHashMap, IdentityHashMap
     */
    // 简单定长关联数组
    class AssociativeArray<K, V> implements Iterable<Object[]> {

        // 二维数组 key - value
        private Object[][] data;

        // 标记当前大小
        private int index;

        public AssociativeArray(int len) {
            data = new Object[len][2];
        }

        public V put(K k, V v) {
            // 先判断长度
            if(index >= data.length) {
                throw new ArrayIndexOutOfBoundsException();
            }
            V res = null;
            for(int i = 0; i < index; i++) {
                // 遍历查找, 如果找到覆盖掉
                if(k.equals(data[i][0])) {
                    res = (V) data[i][1];
                    data[i][1] = v;
                    break;
                }
            }
            // 没有找到新插入一个key
            if(res == null) {
                data[index ++] = new Object[] {k, v};
            }
            return res;
        }

        // 线性搜索, 执行速度会相当地慢, 而这正是HashMap提供速度的地方.
        public V get(K k) {
            V res = null;
            for(int i = 0; i < index; i++) {
                if(k.equals(data[i][0])) {
                    res = (V) data[i][1];
                    break;
                }
            }
            return res;
        }

        public V remove(K k) {
            V res = null;
            for(int i = 0; i < index; i++) {
                if(k.equals(data[i][0])) {
                    res = (V) data[i][1];
                    data[i] = new Object[]{data[index-1][0], data[index-1][1]};
                    data[index--] = null;
                    break;
                }
            }
            return res;
        }

        public int size() {
            return index;
        }

        public Iterator<Object[]> iterator() {
            return new Iterator<Object[]>() {
                private int index = 0;

                @Override
                public boolean hasNext() {
                    return index < AssociativeArray.this.index;
                }

                @Override
                public Object[] next() {
                    return data[index++];
                }
            };
        }
    }

    @Test
    public void associativeArray() {
        AssociativeArray<String, Integer> arr = new AssociativeArray<>(100);
        for(int i = 0; i <10; i++) {
            arr.put(Character.toString((char) (56 + i)), i);
        }

        arr.forEach((data) -> System.out.println("key = " + data[0] + " , value = " + data[1]));

        System.out.println();
        for(int i = 0; i < 5; i++) {
            String key = Character.toString((char) (56 + i));
            arr.remove(key);
        }

        arr.forEach((data) -> System.out.println("key = " + data[0] + " , value = " + data[1]));

        System.out.println(arr.size());
    }


    @Test
    public void wordCounter() {
        List<String> words = new TextFileDemo1("/Users/tim/Documents/samples-tim/thinking-in-java/src/main/java/cn/tim/thinking/collection/complex17/AbstractCollection2.java", "\\W+");
        AssociativeArray<String, Integer> map = new AssociativeArray<>(words.size());
        for(String word : words) {
            map.put(word, map.get(word) == null ? 0 : map.get(word) + 1);
        }

        map.forEach((data) -> System.out.println("key = " + data[0] + " , value = " + data[1]));
    }
}
