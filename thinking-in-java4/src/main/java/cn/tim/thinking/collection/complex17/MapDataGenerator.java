package cn.tim.thinking.collection.complex17;

import cn.tim.thinking.array.TestArrayData;
import org.junit.Test;

import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * Created by LuoLiBing on 16/12/27.
 * Map数据生成器
 */
public class MapDataGenerator {

    // 键值对元素, 有点类似于Map中的Entry
    class Pair<K, V> {
        public final K key;
        public final V value;

        Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    /**
     * Map数据
     * @param <K>
     * @param <V>
     */
    static class MapData<K, V> extends LinkedHashMap<K, V> {

        // 重载了多个构造方法, 提供多种构造方式
        private MapData(TestArrayData.Generator<Pair<K, V>> generator, int quantity) {
            for(int i = 0; i < quantity; i++) {
                Pair<K, V> pair = generator.next();
                put(pair.key, pair.value);
            }
        }

        private MapData(TestArrayData.Generator<K> genK, TestArrayData.Generator<V> genV, int quantity) {
            for(int i = 0; i < quantity; i++) {
                put(genK.next(), genV.next());
            }
        }

        private MapData(TestArrayData.Generator<K> genK, V value, int quantity) {
            for(int i = 0; i < quantity; i++) {
                put(genK.next(), value);
            }
        }

        private MapData(Iterable<K> genK, TestArrayData.Generator<V> genV) {
            for(K k : genK) {
                put(k, genV.next());
            }
        }

        private MapData(Iterable<K> genK, V value) {
            for(K k : genK) {
                put(k, value);
            }
        }


        /**
         * 提供了对应的static方法产生MapData
         * @param gen
         * @param quantity
         * @param <K>
         * @param <V>
         * @return
         */
        public static <K, V> MapData<K, V> map(TestArrayData.Generator<Pair<K, V>> gen, int quantity) {
            return new MapData<>(gen, quantity);
        }

        public static <K, V> MapData<K, V> map(TestArrayData.Generator<K> genK, TestArrayData.Generator<V> genV, int quantity) {
            return new MapData<>(genK, genV, quantity);
        }

        public static <K, V> MapData<K, V> map(TestArrayData.Generator<K> genK, V value, int quantity) {
            return new MapData<>(genK, value, quantity);
        }

        public static <K, V> MapData<K, V> map(Iterable<K> genK, TestArrayData.Generator<V> genV) {
            return new MapData<>(genK, genV);
        }

        public static <K, V> MapData<K, V> map(Iterable<K> genK, V value) {
            return new MapData<>(genK, value);
        }
    }


    /**
     * Letters既是一个Generator, 又是一个Iterable迭代器元素
     */
    class Letters implements TestArrayData.Generator<Pair<Integer, String>>, Iterable<Integer> {
        private int size = 9;
        private int number = 1;
        private char letter = 'A';

        @Override
        public Pair<Integer, String> next() {
            return new Pair<>(number++, "" + letter++);
        }

        @Override
        public Iterator<Integer> iterator() {
            // 匿名内部类
            return new Iterator<Integer>() {
                @Override
                public boolean hasNext() {
                    return number < size;
                }

                @Override
                public Integer next() {
                    return number++;
                }
            };
        }
    }

    @Test
    public void mapDataTest() {
        System.out.println(MapData.map(new Letters(), 11));
        System.out.println(MapData.map(new TestArrayData.CountingGenerator.Character(), new TestArrayData.RandomGenerator.Integer(), 11));
        System.out.println(MapData.map(new TestArrayData.CountingGenerator.Character(), "Value", 10));
        System.out.println(MapData.map(new Letters(), new TestArrayData.RandomGenerator.Boolean()));
        System.out.println(MapData.map(new Letters(), "Pop"));
    }
}
