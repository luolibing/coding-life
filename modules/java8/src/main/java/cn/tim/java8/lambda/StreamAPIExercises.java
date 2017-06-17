package cn.tim.java8.lambda;

import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

/**
 * Created by LuoLiBing on 16/7/2.
 */
public class StreamAPIExercises implements Exercise {

    private static List<String> getWords() throws IOException {
        String contents = new String(Files.readAllBytes(Paths.get("/Users/tim/Downloads/jquery.min.js")), StandardCharsets.UTF_8);
        return Arrays.asList(contents.split("[\\P{L}]+"));
    }

    /**
     * @throws IOException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void concurrentStream() throws IOException, ExecutionException, InterruptedException {
        int processors = Runtime.getRuntime().availableProcessors();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(processors, processors, 200, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(5));
        String contents = new String(Files.readAllBytes(Paths.get("/share/rms_resource.log")), StandardCharsets.UTF_8);
        List<String> words = Arrays.asList(contents.split("[\\P{L}]+"));
        Future<Stream<Integer>> submit = null;
        final int count = words.size() / processors;
        for (int i = 0; i < count; i++) {
            List<String> subList = null;
            if (i == count - 1) {
                subList = words.subList(i * processors, words.size());
            } else {
                subList = words.subList(i * processors, (i + 1) * processors);
            }
            final List<String> finalSubList = subList;
            submit = executor.submit(() -> finalSubList.stream().map((s) -> s.length() > 12 ? 1 : 0));
        }
        final int[] sum = {0};
        if (submit != null) {
            while (!submit.isDone()) {
                submit.get().findFirst().ifPresent(s -> sum[0] += s);
            }
        }
        System.out.println(sum);
    }

    @Test
    public void filter() throws IOException {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        final Stream<String> limit = getWords().stream().filter((w) -> {
            System.out.println(w);
            if (w.length() > 2) {
                atomicInteger.incrementAndGet();
            }
            return w.length() > 2;
        }).limit(10);
        limit.forEach(System.out::println);
        System.out.println(atomicInteger.get());
    }

    @Test
    public void stream() throws IOException {
        long startTime = System.nanoTime();
        getWords().stream().filter(s -> s.length() > 2);
        System.out.println(System.nanoTime() - startTime);
    }

    @Test
    public void parallelStream() throws IOException {
        long startTime = System.nanoTime();
        getWords().parallelStream().filter(s -> s.length() > 2);
        System.out.println(System.nanoTime() - startTime);
    }

    @Test
    public void random() {
        Stream.generate(Math::random).limit(100).forEach(System.out::println);
    }

    @Test
    public void characterStream() {
        String str = "abfgdfdafdsfdslllottpfxZg";
        final Stream<Character> characterStream = Stream.iterate(0, n -> n + 1).limit(str.length()).map(str::charAt);
        characterStream.forEach(System.out::println);
    }

    @Test
    public void isFiniteTest() {
        isFinite(Stream.generate(Math::random));
    }

    public static <T> boolean isFinite(Stream<T> stream) {
        return true;
    }

    // TODO: 16/7/4
//    public static <T> Stream<T> zip(Stream<T> first, Stream<T> second) {
//        AtomicInteger atomicInteger = new AtomicInteger(0);
//        first.collect((t) -> {
//            second.skip(atomicInteger.getAndIncrement());
//            return second.findFirst().orElse(null);
//        });
//    }

    @Test
    public void optionalNull() {
        final Optional<Object> o = Optional.of(null);
        System.out.println(o.orElseGet(() -> "luolibing"));
    }

    /***********************stream正解*****************/
    /**
     * 多线程统计单词长度大于12的个数,然后汇总最终的结果
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void test1() throws ExecutionException, InterruptedException {
        int start = LocalTime.now().getNano();
        final List<String> words = getWordsAsList();
        System.out.println(countConcurrentWithoutStreams(words));
        System.out.println("time=" + (LocalTime.now().getNano() - start));
    }

    @Test
    public void test01() {
        int start = LocalTime.now().getNano();
        System.out.println(getWordsAsList().stream().filter(s -> s.length() > 12).count());
        System.out.println("time=" + (LocalTime.now().getNano() - start));
    }

    private static long countConcurrentWithoutStreams(List<String> words) throws ExecutionException, InterruptedException {
        // 查看有几核心
        int cores = Runtime.getRuntime().availableProcessors();
        int chunkSize = words.size() / cores;
        List<List<String>> chunks = new ArrayList<>();
        // 先分组
        for (int i = 0; i < words.size(); i += chunkSize) {
            chunks.add(words.subList(i, i + Integer.min(chunkSize, words.size() - i)));
        }
        // 线程池处理
        ExecutorService pool = Executors.newFixedThreadPool(cores);
        Set<Future<Long>> set = new HashSet<>();
        for (List<String> chunk : chunks) {
            Callable<Long> callable = () -> {
                long count = 0;
                for (String word : chunk) {
                    if (word.length() > 12) {
                        count++;
                    }
                }
                return count;
            };
            // set收集Future
            set.add(pool.submit(callable));
        }
        long count = 0;
        // 最后使用Future集合获取最终结果
        for (Future<Long> future : set) {
            count += future.get();
        }
        // 获取完之后停掉线程池
        pool.shutdown();
        return count;
    }

    /**
     * 找出前5个长度大于12的单词,一旦找到,将直接退出循环
     */
    @Test
    public void test2() {
        getWordsAsList().stream().filter(s -> s.length() > 12).limit(5).forEach(System.out::println);
    }

