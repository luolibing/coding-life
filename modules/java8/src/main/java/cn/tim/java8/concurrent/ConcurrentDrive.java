package cn.tim.java8.concurrent;

import cn.tim.java8.lambda.Exercise;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.StampedLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by LuoLiBing on 16/7/14.
 */
public class ConcurrentDrive implements Exercise {

    /**
     * 原始的AtomicInteger.compareAndSet(x,y)会检查x的值和ato本身是否相等,相等则设置为y,不相等则不变返回false
     * 所以compareAndSet会因为竞态条件产生重试,类似于乐观锁,这样就不得不进行多次重试,写一个while语句
     * 而使用updateAndSet则不需要写while语句
     */
    @Test
    public void atomic() {
        AtomicInteger atomicInteger = new AtomicInteger(10);
        atomicInteger.incrementAndGet();
        final boolean b = atomicInteger.compareAndSet(2, 5);
        System.out.println(b);
        System.out.println(atomicInteger.get());

        AtomicInteger largest = new AtomicInteger();
        int observed = 10;
        int oldValue, newValue;
        do {
            oldValue = largest.get();
            newValue = Math.max(oldValue, observed);
        } while (!largest.compareAndSet(oldValue, newValue));
    }

    @Test
    public void updateAndGet() {
        AtomicInteger atomicInteger = new AtomicInteger(10);
        atomicInteger.updateAndGet(x -> 12);
        System.out.println(atomicInteger.get());

        atomicInteger.accumulateAndGet(10, Math::max);
    }

    @Test
    public void test1() {
        Instant start = Instant.now();
        AtomicInteger atomicInteger  = new AtomicInteger();
        IntStream.range(1, 10).forEach( index ->
                new Thread(() -> IntStream.range(0, 10000).forEach( i -> {
                    atomicInteger.incrementAndGet();
                    atomicInteger.updateAndGet((x) -> Math.max(x, i));
                })).start()
        );
        System.out.println(Duration.between(start, Instant.now()).getNano());
    }

    /**
     * 当有大量线程访问同一个原子值时,由于乐观锁会需要大量的重试,性能会严重下降,java8提供了LongAdder
     * LongAdder提供了多个Cell变量,Cell变量累加就为当前值.多个线程各自更新自己的Cell值,线程数量增加自动增加被加数
     * 直到所有工作完成之后才需要总和值,所以效率会很高
     *
     * LongAdder.increment()方法不会返回原始值,使用它只会抹杀掉将总和值拆分为多个被加数的性能提升
     *
     */
    @Test
    public void longAdder() {
        Instant start = Instant.now();
        final LongAdder longAdder = new LongAdder();
        ExecutorService pool = Executors.newCachedThreadPool();
        IntStream.range(0, 1000).forEach( x -> {
            pool.submit(() -> {
                int i = 0;
               while(i++ < 100000) {
                   longAdder.add(1);
               }
            });
        });
        System.out.println(longAdder.sum());
        System.out.println(Duration.between(start, Instant.now()).getNano());
    }

    @Test
    public void atomicLong() {
        Instant start = Instant.now();
        final AtomicInteger longAdder = new AtomicInteger();
        ExecutorService pool = Executors.newCachedThreadPool();
        IntStream.range(0, 1000).forEach( x -> {
            pool.submit(() -> {
                int i = 0;
                while(i++ < 100000) {
                    longAdder.incrementAndGet();
                }
            });
        });
        System.out.println(longAdder.get());
        System.out.println(Duration.between(start, Instant.now()).getNano());
    }

    /**
     * LongAccumulator将LongAdder的思想待到了任意的累加操作中.
     * LongAccumulator的内部包含多个变量Cell,每个变量都被初始化为中立元素,例如0.累加时会更新为 ai = ai op v
     */
    @Test
    public void accumulator() {
        // 0为中立元素,Long.sum(a,b)函数
        LongAccumulator adder = new LongAccumulator(Long::sum, 0);
        adder.accumulate(10);
        adder.accumulate(20);
        System.out.println(adder.get());
    }

    @Test
    public void stampedLock() {
        Vector vector = new Vector();
        new Thread(() -> {
            vector.put("luolibing");
        }).start();
        vector.get(0);
    }

    class Vector {
        private int size;
        private Object[] elements;
        private StampedLock lock = new StampedLock();

        public Vector() {
            this.size = 100;
            this.elements = new Object[size];
            this.elements[0] = "aaa";
            this.elements[1] = "aaa";
            this.elements[2] = "aaa";
        }

