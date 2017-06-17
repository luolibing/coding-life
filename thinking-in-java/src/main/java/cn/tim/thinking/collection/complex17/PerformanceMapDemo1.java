package cn.tim.thinking.collection.complex17;

import cn.tim.thinking.io.TextFileDemo1;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by LuoLiBing on 16/12/28.
 * Map如果使用线性搜索时, 执行速度会相当地慢, 而这正是HashMap提高速度的地方.
 * HashMap使用了特殊的值, 称作散列码, 来取代对键的缓慢搜索.
 * 散列码是"相对唯一"的, 用以替换对象的int索引值, 它是通过将该对象的某些信息进行转换而生成的.
 *
 */
public class PerformanceMapDemo1 {

    private static int counter = 0;

    class Item {
        private final int id = counter ++;

        @Override
        public int hashCode() {
            return id % 2;
        }
    }


    /**
     * Map的实现
     * 1 HashMap        Map基于散列表的实现, 插入和查询键值对的开销是固定的. 可以通过构造器设置容量和负载因子, 以调整容器的性能.
     * 2 LinkedHashMap  类似于HashMap, 但是迭代遍历它时, 取得"键值对"的顺序是其插入次序, 或者是LRU的次序. 只比HashMap慢一点, 而在迭代访问时反而更快, 因为它使用链表维护内部次序.
     * 3 TreeMap        基于红黑树的实现. 查看键或键值对时, 他们会被排序(次序由Comparable或Comparator决定). TreeMap是唯一的带有subMap()方法的Map, 允许返回一个子树
     * 4 WeakHashMap    弱键(weak key)映射, 允许释放映射所指向的对象; 这是为解决某类特殊问题而设计的. 如果映射之外没有引用指向某个键, 则此键可以被垃圾回收器回收
     * 5 ConcurrentHashMap 一种线程安全的Map
     * 6 IdentityHashMap   使用==代替equals对键进行比较的散列映射.
     *
     * 任何键都必须具有一个equals()方法, 如果键被用于HashMap, 那么它必须还具有恰当的hashCode()方法. 如果键被用于TreeMap, 那么它必须实现Comparable.
     *
     */
    @Test
    public void hash() {
        Map<Item, Integer> beanMap = new HashMap<>();
        for(int i = 0; i < 20; i++) {
            beanMap.put(new Item(), i * 20);
        }

        System.out.println((16 -1) & 2);
    }


    /**
     * WeakHashMap映射之外没有引用指向某个键, 则此键可以被垃圾回收.
     * @throws InterruptedException
     */
    @Test
    public void weakHashMap() throws InterruptedException {
        WeakHashMap<Item, Integer> map = new WeakHashMap<>();
        Item item = new Item();
        // new Item()在映射之外还有被item引用, 所以会被保留
        map.put(item, 1);
        // key = new Item() 映射之外没有引用指向new Item()对象了, 所有会被自动移除回收
        map.put(new Item(), 2);
        System.gc();
        TimeUnit.SECONDS.sleep(2);
        //
        map.forEach((k, v) -> System.out.println("key = " + k + ", val = " + v));
        System.out.println("Size = " + map.size());
    }


    /**
     * IdentityHashMap在比较key时, 是使用的==判断是否是同一个对象, 而不是使用equals比较
     * 所以下面的数字插入到Map中, 虽然都是130, 但是在IdentityHashMap中却有两个一样的key.
     */
    @Test
    public void identityHashMap() {
        IdentityHashMap<Integer, Integer> map = new IdentityHashMap<>();
        for(int i = 128; i <= 130; i++) {
            map.put(i, i * 2);
        }

        for(int i = 128; i <= 130; i++) {
            map.put(i, i * 2);
        }

        map.forEach((k, v) -> System.out.println("key = " + k + ", val = " + v));
    }


    static class Maps {
        public static void printKeys(Map<Integer, String> map) {
            System.out.print("Size = " + map.size() + ",");
            System.out.print("keys: ");
            System.out.print(map.keySet()); // 生成一个KeySet
        }

