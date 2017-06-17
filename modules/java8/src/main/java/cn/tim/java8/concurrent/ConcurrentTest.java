package cn.tim.java8.concurrent;

import cn.tim.java8.lambda.Exercise;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by LuoLiBing on 16/7/19.
 */
public class ConcurrentTest implements Exercise {

    @Test
    public void test1() {
        // atomicReference原子操作引用
        AtomicReference<String> longest = new AtomicReference<>();
        // 累加器,求最大值 (x,y) -> return.max(x,y)求最大值,0为初始值
        LongAccumulator adder = new LongAccumulator(Long::max, 0);
        getWordsAsList().parallelStream().forEach(
                // 原子更新
                next -> longest.updateAndGet(
                        current -> {
                            String result = next.length() > adder.intValue() ? next : current;
                            // 找出最大长度
                            adder.accumulate(next.length());
                            return result;
                        }
                )
        );
        System.out.println(longest.get());
    }

    @Test
    public void test2_0() {
        ExecutorService executor = Executors.newFixedThreadPool(1000);
        long start = System.currentTimeMillis();
        AtomicLong count = new AtomicLong(0);
        // futures 未来数组,
        CompletableFuture[] futures = new CompletableFuture[1000];
        // forEach(IntConsumer.accept(i))
        IntStream.range(0, 1000).forEach( i ->
                // 创建可完成的Future   runAsync异步调用Runnable线程,使用Executor线程方式,也可以使用ForkJoinPool
                futures[i] = CompletableFuture.runAsync(() ->
                        IntStream.range(0, 100_000).forEach(j ->
                                count.incrementAndGet()), executor));
        // 执行完所有future结果之后执行thenAccept()
        CompletableFuture.allOf(futures).thenAccept((v) -> {
            System.out.printf("time: %d%n", System.currentTimeMillis() - start);
        });
    }

    /**
     * 比AtomicLong快2-4倍
     */
    @Test
    public void test2_1() {
        long start = System.currentTimeMillis();
        ExecutorService executor = Executors.newFixedThreadPool(1000);
        LongAdder counter = new LongAdder();

        CompletableFuture[] futures = new CompletableFuture[1000];
        IntStream.range(0, 1000).forEach( i ->
                futures[i] = CompletableFuture.runAsync( () ->
                    IntStream.range(0, 100_000).forEach(j -> counter.increment()), executor));
        CompletableFuture.allOf(futures).thenAccept(v -> System.out.printf("time: %d%n", System.currentTimeMillis() - start));
    }

    /**
     * 累加器
     */
    @Test
    public void test3() {
        LongAccumulator minAccumulator = new LongAccumulator(Long::min, 10000);
        LongAccumulator maxAccumulator = new LongAccumulator(Long::max, 0);
        IntStream.range(0, 100).forEach( i -> {
                new Thread( () ->
                        Stream.generate(Math::random)
                                .map( d -> Double.valueOf(d * 10000).intValue())
                                .limit(100)
                                .forEach(e -> {
                                    minAccumulator.accumulate(e);
                                    maxAccumulator.accumulate(e);
                                })
                ).start();
        });
        System.out.println("min=" + minAccumulator.intValue());
        System.out.println("max=" + maxAccumulator.intValue());
    }