        public Object get(int n) {
            // 获取一个印戳.
            long stamp = lock.tryOptimisticRead();
            Object[] currentElements = elements;
            int currentSize = size;
            // 验证印戳是否有效
            if(!lock.validate(stamp)) { // 某个线程持有一个写锁
                // 如果无效,可以获取一个写锁,获取一个悲观锁
                stamp = lock.readLock();
                currentElements = elements;
                currentSize =  size;
                // 解锁
                lock.unlockRead(stamp);
            }
            return n < currentSize ? currentElements[n] : null;
        }

        public void put(Object object) {
            final long l = lock.readLock();
            System.out.println(object);
            try {
                Thread.sleep(1000 * 30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.unlock(l);
        }
    }

    /**
     * 哈希映射会将相同的哈希码的所有数据项保存在同一个块中
     * 糟糕的哈希函数会导致所有数据被保存在很小的一组块中,严重影响了哈希映射的效率
     * 攻击者可以通过构造一组大量哈希码都一样的字符串来拉低应用程序的速度.
     * Java8使用了树形结构来组织块,而不是用列表结构
     *
     */
    @Test
    public void concurrentHashMap() {
        ConcurrentHashMap<String, Integer> concurrentHashMap = new ConcurrentHashMap<>();
        Integer oldValue, newValue;
        String word = "word";
        do {
            oldValue = concurrentHashMap.get(word);
            newValue = oldValue == null ? 1: oldValue + 1;
        } while (!concurrentHashMap.replace(word, oldValue, newValue));
    }

    @Test
    public void test2() {
        ConcurrentHashMap<String, LongAdder> map = new ConcurrentHashMap<>();
        String word = "word";
        // 如果不存在则,put new LongAdder()
        map.putIfAbsent(word, new LongAdder());
        map.get(word).increment();
        // putIfAbsent会返回value本身
        map.putIfAbsent(word, new LongAdder()).increment();
        System.out.println(map.get(word).sum());

        // java8提供了更方便进行院子更新的方法
        ConcurrentHashMap<String, Integer> intMap = new ConcurrentHashMap<>();
        // HashMap.compute(key, BiFunction<K,V>)  ConcurrentHashMap本身不允许含有null值,该Map的许多方法用null值来表示映射中不含有的指定键
        // computeIfPresent和computeIfAbsent表示存在值和不存在值的时候进行的计算
        intMap.compute(word, (k, v) -> v == null ? 1 : (v + 1));
        intMap.compute(word, (k, v) -> v == null ? 1 : (v + 1));
        intMap.compute(word, (k, v) -> v == null ? 1 : (v + 1));

        map.computeIfAbsent(word, k -> new LongAdder()).increment();
        System.out.println(intMap.get(word));

        // merge方法,第一个参数key,第二个参数,当键不存在时的初始值,第三个参数表示存在时的处理,如果返回传递给merge方法的函数返回值为null,则会将一有的数据项从映射中删除掉.
        // merge或者compute函数不应该更新映射的其他部分
        map.merge(word, new LongAdder(), (oldValue, newValue) -> {oldValue.increment();return null;});
        System.out.println(map.get(word));
    }

    @Test
    public void batch() {
        int cores = Runtime.getRuntime().availableProcessors();
        // 分组
        ConcurrentMap<String, Long> map = getWordsAsList()
                .parallelStream()
                .collect(Collectors.groupingByConcurrent((s) -> s, Collectors.counting()));
        ConcurrentHashMap<String, Long> concurrentHashMap = (ConcurrentHashMap) map;
        // 搜索key,searchKeys(并行数),使用最大值Long.MAX_VALUE,并行数为1,其他值则表示使用最大核心数
        Set<Long> threads = new HashSet<>();
        String key = concurrentHashMap.searchKeys(cores,
            (s) -> {
                threads.add(Thread.currentThread().getId());
                // 直到该函数返回一个非null的结果,如果找到会立即返回结果
                return "cat1".equals(s) ? s : null;
            });
        System.out.println(key);

        // 多线程forEach
        concurrentHashMap.forEach(1, (k, v) -> System.out.println(k + "->" + v));
        // 多线程forEach 第二个参数为转换参数BiFunction,第三个参数为Consumer
        concurrentHashMap.forEach(1,
                (k,v) -> k + "->" + v,
                System.out::println);
        // 第二个可以搜索所有的
        concurrentHashMap.forEach(1,
                (k, v) -> v> 100 ? k + ":" + v : null, // 转换器
                System.out::println);

        // search(key,value) 搜索map,可以搜索key和value
        String search = concurrentHashMap.search(1, (k, v) -> v > 500 ? k : null);
        System.out.println(search);


        // *************************reduce方法*************************
        // reduce累加
        Long sum = concurrentHashMap.reduceValues(1, Long::sum);
        System.out.println(sum);

        Integer max = concurrentHashMap.reduceKeys(1,
                String::length, // 转换器Function
                Integer::max);
        System.out.println(max);

        // 线程数,转换器,默认值,原始累加器
        final long sum1 = concurrentHashMap.reduceValuesToLong(1,
                Long::longValue,
                0,
                Long::sum);
        System.out.println(sum1);
    }

    @Test
    public void set() {
        int cores = Runtime.getRuntime().availableProcessors();
        // 分组
        ConcurrentMap<String, Long> map = getWordsAsList()
                .parallelStream()
                .collect(Collectors.groupingByConcurrent((s) -> s, Collectors.counting()));
        // 返回一个大的,线程安全的Set,是对ConcurrentHashMap<K,Boolean>()的封装
        Set<String> keySet = ConcurrentHashMap.<String>newKeySet();
        ConcurrentHashMap<String, Long> concurrentHashMap = (ConcurrentHashMap<String, Long>) map;
        keySet.forEach(System.out::println);

        // 实例方法的keySet
        ConcurrentHashMap.KeySetView<String, Long> keySet1 = concurrentHashMap.keySet();
        keySet1.forEach(System.out::println);
    }

    /**
     * 数组并行排序操作
     */
    @Test
    public void parallelSort() {
        String[] words = getWordsAsArray();
        // parallelSort()排序方法
        Arrays.parallelSort(words);
        Arrays.stream(words).forEach(System.out::println);

        System.out.println(words.length);
        // 只排序一部分
        Arrays.parallelSort(words, 0, words.length/2);
        System.out.println(words.length);

        // parallelSetAll, IntFunction中的index为数组的index下标索引值
        Integer[] wordLength = Arrays.stream(words).map(String::length).toArray(Integer[]::new);
        Arrays.parallelSetAll(wordLength, i -> {System.out.println(i); return i;});
        Arrays.stream(wordLength).forEach(System.out::println);

        // 累计计算
        Arrays.parallelPrefix(wordLength, (x, y) -> x * y);
        Arrays.stream(wordLength).forEach(System.out::println);
    }

    /**
     * Future流水线
     */
    @Test
    public void futureReadPage() throws Exception {
        // 创建CompletableFuture的方式,1 supplyAsync   2 runAsync
        CompletableFuture.supplyAsync(this::getWordsAsList);
        CompletableFuture.runAsync(() -> System.out.println("aaa"));

        // 所有的Async结尾的方法都有两种形式,一种使用ForkJoinPool中运行,一种使用Executor执行,不配置Executor,默认是用ForkJoinPool
        CompletableFuture<Void> completableFuture = CompletableFuture.supplyAsync(this::getWordsAsList)
                .thenApply((s) -> s.subList(0, s.size() / 2))
                .thenAccept(System.out::println);
        System.out.println(completableFuture.isDone());
        // thenApply() T -> U

        // thenCompose Function T -> CompletableFuture<U>
        // CompletableFuture.supplyAsync(this::getWordsAsList).thenCompose()

        CompletableFuture.supplyAsync(this::getWordsAsList).thenAccept(System.out::println);

        // CompletableFuture.supplyAsync(BiConsumer<T,Throwable>)
        CompletableFuture.supplyAsync(this::getWordsAsList).whenComplete((l, e) -> e.printStackTrace());

        // 流水线
        CompletableFuture.supplyAsync(this::getWordsAsList)
                .thenRun(() -> System.out.println("hello"))
                .thenRun(() -> System.out.println("world"))
                .thenRun(() -> System.out.println("bye"));
    }

    @Test
    public void futureReadPageTest() throws Exception {
        CompletableFuture<Void> completableFuture = CompletableFuture
                .supplyAsync(() -> blockingReadPage("http://www.01tingshu.com/tuili/sanguoyanyi"))
                .thenApply(this::getLinks)
                .thenAccept(System.out::println);
        while (!completableFuture.isDone()) {
            Thread.sleep(1000);
        }
    }


    public String blockingReadPage(String url) {
        try {
            InputStream inputStream = new URL(url).openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "/n");
            }
            return sb.toString();
        } catch (Exception e) {
            return null;
        }
    }

