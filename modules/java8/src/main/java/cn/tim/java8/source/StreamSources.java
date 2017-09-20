package cn.tim.java8.source;

import org.junit.Test;

import java.util.List;
import java.util.Spliterator;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * User: luolibing
 * Date: 2017/9/20 10:12
 */
public class StreamSources {

    /**
     * Stream的原理，几个重要的组成
     * Spliterator  分割
     */
    @Test
    public void spliterator1() throws InterruptedException {
        // 正常分割，forEachRemaining，执行剩下的元素，forEach默认就是使用的这个执行
        Spliterator<String> spliterator = mockList().spliterator();
        // 执行剩下的
        spliterator.forEachRemaining(System.out::println);

        IntStream.range(0, 10).forEach(i -> System.out.println());
        Spliterator<String> sp1 = mockList().spliterator();
        // 尝试拆分
        Spliterator<String> sp2 = sp1.trySplit();
        Spliterator<String> sp3 = sp2.trySplit();
        Spliterator<String> sp4 = sp1.trySplit();
        // 预估spliterator的大小
        System.out.println(sp1.estimateSize());
        System.out.println(sp2.estimateSize());
        System.out.println(sp3.estimateSize());
        System.out.println(sp4.estimateSize());

        int cors = Runtime.getRuntime().availableProcessors();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                cors, cors, 60, TimeUnit.SECONDS, new LinkedBlockingDeque<>(100), new ThreadPoolExecutor.AbortPolicy());
        executor.execute(() -> {
            sp1.forEachRemaining(System.out::println);
        });
        executor.execute(() -> {
            sp2.forEachRemaining(System.out::println);
        });
        executor.execute(() -> {
            sp3.forEachRemaining(System.out::println);
        });
        executor.execute(() -> {
            sp4.forEachRemaining(System.out::println);
        });

        executor.awaitTermination(30, TimeUnit.SECONDS);
    }

    @Test
    public void spliterator() {
        Spliterator<String> sp = mockList().spliterator();
        // 特性元素，查看其是否包含以下特性
        int characteristics = sp.characteristics();
        System.out.println(characteristics);
        System.out.println("concurrent = " + (sp.hasCharacteristics(Spliterator.CONCURRENT)));
        System.out.println("distinct = " + (sp.hasCharacteristics(Spliterator.DISTINCT)));
        System.out.println("immutable = " + (sp.hasCharacteristics(Spliterator.IMMUTABLE)));
        System.out.println("nonnull = " + (sp.hasCharacteristics(Spliterator.NONNULL)));
        System.out.println("ordered = " + (sp.hasCharacteristics(Spliterator.ORDERED)));
        System.out.println("sized = " + (sp.hasCharacteristics(Spliterator.SIZED)));
        System.out.println("subsized = " + (sp.hasCharacteristics(Spliterator.SUBSIZED)));

        // 当characteristics有SIZE特征时返回预估大小，否则返回-1
        long size = sp.getExactSizeIfKnown();
        System.out.println(size);

        // 是否包含某个特征，利用位运算 characteristics & Spliterator.CONCURRENT == Spliterator.CONCURRENT来进行判断
        boolean concurrent = sp.hasCharacteristics(Spliterator.CONCURRENT);
        System.out.println(concurrent);

        // 尝试向前执行一步
        sp.tryAdvance(System.out::println);
    }

    @Test
    public void stream1() {
        // 非多线程的直接forEach,底层使用的ReferencePipeline.Head进行处理，使用单个spliterator直接调用forEachRemaining
        mockList().stream().forEach(System.out::println);

        // 等同于以下操作
        Spliterator<String> sp = mockList().spliterator();
        sp.forEachRemaining(System.out::println);

        // 等同于下面这个
        sp = mockList().spliterator();
        do { } while (sp.tryAdvance(System.out::println));
    }

    @Test
    public void stream2() {
        Stream<String> stream = mockList().stream();
        stream.map(s -> s + "A").forEach(System.out::println);

    }

    @Test
    public void stream3() {
        List<Person> persons = IntStream.range(0, 10)
                .mapToObj(i -> new Person())
                .collect(Collectors.toList());

        for(Person p : persons) {
            System.out.println(p);
        }

        System.out.println();

        List<Person> person2 = persons.stream().peek(p -> {
            p.id = p.id + 100;
        }).collect(Collectors.toList());

        System.out.println(person2);

        persons.get(0).id = 200;
        System.out.println();

        persons.forEach(System.out::println);

        // 事实证明，stream中并不是说其中的对象在forEach或者collect之后并不是值不可变，而是对象不可变，还是同一个对象，同一个地址
        // 所以在使用collect时并不需要担心它的性能会降低。只是其中的list集合会发生变化，这个时候重新new了对象。
        System.out.println(person2.get(9) == persons.get(9));
    }

    static class Person {
        static long counter = 0;
        long id = counter ++;

        @Override
        public String toString() {
            return "Person{" +
                    "id=" + id +
                    '}';
        }
    }

    static <T> void apply(Consumer<T> consumer, T t) {
        consumer.accept(t);
    }

    @Test
    public void imm() {
        Person person = new Person();
        System.out.println(person + ", id = " + person.id);
        apply((p) -> p.id = 100, person);
        System.out.println(person + ", id = " + person.id);
    }

    private List<String> mockList() {
        return IntStream.range(0, 1000)
                .mapToObj(i -> UUID.randomUUID().toString())
                .collect(Collectors.toList());
    }

}