    @Test
    public void test5() {
        File[] files = new File[]{
                new File(this.getClass().getResource("/alice.txt").getFile()),
                new File(this.getClass().getResource("/person.fxml").getFile())
        };
        ConcurrentHashMap<String, Set<File>> words = new ConcurrentHashMap<>();
        Arrays.stream(files).parallel().forEach(f -> {
            try {
                /**
                 * merge方法(key, 初始值, 如果已经存在key解决冲突).add(f)类似于
                 * if(map.get(k) == null) {
                 *     Set value = new HashSet<k>()
                 *     map.put(k, value)
                 * }
                 * value.add(v)
                 */
                Arrays.asList(new String(Files.readAllBytes(f.toPath()), StandardCharsets.UTF_8)
                        .split("[\\P{L}]+"))
                        .parallelStream()
                        .forEach(w -> words.merge(w, Sets.newHashSet(), (exists, newWord) -> exists).add(f));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        words.forEach((k, w) -> System.out.println(k + ":" + w));
    }

    @Test
    public void test6() {
        File[] files = new File[]{
                new File(this.getClass().getResource("/alice.txt").getFile()),
                new File(this.getClass().getResource("/person.fxml").getFile())
        };
        ConcurrentHashMap<String, Set<File>> words = new ConcurrentHashMap<>();
        Arrays.stream(files).parallel().forEach( f -> {
            try {
                Arrays.asList(new String(Files.readAllBytes(f.toPath()), StandardCharsets.UTF_8)
                        .split("[\\P{L}]+"))
                        .parallelStream()
                        // words.computeIfAbsent(key, Function<String,Set<File>>) 返回Set<File>
                        // 如果当对应的key不存在则执行的操作;computeIfPresent(key,(f,s) -> return f)
                        .forEach(w -> words.computeIfAbsent(w, k -> Sets.newHashSet()).add(f));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        final Set<File> aaa = words.computeIfPresent("aaa", (f, s) -> {
            System.out.println(f + "=" + s);return s;});
        System.out.println(aaa);
        words.forEach((k, w) -> System.out.println(k + "=" + w));
    }

    /**
     * reduce
     */
    @Test
    public void test7() {
        int cores = Runtime.getRuntime().availableProcessors();
        ConcurrentHashMap<String, Long> wordAccumulator = new ConcurrentHashMap<>();
        getWordsAsList().forEach( w -> wordAccumulator.computeIfAbsent(w, (k) -> (long) k.length()));
        // reduce化简,两两计算
        Map.Entry<String, Long> longestEntry = wordAccumulator.reduceEntries(
                cores, (e1, e2) -> e1.getValue() > e2.getValue() ? e1 : e2);
        System.out.println(longestEntry.getKey() + "=" + longestEntry.getValue());
    }

    /**
     *
     */
    @Test
    public void test8_0() {
        long start = System.currentTimeMillis();
        Stream<Integer> limit = Stream.generate(() -> ((int) (Math.random() * 100000))).limit(10_000_000);
        Integer[] array = limit.toArray(Integer[]::new);
        Arrays.sort(array);
        System.out.println(System.currentTimeMillis() - start);
    }

    @Test
    public void test8_1() {
        long start = System.currentTimeMillis();
        Stream<Integer> limit = Stream.generate(() -> ((int) (Math.random() * 100000))).limit(10_000_000);
        Integer[] array = limit.toArray(Integer[]::new);
        Arrays.parallelSort(array);
        System.out.println(System.currentTimeMillis() - start);
    }

    @Test
    public void test9() {
        int[] array = new int[] {1,2,3,4,5};
        Arrays.parallelPrefix(array, (x, y) -> x * y);
        Arrays.stream(array).forEach(System.out::println);
    }

    @Test
    public void test10() {
        CompletableFuture.supplyAsync(() -> readPage("http://www.horstmann.com/"))
                // 获取getLinks
                .thenApply(this::getLinks)
                // 处理结果
                .handle((l, e) -> {
                    if(e != null) {
                        System.out.println(e.getMessage());
                        return Lists.newArrayList();
                    } else {
                        return l;
                    }
                }).thenAccept(l -> l.forEach(System.out::println));
        // ForkJoinPool线程池等待
        ForkJoinPool.commonPool().awaitQuiescence(10, TimeUnit.SECONDS);
    }

    public String readPage(String urlString) {
        URL url;
        try {
            url = new URL(urlString);
            URLConnection conn = url.openConnection();
            StringBuilder content = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String inputLine;
                while ((inputLine = br.readLine()) != null) {
                    content.append(inputLine);
                }
            }
            return content.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getLinks(String content) {
        List<String> links = new ArrayList<>();
        Pattern p = Pattern.compile("(?i)href=\"http://(.*?)\"");
        Matcher m = p.matcher(content);
        while (m.find()) {
            links.add(m.group(1));
        }
        return links;
    }

    @Test
    public void test11() {
        repeat(() -> {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("login:");
                String user = br.readLine();
                System.out.print("password:");
                char[] password = br.readLine().toCharArray();
                return new PasswordAuthentication(user, password);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        },(a) -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            return new String(a.getPassword()).equals("secret");
        }).thenAccept((a) -> System.out.printf("Logged in %s %s %n", a.getUserName(), a.getPassword()));
        ForkJoinPool.commonPool().awaitQuiescence(10, TimeUnit.MINUTES);
    }

    public static <T> CompletableFuture<T> repeat(Supplier<T> action, Predicate<T> until) {
        return CompletableFuture.supplyAsync(action).thenApplyAsync(r -> {
            if(until.test(r)) {
                return r;
            } else {
                return repeat(action, until).join();
            }
        });
    }

}