    public List<String> getLinks(String content) {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();

        lock.unlock();
        Pattern pattern = Pattern
                .compile("(http://|ftp://|https://|www){0,1}[^\u4e00-\u9fa5\\s]*?\\.(com|net|cn|me|tw|fr)[^\u4e00-\u9fa5\\s]*");
        Matcher matcher = pattern.matcher(content);
        List<String> links = Lists.newArrayList();
        while (matcher.find()) {
            links.add(matcher.group());
        }
        return links;
    }

    @Test
    public void combine() throws ExecutionException, InterruptedException {
        // thenCombine 执行两个对象,将结果按照指定的函数组合起来 BiFunction(s, t)两个结果
        CompletableFuture<List<String>> completableFuture = CompletableFuture.supplyAsync(this::getWordsAsList)
                .thenCombine(CompletableFuture.supplyAsync(this::getWordsAsList), (s, t) -> {
                    // s t 是固定的List,不支持add remove操作
                    List<String> result = Lists.newArrayList();
                    System.out.println(s.getClass());
                    result.addAll(t);
                    result.addAll(s);
                    return result;
                });
        System.out.println(completableFuture.get().size());

        // thenAcceptBoth 执行两个对象,将两个结果组合在一起消费BiConsumer, BiConsumer<f,s>
        CompletableFuture.supplyAsync(this::getWordsAsList)
                .thenAcceptBothAsync(
                        CompletableFuture.supplyAsync(
                                this::getWordsAsArray),
                        (f, s) -> System.out.println(f + ":" + s));

        // runAfterBoth,运行完两个任务之后,执行runnable对象, Runnable线程
        CompletableFuture.supplyAsync(this::getWordsAsArray)
                .runAfterBoth(
                        CompletableFuture.supplyAsync(
                                this::getWordsAsList)  ,
                        () -> System.out.println("runnable!!!")
                );

        // 只要有一个结果可用时,将结果传递给指定的函数,Function
        CompletableFuture.supplyAsync(this::getWordsAsList)
                .applyToEither(
                        CompletableFuture.supplyAsync(this::getWordsAsList),
                        (l) -> l
                );

        // 只要有一个结果执行后,调用Runnable
        CompletableFuture.supplyAsync(this::getWordsAsList)
                .runAfterEither(
                        CompletableFuture.supplyAsync(this::getWordsAsList),
                        () -> System.out.println("aaa")
                );

        // 所有Future对象结束后返回void结果
        CompletableFuture.allOf(CompletableFuture.supplyAsync(this::getWordsAsList), CompletableFuture.supplyAsync(this::getWordsAsList));

        // 任意一个Future执行完返回void结果
        CompletableFuture.anyOf(CompletableFuture.supplyAsync(this::getWordsAsList), CompletableFuture.supplyAsync(this::getWordsAsList));


        /***
         * CompletableFuture<JsonResultModel> result = CompletableFuture.supplyAsync(
         () -> courseScheduleService.findByTeacherIdAndClassDateBetween(teacherId, getInternationalDateRange(date)))
         .thenCombine(CompletableFuture.supplyAsync(() -> {
             MonthTimeSlots m = serviceSDK.getMonthTimeSlotsByDateBetween(teacherId, dateRangeForm);
             try {
             return transfer(m, dateRangeForm, teacherId);
             } catch (CloneNotSupportedException e) {
             e.printStackTrace();
             return null;
             }
             }),
             (courseScheduleList, dayTimeSlotsList) -> {
             if (CollectionUtils.isEmpty(dayTimeSlotsList)) {
             try {
             return getInternationalDayTimeSlotsTemplate(teacherId, date);
             } catch (CloneNotSupportedException e) {
             e.printStackTrace();
             return null;
             }
         } else {
             return JsonResultModel.newJsonResultModel(
                 dayTimeSlotsList.parallelStream()
                 // 可选时间过滤,即北京时间周一到周五  周六到周日的时间规则
                 .map(timeLimitPolicy::limit)
                 // 判空
                 .filter(this::notEmptyPredicate)
                 // 国际化时间转换
                 .map(d -> this.filterInternationalDayTimeSlots(d, getInternationalDateTimeRange(date)))
                 .filter(this::notEmptyPredicate)
                 // 3 覆盖,课程信息覆盖
                 .peek(d -> d.override(courseScheduleList, serviceSDK))
                 // 历史日期时间片过滤,只显示有课的
                 .map(dayTimeSLots -> dayTimeSLots.filter(
                 d -> LocalDate.now().isAfter(parseLocalDate(d.getDay())),
                 t -> t.getCourseScheduleStatus() != FishCardStatusEnum.UNKNOWN.getCode()))
                 // 非空
                 .filter(d -> d != null)
                 // 排序
                 //.sorted((f, s) -> Long.compare(f.getDayStamp(), s.getDayStamp()))
                 .collect(Collectors.toList()));
             }
         });
         */
    }
}
