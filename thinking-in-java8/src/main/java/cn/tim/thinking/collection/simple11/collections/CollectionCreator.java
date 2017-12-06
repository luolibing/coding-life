package cn.tim.thinking.collection.simple11.collections;

import cn.tim.thinking.Exercise;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Created by LuoLiBing on 16/9/28.
 */
public class CollectionCreator implements Exercise {

    public List<Person> getSimplePersonList() {
        List<Person> list = new ArrayList<>();
        list.add(new Person(3L, "jiangtengfei", 50));
        list.add(new Person(2L, "luominghao", 90));
        list.add(new Person(0L, "wangwu", 65));
        list.add(new Person(1L, "liuxiaoling", 80));
        list.add(new Person(4L, "zhangsan", 100));
        return list;
    }

    private final Random rand = new Random(47);

    private final AtomicInteger atomic = new AtomicInteger(0);

    public List<Person> getBigTableList() {
        List<String> words = getWordsAsList();
        return words.parallelStream()
                .map( word -> new Person((long)atomic.getAndIncrement(), word, rand.nextInt(100)))
                .collect(Collectors.toList());
    }

    public List<Integer> createRange(int count) {
        List<Integer> result = new ArrayList<>();
        for(int i = 1; i <= count; i++) {
            result.add(i);
        }
        return result;
    }

    public Integer[] createRangeArray(int count) {
        Integer[] array = new Integer[count];
        for(int i = 1; i <= count; i++) {
            array[i - 1] = i;
        }
        return array;
    }

    public Integer[] createRandomArray(int count) {
        Integer[] result = new Integer[count];
        for(int i = 0; i < count; i++) {
            result[i] = new Random().nextInt(count);
        }
        return result;
    }

    @Test
    public void test1() {
        Integer i = 200;
        System.out.println(i);
        Integer j = 200;
        System.out.println(j);
        int l = 4;
        System.out.println(l>>>2);
    }
}
