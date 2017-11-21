package cn.tim.javaone2017.dbvsstream;

import java.util.stream.Stream;

/**
 * User: luolibing
 * Date: 2017/11/21 11:00
 */
public class StreamVsDB {

    /**
     * select * from film where name='war' order by name desc limit 10
     */
    public void test1() {
        // stream() == from, filter == where, limit , order by == sorted
        Stream.of("war", "great")
                .parallel()
                .filter(n -> n.equals("great"))
                .sorted()
                .limit(10)
                .forEach(this::doSomeThing);
    }

    private void doSomeThing(String film) {
        System.out.println(film);
    }
}