    /**
     * 比较stream和parallelStream的区别
     */
    @Test
    public void test3() {
        List<String> words = getWordsAsList();
        for(int i=0; i< 100; i++) {
            long streamCount = countDuration(words.stream());
            long parallelCount = countDuration(words.parallelStream());
            System.out.println(streamCount + ":" + parallelCount);
        }
    }

    private static long countDuration(Stream<String> stream) {
        long start = System.currentTimeMillis();
        stream.filter(s -> s.length()>12).count();
        return System.currentTimeMillis() - start;
    }

    /**
     * 获取int类型流
     */
    @Test
    public void intStream() {
        int[] values = {1, 4, 6546, 3};
        // 返回的是一个int[]数组Stream
        Stream<int[]> values1 = Stream.of(values);
        System.out.println(values1);

        // 两种方法获取到IntStream
        // 1 使用IntStream.of()方法
        IntStream values2 = IntStream.of(values);
        values2.forEach(System.out::println);

        // 2 使用Arrays.stream()方法
        final IntStream stream = Arrays.stream(values);
    }

    /**
     * characterStream
     */
    @Test
    public void test4() {
        characterStream("abcdefg").forEach(System.out::println);
    }

    // 使用了IntStream.range()产生一个序列,然后再将其作为index遍历string
    private static Stream<Character> characterStream(String string) {
        return IntStream.range(0, string.length()).mapToObj(string::charAt);
    }

    /**
     *
     */
    @Test
    public void test5() {
        zip(Stream.of("a","b","c"), Stream.of("5","6")).peek(System.out::println).count();
        zip(Stream.of("a"), Stream.of("1", "2")).peek(System.out::println).count();
    }

    public static <T> Stream<T> zip(Stream<T> first, Stream<T> second) {
        Iterator<T> secondIterator = second.iterator();
        Stream.Builder<T> builder = Stream.builder();
        first.forEach( (e) -> {
            // 只要第二个stream没有了,直接关闭掉first,循环结束,如果first没有了会自动关闭
            if(secondIterator.hasNext()) {
                builder.accept(e);
                builder.accept(secondIterator.next());
            } else {
                first.close();
            }
        });
        return builder.build();
    }