        public static void test(Map<Integer, String> map) {
            System.out.print(map.getClass().getSimpleName());

            // putAll()添加所有元素
            map.putAll(new AbstractCollection2.CountingMapData(25));
            printKeys(map);

            System.out.println("values : ");
            // 获取Key集合: keySet(), 获取Value集合: values()
            System.out.println(map.values());

            // containsKey()判断key是否存在, containsValue()判断value是否存在, get()方法
            System.out.println(map);
            System.out.println("map.containsKey(11): " + map.containsKey(11));
            System.out.println("map.get(11): " + map.get(11));
            System.out.println("map.containsValue(\"F0\"): " + map.containsValue("F0"));

            // remove删除元素, clear()清空元素
            Integer key = map.keySet().iterator().next();
            System.out.println("First key in map: " + key);
            map.remove(key);
            printKeys(map);
            map.clear();
            System.out.println("map.isEmpty : " + map.isEmpty());

            map.putAll(new AbstractCollection2.CountingMapData(25));
            map.keySet().removeAll(map.keySet());
            System.out.println();
        }

        public static void main(String[] args) throws IOException {
            test(new HashMap<>());
            test(new TreeMap<>());
            test(new LinkedHashMap<>());
            test(new IdentityHashMap<>());
            test(new ConcurrentHashMap<>());
            test(new WeakHashMap<>());
            Properties properties = new Properties();
            properties.load(new BufferedReader(new FileReader("/Users/tim/Documents/samples-tim/thinking-in-java/src/main/resources/application.properties")));
            test((Map) properties);
        }
    }


    /**
     * SortedMap(TreeMap是现阶段的唯一实现), 可以确保键处于排序状态.
     */
    @Test
    public void sortedMapDemo1() {
        TreeMap<Integer, String> sortedMap = new TreeMap<>(new AbstractCollection2.CountingMapData(20));
        System.out.println(sortedMap);
        // firstKey, lastKey首个key和最后一个Key
        Integer low = sortedMap.firstKey();
        Integer high = sortedMap.lastKey();
        System.out.println(low);
        System.out.println(high);
        Iterator<Integer> it = sortedMap.keySet().iterator();
        for(int i = 0; i <= 6; i++) {
            if(i == 3) low = it.next();
            if(i == 6) high = it.next();
            else it.next();
        }
        System.out.println(low);
        System.out.println(high);

        // 唯一有subMap(low, high)方法的Map(从low到high的子map),
        // headMap(high)从首个到high(不包含)这个位置的子map, tailMap(low)从low(包含)到末尾的子map
        // 正式因为TreeMap有序, 因此位置的概念才有意义, 所以才能渠道第一个和最后一个子集, 并且可以提取Map的子集
        System.out.println(sortedMap.subMap(low, high));
        System.out.println(sortedMap.headMap(high));
        System.out.println(sortedMap.tailMap(low));
    }


    /**
     * LinkedHashMap
     * 是HashMap的子类, 在HashMap的基础上, 添加了一个链表用来维护元素插入的顺序. 遍历键值对的时候, 会以插入顺序进行遍历.
     * 因为使用链表维护顺序, 所以迭代LinkedHashMap的速度会比HashMap更快一些.
     * 可以在构造器中设定LinkedHashMap, 使之采用LRU算法, 没有被访问过的元素就会出现在队列得前面.
     */
    @Test
    public void linkedHashMapDemo() {
        LinkedHashMap<Integer, String> linkedMap = new LinkedHashMap<>(new AbstractCollection2.CountingMapData(10));
        System.out.println(linkedMap);
        // LinkedHashMap构造LRU, accessOrder, true为accessOrder, false为insertOrder.
        linkedMap = new LinkedHashMap<>(16, .75f, true);
        // 最后访问得元素位于集合的最后. 如果要完全实现定量LRU, 需要重写删除方法.
        for(int i = 0; i < 10; i++) {
            linkedMap.put(i, Integer.toString(i));
        }
        System.out.println(linkedMap);

        // 0到5移动到最后
        for(int i = 0; i < 6; i++) {
            linkedMap.get(i);
        }
        System.out.println(linkedMap);
        linkedMap.get(0);
        linkedMap.get(1);
        System.out.println(linkedMap);
    }


    /**
     * 散列和散列码
     *
     */
    static class Groundhog {
        protected int number;
        public Groundhog(int n) {
            number = n;
        }

        @Override
        public String toString() {
            return "Groundhog #" + number;
        }

        @Override
        public int hashCode() {
            return number;
        }

