package cn.tim.thinking.collection.simple11.arrays;

import cn.tim.thinking.collection.simple11.collections.CollectionCreator;
import cn.tim.thinking.collection.simple11.collections.Person;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;

/**
 * Created by LuoLiBing on 16/9/29.
 */
public class ArraysTest {

    private CollectionCreator creator = new CollectionCreator();

    /**
     * 排序
     */
    @Test
    public void sort() {
        Integer[] array = creator.createRandomArray(10);
        Arrays.sort(array);
        System.out.println(Arrays.toString(array));
    }

    /**
     * 并行排序
     */
    @Test
    public void parallelSort() {
        Integer[] array = creator.createRandomArray(1000000);
        Arrays.parallelSort(array);
    }

    /**
     * 与前一个元素一起操作
     */
    @Test
    public void parallelPrefix() {
        Integer[] array = creator.createRangeArray(10000);
        Arrays.parallelPrefix(array, (x, y) -> x + y );
        System.out.println(Arrays.toString(array));
    }

    /**
     * 二分法查找
     */
    @Test
    public void binarySearch() {
        System.out.println(Arrays.binarySearch(creator.createRangeArray(1000), 500));
    }

    /**
     * 比较数组集合
     */
    @Test
    public void equals() {
        System.out.println(Arrays.equals(creator.createRangeArray(100), creator.createRangeArray(100)));
    }

    /**
     * 填充数组
     */
    @Test
    public void fill() {
        int[] arr = new int[1000];
        Arrays.fill(arr, 100);
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 拷贝
     */
    @Test
    public void copyOf() {
        Person[] persons = creator.getBigTableList().toArray(new Person[]{});
        Person[] persons1 = Arrays.copyOf(persons, 10);
        System.out.println(Arrays.toString(persons1));

        Integer[] randomArray = creator.createRandomArray(100);
        Integer[] integers = Arrays.copyOf(randomArray, 100);
    }

    /**
     * 局部拷贝
     */
    @Test
    public void copyOfRange() {
        Integer[] array = creator.createRangeArray(100);
        Integer[] result = Arrays.copyOfRange(array, 10, 20);
        System.out.println(Arrays.toString(result));
    }

    /**
     * 将数组转换成list
     */
    @Test
    public void asList() {
        List<Integer> list = Arrays.asList(creator.createRandomArray(10));
        System.out.println(list);
    }

    /**
     * 求数组的hashCode
     */
    @Test
    public void hashCode1() {
        int[] arr = new int[10];
        System.out.println(Arrays.hashCode(arr));
    }

    /**
     * 深度hashCode
     */
    @Test
    public void deepHashCode() {
        List<Person> persons = creator.getSimplePersonList();
        Object[] objects = persons.toArray();
        // warn 获取array的hashCode应该使用Arrays.hashCode()
        // System.out.println(objects.hashCode());
        System.out.println(Arrays.hashCode(objects));
        System.out.println(Arrays.deepHashCode(objects));
    }

    /**
     * 深度equals
     */
    @Test
    public void deepEquals() {
        Arrays.deepEquals(creator.getSimplePersonList().toArray(), creator.getSimplePersonList().toArray());
    }

    /**
     * Arrays.toString(array) 数组toString()
     */
    @Test
    public void toString1() {
        System.out.println(Arrays.toString(creator.createRangeArray(100)));
        System.out.println(Arrays.toString(creator.getSimplePersonList().toArray()));
        // deepToString()
        System.out.println(Arrays.deepToString(creator.getSimplePersonList().toArray()));
    }

    /**
     * setAll 用生成对象的闭包填充数组
     */
    @Test
    public void setAll() {
        Person[] persons = new Person[10];
        Arrays.setAll(persons, (index) -> new Person((long) index, "luolibing", 100));
        System.out.println(Arrays.toString(persons));
    }

    /**
     * 并行setAll
     */
    @Test
    public void parallelSetAll() {
        int[] array = new int[10000000];
        Arrays.parallelSetAll(array, (index) -> index + 1);
        System.out.println(Arrays.toString(array));
    }

    @Test
    public void spliterator() {
        Spliterator<Integer> spliterator = Arrays.spliterator(creator.createRangeArray(1000));
        for(int i = 0; i < 3; i++) {
            spliterator = spliterator.trySplit();
        }
        while (spliterator.tryAdvance(System.out::println)) {
            spliterator.forEachRemaining(System.out::println);
        }

        int[] range = new int[10000];
        Arrays.fill(range, 2);
        Spliterator.OfInt ofInt = Spliterators.spliterator(range, 250);
    }

    @Test
    public void stream() {
        Spliterator<Integer> sp = Arrays.spliterator(creator.createRangeArray(100));
        split(sp);
    }

    private void split(Spliterator spliterator) {
        Spliterator sp = spliterator.trySplit();
        if(sp != null) {
            sp.tryAdvance(System.out::println);
        }
        System.out.println();
        spliterator.forEachRemaining(System.out::println);
    }

    @Test
    public void stream1() {
        creator.createRange(8).parallelStream().forEach((x) -> {
            long id = Thread.currentThread().getId();
            System.out.println(id);
        } );
    }
}
