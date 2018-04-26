package cn.tim.thinking.collection.simple11;

import cn.tim.thinking.Exercise;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by LuoLiBing on 16/8/29.
 * Map接口
 * 1 HashMap
 * 2 LinkedHashMap
 * 3 TreeMap
 */
public class MapTest implements Exercise {

    /**
     * HashMap
     * 与hashtable的区别在于允许key和value为null和没有加同步锁
     * 特性
     * 1 无序
     * 2 迭代的时间与hashMap实例buckets和键值对数量密切相关
     * 3 初始容量不能太高,并且负载因子不能太低
     * 4 两个重要的参数,初始容量与负载因子.容量指的是hashMap的buckets数组数量,负载因子是测量hashMap满的程度
     * 5 当元素的个数超过负载因子和当前容量时,hashMap将要进行rehash重新构建数据结构,因此hash表有大约两倍的桶数
     * 6 默认的负载因子0.75在时间与空间成本上取得了一个良好的平衡.更多的值降低了空间开销,但是增加查找成本. 很好的设置map的初始大小,可以减少rehash的频率. 设置一个好的初始大小比让其自动扩容性能更好
     * 7 threshold作为判断map是否扩容的决定性因素,(capacity * load factor)>threshold时,需要重新
     * 8 capacity都是2的幂,因此如果initialCapacity不是2的次幂,则找到大于等于initialCapacity的最小的2次幂,tableSizeFor方法即返回这种数
     * 9 put返回上一个关联的值,当put时之前没有映射过,则返回null,有关联则返回之前关联的值
     * 10entrySet使用的是EntrySet以及EntryIterator内部类实现
     *
     * HashMap的底层实现是数组和链表
     * 1 添加元素的时候,会先判断对应hash索引值下标的bucket是否有节点,没有节点,把当前节点放置在该bucket,如果有节点,则
     * 2 当(capacity * load factor) > threshold时,需要进行resize,自动扩容,扩容的时候需要进行rehash()重新分布bucket.所以尽量避免多次resize
     * 3 遍历的性能很大程度上取决于capacity
     *
     * HashMap的几个重要参数
     * 1 capacity 数组的大小  默认为16
     * 2 size map元素的个数
     * 3 load factor负载因子  默认为0.75
     * 4 threshold扩容参数大于这个值,就需要扩容重新分配
     *
     * HashMap Java8中新特性
     * 1 实现方式有所变化,当链表长度大于8时,由链表切换成红黑树,当链表长度缩减为6时,红黑树切换成链表.当链表长度过大时,使用红黑树的性能高于链表
     *
     */
    @Test
    public void hashMap() throws InterruptedException {
        HashMap hashMap = new HashMap(5); // 初始capacity大小会被设置为大于等于initCapacity的最小的2次幂,这里等于8,threshold=capacity*load factor =  8 * 0.75=6
        hashMap.put("aaa", 1);
        hashMap.put("bbb", 2);
        hashMap.put("ccc", 3);
        hashMap.put("ddd", 4);
        hashMap.put("eee", 5);
        hashMap.put("fff", 6);
        hashMap.put("hhh", 7);
//        hashMap.put(null, null); // key和值不能都

        //
        System.out.println(hashMap.put("hhh", 8));
        new HashMap<>().put("aaa", 1);
        System.out.println(hashMap.get("aaa"));
    }

