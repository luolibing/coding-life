package cn.tim.thinking.collection.simple11.collections;

import org.junit.Test;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by LuoLiBing on 16/9/28.
 * THRESHOLD(阈值)
 *
 * 重点:
 * 1 for(int i=0,size=arr.length; i< size; i++)减少判断的时候调用方法或者属性的次数,可以提升一部分性能. 尽可能的少调用方法,多次调用返回一样结果的,可以缩减成只调用一次
 * 2 遍历List的时候,判断是否标记了RandomAccess,如果是,可以使用for(int i=0,size=list.size();i<size; i++)来进行遍历,这样JVM会针对这种格外优化一下,所以最好遍历之前先判断类型,再选择使用何种方式遍历
 * 3 用位移来进行*2 /2的操作, 取中间位等操作; list交互位置l.set(y, l.set(x, l.get(y))); l.set()返回的是被替换之前的值
 * 4 Collections.unmodifiableCollection(不可变集合),
 *   Collections.synchronizedCollection(添加了同步的集合),
 *   Collections.checkedCollection(验证类型的集合)
 *   Collections.emptySet(空的集合,同时是unmodifiable不允许修改的)
 *   Collections.singleton(单个元素的集合,也是unmodifiable不允许修改的)
 *
 *
 */
public class CollectionsTest {

    private CollectionCreator creator = new CollectionCreator();

    /**
     * 排序,要使用排序,要排序的类必须实现Comparable接口或者传入一个Comparable
     * java8给List新增了排序方法sort
     */
    @Test
    public void testSort() {
        List<Person> pList = creator.getSimplePersonList();
        Collections.sort(pList);
        print(pList);
        System.out.println();
        Collections.sort(pList, Comparator.comparing(Person::getScore));
        print(pList);
    }

    /**
     * 二分法查找,但是前提是排好序的列表
     * 判断了list是实现了RandomAccess接口或者list的大小小于阙值 BINARYSEARCH_THRESHOLD(5000)的时候使用
     * if (list instanceof RandomAccess || list.size()<BINARYSEARCH_THRESHOLD)
         return Collections.indexedBinarySearch(list, key);
       else
         return Collections.iteratorBinarySearch(list, key);
     */
    @Test
    public void binarySearch() {

        List<String> words = creator.getWordsAsList();
        Collections.sort(words);
        // 如果查找到返回对应的位置,否则返回-1或者对应的插入点
        int good = Collections.binarySearch(words, "use");
        System.out.println(good);
        // 二分查找可以指定一个Comparator,用于与mid比较大小, 如果不传,默认使用类本身的compareTo
        Collections.binarySearch(words, "use", String.CASE_INSENSITIVE_ORDER);
    }

