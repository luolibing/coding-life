package cn.tim.thinking.collection.complex17;

import org.junit.Test;

import java.util.*;

/**
 * Created by LuoLiBing on 16/12/29.
 * 散列性能
 */
public class HashPrinciple1 {

    /**
     * 散列
     * 散列查找和线性查找Map的区别, 在于对键的查询, 键没有按照任何特定顺序保存, 所以只能使用简单的线性查询, 而线性查找是最慢的查询方式.
     * 散列的价值在于速度: 散列使得查询得以快速进行. 键的查找可以使用线性查找|散列查找|二分查找等.
     * 存储一组元素最快的数据结构是数组, 所以使用它来表示键的信息(键的信息, 而不是键本身). 但是因为数组不能调整容量, 容量被限制了.
     * 数组本身并不保存键本身. 而是通过键对象生成一个数字, 将其作为数组的下标, 这个数字就是散列码.
     *
     * 散列冲突解决:
     * 没有任何冲突, 叫做完美的散列函数.
     * 链表处理: 数组并不直接保存值, 而是保存值的list. 然后对链表中的值使用equals()方法进行线性查询, 这部分线性查询自然会比较慢, 但是如果散列函数好的话, 数组的每个位置就只有较少的值.
     *
     * 为了使散列分布均匀, 桶的数量通常使用质数. 但是经过广泛的测试, java的散列函数都使用2的整数次方, 除法与取余都是最慢的操作.
     * 使用2的整数次方的散列表, 可用掩码代替除法
     */
    class SimpleHashMap<K, V> extends AbstractMap<K, V> {

        private final static double loadFactor = 0.75;

        final static int SIZE = 16;

        private int capacity = SIZE;

        // 阈值
        private int threshold = (int) (capacity * loadFactor);

        int counter = 0;

        // Bucket数组
        LinkedList<SimpleEntry<K, V>>[] buckets = new LinkedList[SIZE];

        @Override
        public Set<Entry<K, V>> entrySet() {
            Set<Entry<K, V>> set = new HashSet<>();
            for(LinkedList<SimpleEntry<K, V>> bucket : buckets) {
                if(bucket == null) {
                    continue;
                }
                set.addAll(bucket);
            }
            return set;
        }

        /**
         *
         * @param key
         * @param value
         * @return
         */
        @Override
        public V put(K key, V value) {
            counter++;
            V oldValue = null;
            // 获取Bucket的索引值
            int index = Math.abs(key.hashCode()) % SIZE;
            // 找到对应的bucket链表
            if(buckets[index] == null) {
                buckets[index] = new LinkedList<>();
            }

            // 线性查找Buckets[index]链表
            boolean found = false;
            Iterator<SimpleEntry<K, V>> it = buckets[index].iterator();
            while (it.hasNext()) {
                SimpleEntry<K, V> entry = it.next();
                if(entry.getKey().equals(key)) {
                    System.out.println("hash冲突" + key + ", counter = " + counter);
                    oldValue = entry.getValue();
                    entry.setValue(value);
                    found = true;
                    break;
                }
            }

            // 这个地方不能使用oldValue判空, 因为value允许为空
            if(!found) {
                // 如果大于阈值, 进行rehash()
                if(counter >= threshold) {
                    rehash();
                }
                if(buckets[index] == null) {
                    buckets[index] = new LinkedList<>();
                }
                buckets[index].add(new SimpleEntry<>(key, value));
            }
            return oldValue;
        }

        @Override
        public V get(Object key) {
            // 获取Bucket的索引值
            int index = Math.abs(key.hashCode()) % SIZE;
            if(buckets[index] == null) {
                return null;
            }

            // 线性查找Buckets[index]链表
            Iterator<SimpleEntry<K, V>> it = buckets[index].iterator();
            while (it.hasNext()) {
                SimpleEntry<K, V> entry = it.next();
                if(entry.getKey().equals(key)) {
                    return entry.getValue();
                }
            }
            return null;
        }

        @Override
        public void clear() {
            buckets = new LinkedList[SIZE];
        }

        @Override
        public V remove(Object key) {
            int index = Math.abs(key.hashCode()) % SIZE;
            // 没有找到对应的buckets
            if(buckets[index] == null) {
                return null;
            }

            // 线性查找buckets[index]链表
            Iterator<SimpleEntry<K, V>> it = buckets[index].iterator();
            while (it.hasNext()) {
                SimpleEntry<K, V> entry = it.next();
                // 找到之后, 将其删除
                if(entry.getKey().equals(key)) {
                    it.remove();
                    return entry.getValue();
                }
            }
            return null;
        }

        /**
         * rehash()重新散列
         */
        private void rehash() {
            capacity = capacity * 2;
            threshold = (int) (capacity * loadFactor);
            System.out.println("Rehashing, new capacity = " + capacity);
            buckets = new LinkedList[capacity];
            Iterator<Entry<K, V>> it = entrySet().iterator();
            // 将size设置为0
            counter = 0;
            while (it.hasNext()) {
                Entry<K, V> entry = it.next();
                put(entry.getKey(), entry.getValue());
            }
        }
    }

    @Test
    public void simpleHashMap() {
//        Map<String, String> map = new SimpleHashMap<>();
//        map.putAll(AbstractCollection1.Countries.capitals());
//        System.out.println(map);
//        System.out.println(map.get("CHINA"));
//        map.put("CHINA", "北京");
//        System.out.println(map.remove("CHINA"));
//        System.out.println(map.entrySet());
//        map.clear();
//        System.out.println(map);

        SimpleHashMap<Integer, Integer> m = new SimpleHashMap<>();
        for(int i = 0; i < 1000; i++) {
            m.put(i, i*10);
        }
        System.out.println(m);
    }
}