    /**
     * LinkedHashMap
     * 1
     *
     * LinkedHashMap底层实现
     * 1 在所有的Node里保持了一个双向链表,以维持插入的顺序,同样re-insert也不会影响迭代的顺序
     * 2 LinkedHashMap(int,float,boolean)构造函数提供了一个LRU的功能
     * 3 与LinkedHashSet一样,LinkedHashMap插入性能略微低于HashMap,迭代性能比HashMap高,因为与size相关
     * 4 正常情况下,只有add或者delete才会改变linkedHashMap的结构,而在access_ordered Linked hash map中,只有查询会改变它的结构
     * 5 LinkedHashMap中的entry有一个before和after,并且有一个head,和一个tail
     * 6 有一个accessOrder选项,当设置为true时,访问一个entry,该entry会自动放置到末尾
     * 7 LinkedHashMap的顺序可以分为: 按插入顺序的链表,和按访问顺序(调用get方法)的链表
     * 8 LinkedHashMap可以很好的实现LRU算法, LinkedHashMap使用了一个钩子removeEldestEntry()方法来决定是否删除掉最少访问的元素,子类可以使用这个钩子来巧妙的实现最少删除
     *
     */
    @Test
    public void linkedHashMap() {
        LinkedHashMap linkedHashMap = new LinkedHashMap(10, 0.75f, true);
        linkedHashMap.put("a", 1);
        linkedHashMap.put("b", 2);
        linkedHashMap.put("c", 3);
        linkedHashMap.put("d", 4);
        linkedHashMap.forEach((k, v) -> System.out.println(k + ":" + v));

        linkedHashMap.get("d");
        linkedHashMap.get("c");
        System.out.println("after access!!!!!!!!!");
        linkedHashMap.forEach((k, v) -> System.out.println(k + ":" + v));
    }

    /**
     * TreeMap
     * 1 底层通过红黑树实现,初始化的时候可以指定一个Compareator
     * 2 HashMap,LinkedHashMap都允许key为null,但是treeMap不允许key为空,为空会报空指针异常
     * 3 TreeMap能够加快搜索速度,因为红黑树也遵守了任何节点都比左孩子大比右孩子小,所以可以很快的进行搜索减少搜索范围
     *
     *
     */
    @Test
    public void treeMap1() {
        Map<String, Integer> treeMap = new TreeMap<>((k1, k2) -> {
            int len1, len2;
            if(Objects.isNull(k1)) {
                len1 = 0;
            } else {
                len1 = k1.length();
            }

            if(Objects.isNull(k2)) {
                len2 = 0;
            } else {
                len2 = k2.length();
            }
            if(len1 > len2) {
                return 1;
            } else if(len1 == len2) {
                return 0;
            } else {
                return -1;
            }
        });
    }