    private void print(Iterable it) {
        Iterator iterator = it.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    /**
     * 反转List,同样需要判断RandomAccess
     * 从中间位置进行反转,从mid开始,交换相对的位置
     */
    @Test
    public void reverse() {
        List<String> words = creator.getWordsAsList();
        Collections.sort(words);
        reverse(words);
    }

    public void reverse(List<?> list) {
        int size  = list.size();
        if(size < 5000 || list instanceof RandomAccess) {
            for (int i = 0, j = size - 1, mid = size >> 2; i < mid; i++, j--) {
                swap(list, i, j);
            }
        } else {
            // list.listIterator(index)可以指定从index处开始遍历, 可以灵活地向前与向后遍历
            ListIterator fwd = list.listIterator();
            ListIterator rev = list.listIterator(size);
            for (int i = 0, mid = size>>1; i < mid; i++) {
                Object tmp = fwd.next();
                fwd.set(rev.previous());
                rev.set(tmp);
            }
        }
    }

    public void swap(List<?> list, int x, int y) {
        List l = list;
        l.set(y, l.set(x, l.get(y)));
    }

    /**
     * 打乱list的顺序,用一个Random随机替换, 从最大到最小一直替换
     */
    @Test
    public void shuffle() {
        List<String> words = creator.getWordsAsList();
        Collections.sort(words);

        shuffle(words, new Random(50));
        Collections.shuffle(words);
    }

    public void shuffle(List<?> list, Random rand) {
        int size = list.size();
        for(int i = size; i > 1; i--) {
            // random.nextInt(i)能保证已经替换过的不会再次被替换
            swap(list, i-1, rand.nextInt(i));
        }
    }

    /**
     * 使用一个对象填充list,list数组中所有元素指向同一个对象
     */
    @Test
    public void fill() {
        List<Person> persons = creator.getSimplePersonList();
        Person wei = new Person(10L, "wei", 80);
        Collections.fill(persons, wei);
        wei.score = 100;

        List<Integer> ints = new ArrayList<>();
        ints.add(0);
        ints.add(1);
        ints.add(2);
        Integer i = 100;
        Collections.fill(ints, i);
    }

    /**
     * Collections.copy,因为使用的是List.set()方法,要求源与目标大小一样,这很操蛋
     */
    @Test
    public void copy() {
        List<String> words = new ArrayList<>();
        List<String> target = new ArrayList<>();
        words.add("bbb");
        target.add("aa");
        Collections.copy(target, words);
    }

    /**
     * 获取最小值
     */
    @Test
    public void min() {
        List<String> words = creator.getWordsAsList();
        String min = Collections.min(words);
        System.out.println("min=[" + min + "]");

        Iterator<String> it = words.iterator();
        String candidate = it.next();
        while (it.hasNext()) {
            String next = it.next();
            if(next.compareTo(candidate) > 0) {
                candidate = next;
            }
        }
        System.out.println("max=[" + candidate + "]");
    }

    /**
     * 将list向前或者向后移位
     */
    @Test
    public void rotate() {
        List<Integer> range = creator.createRange(5);
        rotate(range, 3);
        System.out.println(range);
    }

    private <T> void rotate(List<T> list, int distance) {
        if(list instanceof RandomAccess || list.size() < 5000) {
            rotate1(list, distance);
        } else {
            rotate2(list, distance);
        }
    }

    /**
     * 移位算法1
     * @param list
     * @param distance
     * @param <T>
     */
    private <T> void  rotate1(List<T> list, int distance) {
        int size = list.size();
        if(size == 0) {
            return;
        }

        // distance向后移位
        distance = distance % size;
        // 小于0表明向前移位, 等价于向后移动size-distance位
        if(distance < 0) {
            distance += size;
        }
        // distance == 0 表明不需要移位
        if(distance == 0) {
            return;
        }

        // 每次跳distance步,每个元素都需要跳步,cycleStart=0,nMoved=0
        for(int cycleStart = 0, nMoved = 0; nMoved != size; cycleStart++) {
            // displaced获取第一个替换元素
            T displaced = list.get(cycleStart);
            int i = cycleStart;
            do {
                // 跳distance步
                i += distance;
                // 如果超出size,表明已经跳出最后一个元素,需要往前面跳
                if(i >= size) {
                    i -= size;
                }
                // 替换元素
                displaced = list.set(i, displaced);
                nMoved ++;
            }
            // 如果又跳到起点,则从起点的下一个元素开始跳转
            while (i != cycleStart);
        }
    }

    /**
     * 移位算法2
     * @param list
     * @param distance
     */
    private void rotate2(List<?> list, int distance) {
        int size = list.size();
        if(size == 0) {
            return;
        }

        // 找一个中间点,向后移动,从倒数第distance个算起, 向前移动,从起点第distance个算起
        int mid = - distance % size;
        if(mid < 0) {
            mid += size;
        }
        if(mid == 0) {
            return;
        }

        // 倒转0 mid
        Collections.reverse(list.subList(0, mid));
        // 倒转mid size
        Collections.reverse(list.subList(mid, size));
        // 最后倒转
        Collections.reverse(list);
    }

    /**
     * 替换,当匹配到值时替换,并且返回值为true
     */
    @Test
    public void replaceAll() {
        List<String> words = creator.getWordsAsList();
        Collections.sort(words);
        System.out.println(Collections.replaceAll(words, "A", "A罗立兵"));
        System.out.println(replaceAll(words, "B", "Beyond"));
    }

    public <T> boolean replaceAll(List<T> list, T oldVal, T newVal) {
        boolean result = false;
        int size = list.size();
        if(oldVal == null) {
            for(int i = 0; i < size; i++) {
                if(list.get(i) == null) {
                    list.set(i, newVal);
                    result = true;
                }
            }
        } else {
            for(int i = 0; i < size; i++) {
                if(Objects.equals(list.get(i), oldVal)) {
                    list.set(i, oldVal);
                    result = true;
                }
            }
        }
        return result;
    }

    /**
     * 跳出外层循环,使用break label;标签技术
     */
    @Test
    public void continueTest() {

        liu:
        for(int k = 0; k < 100; k++) {
            luo:
            for (int i = 0; i < 100; i++) {
                for (int j = 0; j < 100; j++) {
                    System.out.println(i + ":" + j);
                    if (j > i + 40) {
                        continue liu;
                    }
                }
            }
        }
    }

    /**
     * 查找子list是否在另一个大的list当中
     */
    @Test
    public void indexOfSubList() {
        List<Integer> list = creator.createRange(10);
        List<Integer> subList = new ArrayList<>();
        subList.add(6);
        subList.add(7);
        subList.add(8);
        System.out.println(Collections.indexOfSubList(list, subList));
        System.out.println(indexOfSubList(list, subList));
    }

    /**
     * 穷举法查找,朴素匹配
     * @param source
     * @param target
     * @return
     */
    private int indexOfSubList(List<?> source, List<?> target) {
        int sourceSize = source.size();
        int targetSize = target.size();
        // 最大可用候选区
        int maxCandidate = sourceSize - targetSize;

        nextCand:
        // 从0到最大可用候选区
        for(int candidate = 0; candidate <= maxCandidate; candidate ++) {
            // 每次最多匹配targetSize个,如果匹配完,没有continue表明匹配上,返回
            for(int i = 0, j = candidate; i < targetSize; i++, j++) {
                if(!target.get(i).equals(source.get(j))) {
                    continue  nextCand;
                }
            }
            return candidate;
        }
        return -1;
    }

    /**
     * unmodifiable集合和Map
     * 不允许更改的集合,通过集成Collection实现,add(obj) throw new UnsupportedOperationException()异常
     */
    @Test
    public void unmodifiableCollection() {
        List<Integer> numbers = Collections.unmodifiableList(creator.createRange(10));
        System.out.println(numbers.get(0));
    }

    /**
     * synchronized
     * 同步集合或者Map
     */
    @Test
    public void synchronizedCollection() {
        Collections.synchronizedList(creator.createRange(10));
    }

    private Collection<Integer> collection() {
        return new ArrayList<>();
    }

    private Collection<Integer> checkCollection() {
        return Collections.checkedCollection(new ArrayList<>(), Integer.class);
    }

    // 类型丢失, 返回值是带泛型的Collection<Integer>,但是如果用一个不带泛型的声明接收,导致类型丢失
    @Test
    public void checkCollection1() {
        Collection collection = collection();
        collection.add("string");
        System.out.println(collection);

        // 使用checkedCollection,即使类型擦除,也会去检测对应的类型是否正确,如果不正确会抛出ClassCastException
        Collection checkCollection = checkCollection();
        checkCollection.add("aaa");
        System.out.println(checkCollection);
    }

    /**
     * 获取大小为0的数组
     */
    @Test
    public void zeroLengthArray() {
        // 使用Array,直接初始化数组
        Integer[] arr1 = (Integer[])Array.newInstance(Integer.class, 0);
        System.out.println(arr1);
        // 生成多维数组
        Object o = Array.newInstance(int.class, 1, 2);
        System.out.println(o);
    }

    /**
     * 空集合
     */
    @Test
    public void emptyIterator() {
        Iterator<Object> it = Collections.emptyIterator();
        System.out.println(it.hasNext());
        Map<Object, Object> map = Collections.emptyMap();
        List<Object> list = Collections.emptyList();
        Set<Object> set = Collections.emptySet();
        set.add("aaa");
    }

    /**
     * 单个,不能修改
     */
    @Test
    public void singleton() {
        // 单例的unmodifiable
        Set<String> singleton = Collections.singleton("aaa");
        Map<String, Integer> map = Collections.singletonMap("aaa", 1);
    }

    /**
     * 创建出n个相同元素的集合, 但是指向的还是同一个对象
     */
    @Test
    public void nCopies() {
        Person person = new Person(1L, "luolibing", 100);
        List<Person> list = Collections.nCopies(10, person);
        System.out.println(list);
        person.name = "liuxiaoling";
    }

    /**
     * 反转comparator, 将原来的o1.compareTo(o2),改变顺序成o2.compareTo(o1),就能取之前的反序
     */
    @Test
    public void reverseOrder() {
        List<Integer> range = creator.createRange(10);
        Collections.sort(range, Collections.reverseOrder());
        System.out.println(range);

        List<Integer> range2 = creator.createRange(20);
        Collections.sort(range2, ReverseComparator.REVERSE_ORDER);
        System.out.println(range2);
    }

    @Test
    public void reverseOrder1() {

    }

    static class ReverseComparator implements Comparator<Comparable<Object>>, Serializable {

        public final static Comparator REVERSE_ORDER = new ReverseComparator();

        @Override
        public int compare(Comparable<Object> o1, Comparable<Object> o2) {
            return o2.compareTo(o1);
        }

        @Override
        public Comparator<Comparable<Object>> reversed() {
            // 自然序
            return Comparator.naturalOrder();
        }
    }

    /**
     * 包装一个Comparator,并且将其进行反转
     * @param <T>
     */
    class ReverseComparator1<T> implements Comparator<T>, Serializable {

        private final Comparator<T> comparator;

        public ReverseComparator1(Comparator<T> comparator) {
            assert comparator != null;
            this.comparator = comparator;
        }

        @Override
        public int compare(T o1, T o2) {
            return comparator.compare(o2, o1);
        }

        @Override
        public Comparator<T> reversed() {
            return comparator;
        }
    }

    /**
     * 判断一个集合里面有几个制定对象
     */
    @Test
    public void frequency() {
        List<Integer> range = creator.createRange(10);
        range.addAll(creator.createRange(10));
        int frequency = Collections.frequency(range, 10);
        System.out.println(frequency);
    }

    /**
     * 判断是否不相交
     */
    @Test
    public void disjoint() {
        System.out.println(Collections.disjoint(creator.createRange(10), creator.createRange(10)));
        System.out.println(disjoint(creator.createRange(10), creator.createRange(10)));
    }

    public boolean disjoint(Collection<?> c1, Collection<?> c2) {
        // contains需要查找的性能好, Set的查找性能是很好的
        Collection contains = c2;
        Collection iterate = c1;

        if(c1 instanceof Set) {
            contains = c1;
            iterate = c2;
        } else if (!(c2 instanceof Set)) {
            // 如果c1 c2都不为set
            int c1Size = c1.size();
            int c2Size = c2.size();
            if(c1Size == 0 || c2Size == 0) {
                return true;
            }
            if(c1Size > c2Size) {
                iterate = c2;
                contains = c1;
            }
        }

        for(Object obj : iterate) {
            if(contains.contains(obj)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 添加所有元素, 可以传入数组
     */
    @Test
    public void addAll() {
        List<Integer> range = creator.createRange(10);
        Collections.addAll(range, new Integer[] {10, 12, 13});
        System.out.println(range);
    }

    @Test
    public void newSetFromMap() {
        Map<String, Boolean> map = new HashMap<>();
        Set<String> set = Collections.newSetFromMap(map);
        set.add("a");
        set.add("b");
        set.add("c");
        map.put("d", false);
        System.gc();
        System.out.println(set);

        // 创建ConcurrentHashSet, 使用ConcurrentHashMap转换而来
        Set<String> concurrentHashSet = Collections.newSetFromMap(new ConcurrentHashMap<>());
        System.out.println(concurrentHashSet);
    }

    @Test
    public void weakHashMap() {
        Map<String, Integer> map = new WeakHashMap<>();
        String str1 = new String("a");
        String str2 = new String("b");
        map.put(str1, 1);
        map.put(str2, 2);
        System.out.println(map);
        str1 = null;
        System.gc();
        System.out.println(map);
    }

    /**
     * 双端队列转换成后进先出队列
     */
    @Test
    public void asLifoQueue() {
        Queue<String> queue = Collections.asLifoQueue(new LinkedList<>());
        queue.add("aaa");
        queue.add("bbb");
        while (queue.peek() != null) {
            System.out.println(queue.remove());
        }
    }
}