    @Test
    public void flatList() {
        ArrayList<String> list1 = new ArrayList<>();
        list1.add("aaa");
        list1.add("bbb");
        ArrayList<String> list2 = new ArrayList<>();
        list2.add("ccc");
        list2.add("ddd");
        // 1 使用collect
        Stream<ArrayList<String>> stream = Stream.of(list1, list2);
        ArrayList<String> collect = stream.collect(ArrayList<String>::new, ArrayList::addAll, ArrayList::addAll);
        collect.forEach(System.out::println);

        // 2 使用flatMap
        Stream<ArrayList<String>> stream1 = Stream.of(list1, list2);
        List<String> collect1 = stream1.flatMap(Collection::stream).collect(Collectors.toList());
        System.out.println(collect1);

        // 3 使用reduce()   l,e是要操作的两个元素,将两个元素组合起来
        Stream.of(list1, list2).reduce((l, e) -> {
            ArrayList<String> list = new ArrayList<>(l);
            list.addAll(e);
            return list;
        }).orElse(new ArrayList<>());

        // 4 reduce(identify) identify作为起点
        Stream.of(list1, list2).reduce(
                new ArrayList<>(),
                (f, s) -> {
                    ArrayList<String> list = new ArrayList<>(f);
                    list.addAll(s);
                    return list;
                }
        );

        // 4 reduce
        Stream.of(list1, list2).reduce(
                new ArrayList<String>(),
                (f, s) -> {
                    ArrayList<String> list = new ArrayList<>(f);
                    list.addAll(s);
                    return list;
                },
                (f, s) -> {
                    ArrayList<String> list = new ArrayList<>(f);
                    list.addAll(s);
                    return list;
                }
        );
    }

    /**
     * 求平均值
     */
    @Test
    public void test6() {
        average(Stream.of(2.0, 3.0, 4.0, 8.0));
    }

    public double average(Stream<Double> stream) {
        return stream.reduce(new Averager(), Averager::accept, Averager::combine).average();
    }

    class Averager {
        final long count;
        final Double value;

        Averager() {
            this.count = 0;
            this.value = 0.0;
        }

        Averager(long count, Double value) {
            this.count = count;
            this.value = value;
        }

        Averager accept(Double value) {
            return new Averager(this.count + 1, this.value + value);
        }

        Averager combine(Averager averager) {
            return new Averager(this.count + averager.count, this.value + averager.value);
        }

        double average() {
            return value / count;
        }
    }

    @Test
    public void test7() {
        List<ArrayList<String>> list = new ArrayList<>();
        list.add(new ArrayList<>(Arrays.asList("01", "02", "03")));
        list.add(new ArrayList<>(Arrays.asList("04", "05")));
        list.add(new ArrayList<>(Arrays.asList("06", "07", "08", "09", "10")));
        assertEquals(10, collect(list.stream()).size());
    }

    public List<String> collect(Stream<ArrayList<String>> stream) {
        String[] array = stream.flatMap(Collection::stream).toArray(String[]::new);
        IntStream range = IntStream.range(0, array.length);
        List<String> result = new ArrayList<>(Arrays.asList(new String[array.length]));
        range.parallel().forEach(e -> result.set(e, array[e]));
        return result;
    }

    /**
     * 计算所有段单词的个数
     */
    @Test
    public void test8() {
        List<String> words = getWordsAsList();
        AtomicInteger[] atomicIntegers = new AtomicInteger[12];
        words.parallelStream().forEach(word -> {
            int len = word.length();
            if(len <12) {
                AtomicInteger atomicInteger = atomicIntegers[len];
                if(atomicInteger == null) {
                    atomicIntegers[len] = new AtomicInteger(0);
                }
                atomicIntegers[len].incrementAndGet();
            }
        });
        Arrays.stream(atomicIntegers).forEach(System.out::println);
    }

    /**
     * 使用collect收集结果,使用groupingBy(p1, p2), p1为分组条件参数,p2为Collector
     */
    @Test
    public void test9() {
        List<String> words = getWordsAsList();
        Map<Integer, Long> countMap = words.stream().collect(
                Collectors.groupingBy(
                        String::length,
                        Collectors.counting()
                ));
        countMap.forEach((key, val) -> System.out.println(key + ":" + val));
    }
}