    @Test
    public void treeMap2() {
        Map<String, Integer> treeMap = new TreeMap<>();
        treeMap.put("e", 1);
        treeMap.put("f", 2);
        treeMap.put("g", 3);
        treeMap.put("h", 8);
        treeMap.put("a", 1);
        treeMap.put("b", 2);
        treeMap.put("c", 3);
        treeMap.put("d", 4);
        treeMap.containsKey("a");
//        treeMap.forEach((k, v) -> System.out.println(k + ":" + v));
        System.out.println();
        final SortedSet<Map.Entry<String, Integer>> entries = entriesSortedByValues(treeMap);
        for(Map.Entry<String, Integer> entry: entries) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }

    }

    @Test
    public void test() {
        String str = "luolibingnihao";
        System.out.println(str.hashCode() % 16);
        System.out.println(str.hashCode() & 15);
    }


    @Test
    public void random() {
        for(int i = 0 ; i< 100; i++) {
            Integer[] max = getRandomMax(createRandomMap());
            System.out.println(Arrays.toString(max));
        }
    }

    public Map<Integer, Integer> createRandomMap() {
        Random random = new Random();
        Map<Integer, Integer> m = new TreeMap<>();
        for(int i = 0; i < 10000; i++) {
            int r = random.nextInt(20);
            // 返回上一次插入的值,如果第一次返回null
            Integer freq = m.get(r);
            m.put(r, freq == null ? 1 : freq + 1);
        }
        return m;
    }

    public Integer[] getRandomMax(Map<Integer, Integer> beanMap) {
        final int[] i = {0};
        final int[] count = {0};
        beanMap.forEach( (k, v) -> {
            if(v >= count[0]) {
                i[0] = k;
                count[0] = v;
            }
        });
        return new Integer[] {i[0], count[0]};
    }


    /**
     * treemap如何根据value排序,本身treeMap并不能根据value进行排序.
     * 可以通过Map.entrySet重写Set来实现,SortedSet,我们使用TreeSet来实现,treeSet需要一个Comparator,传入一个按照value进行比较的comparator即可
     * @param map
     * @param <K>
     * @param <V>
     * @return
     */
    static <K,V extends Comparable<? super V>> SortedSet<Map.Entry<K,V>> entriesSortedByValues(Map<K,V> map) {
        SortedSet<Map.Entry<K,V>> sortedEntries = new TreeSet<>(
                (e1, e2) -> {
                    int res = e1.getValue().compareTo(e2.getValue());
                    return res != 0 ? res : 1;
                }
        );
        sortedEntries.addAll(map.entrySet());
        return sortedEntries;
    }

    @Test
    public void test18() {
        Map<String, Integer> treeMap = new HashMap<>();
        treeMap.put("e", 1);
        treeMap.put("f", 2);
        treeMap.put("g", 3);
        treeMap.put("h", 8);
        treeMap.put("a", 1);
        treeMap.put("b", 2);
        treeMap.put("c", 3);
        treeMap.put("d", 4);

        Map<String, Integer> linkedHashMap = new LinkedHashMap<>();
        treeMap.forEach((key, val) -> {
            System.out.println("key hash=" + key.hashCode() % 16 + ",key=" + key + ",value=" + val);
            linkedHashMap.put(key, val);
        });

        System.out.println();
        linkedHashMap.forEach((key,val) -> {
            System.out.println(key + "=" +val);
        });
    }

    @Test
    public void test19() {
        Set<String> set = new HashSet<>();
        set.add("c");
        set.add("a");
        set.add("e");
        set.add("b");
        set.add("c");
        set.add("d");
        set.add("f");
        set.add("g");
        set.add("h");
        Set<String> linkedHashSet = new LinkedHashSet<>();
        set.forEach( str -> {
            System.out.println("key hash=" + str.hashCode() % 16 + ",val=" + str);
            linkedHashSet.add(str);
        });
        System.out.println();
        linkedHashSet.forEach(System.out::println);
    }

    @Test
    public void test16() {
        List<String> wordList = getWordsAsList();
        Set<String> wordSet = new HashSet<>(wordList);
        final int[] sum = {0};
        wordSet.forEach(word -> {
            int count = getYuanyinCount(word);
            sum[0] += count;
            System.out.println(word + ":" + count);
        });
        System.out.println("sum=" + sum[0]);

    }

    private Map<Character, Integer> counter = new HashMap<>();

    @Test
    public void test20() {
        List<String> wordList = getWordsAsList();
        Set<String> wordSet = new HashSet<>(wordList);
        wordSet.forEach(this::getYuanyinCount1);
        counter.forEach( (ch, count) -> System.out.println(ch + "=" + count));
    }


    private void getYuanyinCount1(String str) {
        final char[] chars = str.toCharArray();
        for(char ch : chars) {
            switch (ch) {
                case 'a' :case 'e':case 'i':case 'o':case 'u':
                    Integer count = counter.get(ch);
                    count = (count == null ?1 : count + 1);
                    counter.put(ch, count);
                    break;
            }
        }
    }


    private int getYuanyinCount(String str) {
        final char[] chars = str.toCharArray();
        int count = 0;
        for(char ch : chars) {
            switch (ch) {
                case 'a' :case 'e':case 'i':case 'o':case 'u': count ++; break;
            }
        }
        return count;
    }

    @Test
    public void test21() {
        List<String> wordList = getWordsAsList();
        Map<String, Integer> map = new HashMap<>();
        wordList.forEach(word -> {
            Integer count = map.get(word);
            count = count == null ? 1 : count + 1;
            map.put(word, count);
        });
        map.forEach( (key, val) -> System.out.println(key + "=" + val));

        ArrayList<String> keyList = new ArrayList<>(map.keySet());
        Collections.sort(keyList, String.CASE_INSENSITIVE_ORDER);
        keyList.forEach( word -> System.out.println(word + "=" + map.get(word)));
    }

    @Test
    public void test22() {
        final String[] wordsArray = getWordsAsArray();
        Set<Word> words = new HashSet<>();
        for(int i = 0; i < wordsArray.length; i++) {
            words.add(new Word(wordsArray[i]));
        }
        words.forEach(System.out::println);
    }

    @Test
    public void test24() {
        Map<String, Integer> treeMap = new LinkedHashMap<>();
        treeMap.put("e", 1);
        treeMap.put("f", 2);
        treeMap.put("g", 3);
        treeMap.put("h", 8);
        treeMap.put("a", 1);
        treeMap.put("b", 2);
        treeMap.put("c", 3);
        treeMap.put("d", 4);
        SortedSet<Map.Entry<String, Integer>> entries = sortedByValue(treeMap);
        LinkedHashMap result = new LinkedHashMap();
        entries.forEach(entry -> {
            result.put(entry.getKey(), entry.getValue());
        });
        result.forEach((k,v) -> System.out.println(k + ":" + v));
    }

    static <K, V extends Comparable<? super V>> SortedSet<Map.Entry<K,V>> sortedByValue(Map<K, V> map) {
        SortedSet<Map.Entry<K,V>> result = new TreeSet<>(
                (e1, e2) -> {
                    int res = e1.getValue().compareTo(e2.getValue());
                    return res != 0 ? res : 1;
                }
        );
        result.addAll(map.entrySet());
        return result;
    }

    @Test
    public void test25() {
        String[] wordsAsArray = getWordsAsArray();
        Map<String, ArrayList<Integer>> result = new HashMap<>();
        for(int i = 0; i<wordsAsArray.length; i++) {
            ArrayList<Integer> counter = result.get(wordsAsArray[i]);
            if(counter == null) {
                counter = new ArrayList<>();
                result.put(wordsAsArray[i], counter);
            }
            counter.add(i);
        }
        result.forEach((k, v) -> {
            System.out.println(k + ":" + v);
        });
    }

    @Test
    public void test26() {
        String[] wordsAsArray = getWordsAsArray();
        Map<String, ArrayList<Integer>> result = new HashMap<>();
        for(int i = 0; i<wordsAsArray.length; i++) {
            ArrayList<Integer> counter = result.get(wordsAsArray[i]);
            if(counter == null) {
                counter = new ArrayList<>();
                result.put(wordsAsArray[i], counter);
            }
            counter.add(i);
        }

        List<Map.Entry<String, ArrayList<Integer>>> wordsOrder = result.entrySet().stream().sorted(
                (e1, e2) -> e1.getValue().get(0).compareTo(e2.getValue().get(0))).collect(Collectors.toList());
        wordsOrder.forEach((entry) -> {
            System.out.println(entry.getKey() + "=" + entry.getValue());
        });

    }

    class Word {
        private String word;
        private int count = 0;
        public Word(String word) {
            this.word = word;
        }
        public void increment() {
            count ++;
        }

        @Override
        public String toString() {
            return "Word{" +
                    "word='" + word + '\'' +
                    ", count=" + count +
                    '}';
        }
    }

    @Test
    public void test27() throws InterruptedException {
//        Cache<String, Object> graphs = CacheBuilder.newBuilder()
//                .concurrencyLevel(4)
//                .weakKeys()
//                .maximumSize(10000)
//                .expireAfterWrite(10, TimeUnit.MINUTES)
//                .build(
//                        new CacheLoader<String, Object>() {
//                            public Object load(String key) throws Exception {
//                                return createExpensiveGraph(key);
//                            }
//                        });

        Cache<String, String> cache = CacheBuilder.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(5, TimeUnit.SECONDS)
                .concurrencyLevel(4)
                .recordStats()
                .build();
        cache.put("aaa", "aa1");
        System.out.println(cache.getIfPresent("aaa"));
        TimeUnit.SECONDS.sleep(10);
        System.out.println(cache.getIfPresent("aaa"));
    }

}