        @Override
        public boolean equals(Object obj) {
            // instanceof会自动检测是否为null, 如果为null会返回false
            return obj instanceof Groundhog && ((Groundhog) obj).number == this.number;
        }
    }

    static class Prediction {
        private static Random rand = new Random(47);

        // 多云
        private boolean shadow = rand.nextDouble() > 0.5;

        @Override
        public String toString() {
            if(shadow) {
                return "Six more weeks of Winter";
            } else {
                return "Early Spring";
            }
        }
    }
    
    static class SpringDetector {
        public static <T extends Groundhog> void detectSpring(Class<T> type) throws Exception {
            Constructor<T> ghog = type.getConstructor(int.class);
            Map<Groundhog, Prediction> map = new HashMap<>();
            for(int i = 0; i < 10; i++) {
                map.put(ghog.newInstance(i), new Prediction());
            }
            System.out.println(map);

            /**
             * 如果没有为key指定对应的equals()和hashCode()方法, 使用HashMap时, 会产生一些意向不到的情况.
             * 因为没有指定, 会自动使用Object的equals()和hashCode()方法.
             * 在进行查找或者插入的时候, 首先会获取key的hash码, 然后根据hash码对数组size取余, 得到Slot槽的索引值, 然后再在这个索引节点链表或者树上进行查找, 逐个调用equals()方法找到对应节点.
             * 而Object的equals()方法是使用==进行判断, 判断对象是否是指向同一个对象. 而hashCode()则是将对象的内部地址计算出一个整数, 所以即使对象内容一样, 但是分属两个不同对象, hashCode也会不一样.
             *
             * 正确的equals()方法必须满足的5个条件
             * 1 自反性. 对任意x, x.equals(x)一定返回true
             * 2 对称性. 对任意x, y, 如果y.equals(x)返回true, 则x.equals(y)也返回true
             * 3 传递性. 对任意x, y, z, 如果有x.equals(y)返回true, y.equals(z)返回true, 则x.equals(z)也一定返回true
             * 4 一致性. 对任意x, y, 如果兑现各种用于等价比较的信息没有改变, 那么无论调用x.equals(y)多少次, 返回的结果应该保持一致
             * 5 对任何不是Null的x, x.equals(null)一定返回false.
             *
             */
            Groundhog gh = ghog.newInstance(3);
            System.out.println("map.containsKey(gh) = " + map.containsKey(gh));
        }

        public static void main(String[] args) throws Exception {
            detectSpring(Groundhog.class);
            Groundhog g = new Groundhog(3);

            System.out.println("HashCode:       " + g.hashCode());
            System.out.println("HashCode:       " + System.identityHashCode(g)); // 系统身份hashCode
            System.out.println("HashCode(HEX):  " + Integer.toHexString(g.hashCode()));
            System.out.println("toString:       " + String.valueOf(g)); // class@HEX

            System.out.println(null instanceof Integer);
        }
    }


    /**
     * 理解hashCode()
     * 使用散列的目的在于: 想要使用一个对象来查找另一个对象.
     * 使用keys和values两个list来实现Map, 变长的Map
     */
    class SlowMap<K, V> extends AbstractMap<K, V> {

        private List<K> keys = new ArrayList<>();

        private List<V> values = new ArrayList<>();

        @Override
        public Set<Entry<K, V>> entrySet() {
            Set<Entry<K, V>> result = new HashSet<>();
            for(int i = 0; i < keys.size(); i++) {
                // entrySet()的恰当实现应该在Map中提供视图, 而不是副本, 并且这个视图允许对原始映射表进行修改(副本就不行).
                // 比较节点的时候equals()需要同时检查键和值.
                result.add(new SimpleEntry<>(keys.get(i), values.get(i)));
            }
            return result;
        }

        @Override
        public V get(Object key) {
            // 先判断key是否存在, 如果不存在则直接返回null
            if(!keys.contains(key)) {
                return null;
            }
            // key和value在list中的位置一一对应
            return values.get(keys.indexOf(key));
        }

        @Override
        public V put(K key, V value) {
            // 不包含, 直接添加
            if(!keys.contains(key)) {
                keys.add(key);
                values.add(value);
                return null;
            } else {
                int index = keys.indexOf(key);
                V res = values.get(index);
                values.set(index, value);
                return res;
            }
        }
    }


