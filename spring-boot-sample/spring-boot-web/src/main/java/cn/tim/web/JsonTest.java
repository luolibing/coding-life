package cn.tim.web;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * User: luolibing
 * Date: 2018/3/12 10:07
 */
public class JsonTest {

    public static void main(String[] args) {
        Class<?>[] classes = JsonTest.class.getClasses();
        for(Class<?> clazz : classes) {
            System.out.println(clazz.getName());
        }

        List<Integer> list = Arrays.asList(1, 2, 3);
        Map<Integer, List<Integer>> collect = IntStream.range(0, list.size())
                .boxed()
                .collect(Collectors.toMap(Function.identity(), v -> list));
    }

    public class Person implements Serializable {
        public Integer a;
        public String name;
        private List<String> b;
    }
}
