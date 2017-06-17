package cn.tim.thinking.collection.complex17;

import org.junit.Test;

import java.util.*;

/**
 * Created by LuoLiBing on 16/12/29.
 */
public class HashPrinciple2 {

    class Entry<K, V> implements Map.Entry<K, V> {
        private K k;

        private V v;

        private Entry<K, V> next;

        public Entry(K k, V v, Entry<K, V> next) {
            this.k = k;
            this.v = v;
            this.next = next;
        }

        @Override
        public K getKey() {
            return k;
        }

        @Override
        public V getValue() {
            return v;
        }

        @Override
        public V setValue(V value) {
            V oldValue = v;
            this.v = value;
            return oldValue;
        }

        @Override
        public boolean equals(Object o) {
            if (o == this)
                return true;
            if (o instanceof Map.Entry) {
                Map.Entry<?,?> e = (Map.Entry<?,?>)o;
                if (Objects.equals(k, e.getKey()) &&
                        Objects.equals(v, e.getValue()))
                    return true;
            }
            return false;
        }

        public Entry<K, V> next() {
            return next;
        }
    }

    class SimpleHashMap<K, V> extends AbstractMap<K, V> {

        final static int SIZE = 997;

        int counter = 0;

        // Bucket数组
        Entry<K, V>[] buckets = new Entry[SIZE];

        @Override
        public Set<Entry<K, V>> entrySet() {
            Entry<K, V> p, e;
            Set<Entry<K, V>> entrySet = new HashSet<>();
            for(Entry<K, V> entry : buckets) {
                if((p = entry) != null) {
                    entrySet.add(p);
                    while ((p = ((HashPrinciple2.Entry<K, V>) entry).next) != null) {
                        entrySet.add(p);
                    }
                }
            }
            return entrySet;
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
            Entry<K, V> p, e;
            // 获取Bucket的索引值
            int index = Math.abs(key.hashCode()) % SIZE;
            // 找到对应的bucket链表
            if(buckets[index] == null) {
                buckets[index] = new HashPrinciple2.Entry<>(key, value, null);
            }
            // bucket的首个节点
            else if((p = buckets[index]).getKey().equals(key)) {
                oldValue = p.getValue();
                p.setValue(value);
            }
            // 在bucket链表首个之后的元素中
            else {
                // 线性查找Buckets[index]链表
                while (true) {
                    // 找到的下一个元素为null, 则直接将其添加到其末尾
                    if((e = ((HashPrinciple2.Entry<K, V>) p).next) == null) {
                        ((HashPrinciple2.Entry<K, V>) p).next = new HashPrinciple2.Entry<>(key, value, null);
                        break;
                    }

                    // 找到对应的节点
                    if(key.equals(p.getKey())) {
                        break;
                    }
                    p = e;
                }

                if (e != null) {
                    oldValue = e.getValue();
                    if (oldValue == null)
                        e.setValue(value);
                }

            }
            return oldValue;
        }

        @Override
        public V get(Object key) {
            HashPrinciple2.Entry<K, V> p, e;
            // 获取Bucket的索引值
            int index = Math.abs(key.hashCode()) % SIZE;
            if(buckets[index] == null) {
                return null;
            }
            // 线性查找Buckets[index]链表
            else {
                do {
                    p = (HashPrinciple2.Entry<K, V>) buckets[index];
                    if(p.k.equals(key)) {
                        return p.v;
                    }
                    p = p.next();
                } while (p != null);
            }
            return null;
        }

        @Override
        public void clear() {
            buckets = new Entry[SIZE];
        }

        @Override
        public V remove(Object key) {
            HashPrinciple2.Entry<K, V> p, e;
            int index = Math.abs(key.hashCode()) % SIZE;
            // 没有找到对应的buckets
            if(buckets[index] == null) {
                return null;
            }

            // 线性查找buckets[index]链表
            if((e = p = (HashPrinciple2.Entry<K, V>) buckets[index]) == null) {
                return null;
            }
            // 线性查找Buckets[index]链表
            else {
                do {
                    if(p.getKey().equals(key)) {
                        e.next = p.next;
                        p.next = null;
                        V oldValue = p.getValue();
                        if(e == p) {
                            buckets[index] = null;
                        }
                        return oldValue;
                    }
                    e = p;
                } while ((p = p.next) != null);
            }
            return null;
        }
    }

    @Test
    public void hashPrinciple2() {
        SimpleHashMap<String, String> map = new SimpleHashMap<>();
        map.put("a", "luo");
        map.put("b", "li");
        map.put("c", "bing");
        map.put("c1", "bing");
        map.put("c2", "bing");
        map.forEach((k, v) -> System.out.println(k + " : " + v));

        System.out.println(map.get("c"));
        System.out.println(map.remove("c"));
        map.forEach((k, v) -> System.out.println(k + " : " + v));
    }
}