    @Test
    public void slowMap() {
        SlowMap<String, String> map = new SlowMap<>();
        map.putAll(AbstractCollection1.Countries.capitals());
        System.out.println(map);
        System.out.println(map.get("CHINA"));
        System.out.println(map.entrySet());
    }

    @Test
    public void textFile() {
        List<String> words = new TextFileDemo1("/Users/tim/Documents/samples-tim/thinking-in-java/src/main/java/cn/tim/thinking/collection/complex17/AbstractCollection2.java", "\\W+");
        SlowMap<String, Integer> map = new SlowMap<>();
        for(String word : words) {
            map.put(word, map.get(word) == null ? 0 : map.get(word) + 1);
        }
        map.forEach((k, v) -> System.out.println("key = " + k + ", value = " + v));
    }



    class SlowMap1<K, V> extends AbstractMap<K, V> {

        private List<K> keys = new ArrayList<>();

        private List<V> values = new ArrayList<>();

        private Set<Entry<K, V>> entrySet = new EntrySet();

        @Override
        public Set<Entry<K, V>> entrySet() {
            return entrySet;
        }

        @Override
        public V get(Object key) {
            // 先判断key是否存在, 如果不存在则直接返回null
            if(!keys.contains(key)) {
                return null;
            }
            // key和value在list中的位置一一对应
            return values.get(keys.indexOf(key));
        }

        @Override
        public V put(K key, V value) {
            // 不包含, 直接添加
            if(!keys.contains(key)) {
                keys.add(key);
                values.add(value);
                return null;
            } else {
                int index = keys.indexOf(key);
                V res = values.get(index);
                values.set(index, value);
                return res;
            }
        }


        // 自定义EntrySet内部类, 直接操作keys和values
        private class EntrySet extends AbstractSet<Map.Entry<K, V>> {

            @Override
            public Iterator<Entry<K, V>> iterator() {
                return new Iterator<Entry<K, V>>() {
                    private int index = -1;

                    boolean canRemove;

                    @Override
                    public boolean hasNext() {
                        // 判断是否还有下一个
                        return index < keys.size() - 1;
                    }

                    @Override
                    public Entry<K, V> next() {
                        canRemove = true;
                        ++ index;
                        return new SimpleEntry<>(keys.get(index), values.get(index));
                    }

                    @Override
                    public void remove() {
                        // 不允许删除
                        if(!canRemove) {
                            throw new IllegalArgumentException();
                        }
                        canRemove = false;
                        // 删除key和删除values是一个原子操作得一起执行
                        keys.remove(index);
                        values.remove(index--);
                    }
                };
            }

            @Override
            public boolean contains(Object o) {
                return super.contains(o);
            }

            @Override
            public int size() {
                return keys.size();
            }

            @Override
            public void clear() {
                keys.clear();
                values.clear();
            }

            @Override
            public boolean remove(Object o) {
                return super.remove(o);
            }
        }
    }


    @Test
    public void slowMap1() {
        SlowMap1<Integer, String> map = new SlowMap1<>();
        for(int i = 0; i < 20; i++) {
            map.put(i, Integer.toString(i * 10));
        }

        // 这个SlowMap1实现了EntrySet, 这个EntrySet可以作为HashMap的操作视图.
        Iterator<Map.Entry<Integer, String>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, String> entry = it.next();
            if(entry.getKey() % 2 == 0) {
                it.remove();
            }
        }

        map.forEach((k, v) -> System.out.println(k + " : " + v));
    }


    class SlowSet<K> extends AbstractSet<K> {
        private List<K> keys = new ArrayList<>();

        @Override
        public Iterator<K> iterator() {
            return keys.iterator();
        }

        @Override
        public int size() {
            return keys.size();
        }

        @Override
        public boolean add(K k) {
            if(!contains(k)) {
                keys.add(k);
                return true;
            }
            return false;
        }
    }

    @Test
    public void slowSet() {
        SlowSet<Integer> slowSet = new SlowSet<>();
        for(int i = 0; i < 10; i++) {
            slowSet.add(i);
        }

        for(int i = 0; i < 10; i++) {
            slowSet.add(i);
        }

        slowSet.forEach(System.out::println);
    }
}
