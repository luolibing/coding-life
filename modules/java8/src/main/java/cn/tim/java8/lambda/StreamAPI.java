package cn.tim.java8.lambda;

import org.junit.Test;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

/**
 * Created by LuoLiBing on 16/6/29.
 * 对比使用普通迭代方式与Streams聚合方式
 * 传统方式:
 * 1 for-loop增强, 有序, 从左至右
 * 2 大部分是固定的
 *
 * Stream方式:
 * 1 以任何顺序执行, 或者并行执行
 * 2 以聚合方式考虑, 而非单独操作. 隐含操作所有元素, 而非单独操作
 * 3 更高层次的抽象
 * 4 每个操作都是独立的, 更少的偶然复杂度
 *
 * 并行Streams
 * 有用的知识: 在index索引上使用Streams, 而不是所有元素
 * 并行是使用更多的资源来更快的计算出结果, 以空间获取时间. 所以并不是并行就一定比顺序访问更快.
 *
 * 并行计算需要更多的Worker来计算结果, 同样会引入更多的开销与问题:
 * 1 分解问题
 * 2 发送任务,管理任务,等待任务完成
 * 3 合并结果
 *
 * 性能考虑
 * 1 splitting / decomposition 开销
 * 2 task分发/管理开销
 * 3 result合并开销
 * 4 需要大量的数据才能让拆分变得有意义
 *
 * NQ模型
 * N = 数据总条数
 * Q = 每个worker操作的条数
 * NQ>10000的时候才有可能提升并行速度
 * 大部分的Stream都有一个很低的Q
 *
 * 下列情况不适合使用并行
 * 1 不够高的NQ
 * 2 Cache-miss比太高
 * 3 资源需要昂贵的代价进行拆分
 * 4 结果合并代价太高
 * 5 管道需要严格的顺序依赖
 *
 *
 */
public class StreamAPI {

    @Test
    public void readFile() throws IOException {
        long begin = System.currentTimeMillis();
        String contents = new String(Files.readAllBytes(Paths.get("/Users/tim/Downloads/jquery.min.js")), StandardCharsets.UTF_8);
        List<String> words = Arrays.asList(contents.split("[\\P{L}]+"));
        int count = 0;
        for (String w : words) {
            if(w.length() > 1)
                count ++;
        }
        System.out.println("count=" + count);
        System.out.println("time=" + (System.currentTimeMillis() - begin));
    }


    private static List<String> getWords() throws IOException {
        String contents = new String(Files.readAllBytes(Paths.get("/Users/tim/Downloads/jquery.min.js")), StandardCharsets.UTF_8);
        return Arrays.asList(contents.split("[\\P{L}]+"));
    }

    private static String getContent() throws IOException {
        return new String(Files.readAllBytes(Paths.get("/Users/tim/Downloads/jquery.min.js")), StandardCharsets.UTF_8);
    }

    /**
     * 一 stream特点
     * stream不会存储元素
     * stream不会改变源对象
     * stream操作符可能是延迟执行的,会等到需要结果的时候才执行
     * stream不会按照元素的调用顺序执行,只有在count被调用的时候才会执行stream操作.当count方法需要第一个元素时,filter方法会开始请求各个元素,直到找到一个符合条件的元素
     *
     *
     * 二 stream创建
     * 创建一个stream
     * 指定将初始stream转换为另一个stream的中间操作
     * 试用一个终止操作来产生一个结果,终止之后stream就不会再被试用了.
     * @throws IOException
     */
    @Test
    public void streamReadFile() throws IOException {
        long begin = System.currentTimeMillis();
        String contents = new String(Files.readAllBytes(Paths.get("/Users/tim/Downloads/jquery.min.js")), StandardCharsets.UTF_8);
        List<String> words = Arrays.asList(contents.split("[\\P{L}]+"));
        long count = words.parallelStream().filter(w -> w.length() > 1).count();
        System.out.println("count=" + count);
        System.out.println("time=" + (System.currentTimeMillis() - begin));
    }

    @Test
    public void arrayStream() {
        // 将数组转换为stream
        Stream<String> words = Stream.of("aaa", "bbb", "ccc");
        // 将一个数组的一部分转换为stream
        Stream<String> words1 = Arrays.stream(new String[] {"aaa", "bbb", "ccc"}, 0, 1);
        // 创建空的Stream
        Stream<String> empty = Stream.empty();
        // 创建无限Stream的静态方法 generate(Supplier<T>)
        Stream<String> generate = Stream.generate(() -> "Echo");
        Optional<String> generateFirst = generate.findFirst();
        System.out.println(generateFirst.get());

        // 随机数生成器
        Stream<Double> randomStream = Stream.generate(Math::random);
        Optional<Double> doubleOptional = randomStream.findAny();
        System.out.println(Math.round(doubleOptional.get() * 10));

        // 产生无限序列stream
        Stream<BigInteger> iterateStream = Stream.iterate(BigInteger.ZERO, n -> n.add(BigInteger.ONE)).limit(100);
        BigInteger[] bigIntegers = iterateStream.toArray(BigInteger[]::new);
        for(BigInteger bigInteger : bigIntegers) {
            System.out.println(bigInteger.intValue());
        }

        // 正则表达式对CharSequence对象分割
        Stream<String> stringStream = Pattern.compile("[\\]P{L}]+").splitAsStream("aaf54354 fafd a534 fdfda");
        stringStream.forEach(System.out::println);

        // 读取文件
        try(Stream<String> lines = Files.lines(Paths.get("/share/rms_resource.log"))) {
            lines.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * filter过滤 Predicate<T>对象,从T到boolean的函数
     * @throws IOException
     */
    @Test
    public void filter() throws IOException {
        Stream<String> words = getWords().stream();
        words.filter( w -> w.length() > 2).forEach(System.out::println);
    }

    /**
     * map对stream进行转换,从一个string转换为目标对象, Function<T,B>
     * 将流中每个元素都应用一个函数,并将返回的值手机到一个新的流中
     * @throws IOException
     */
    @Test
    public void map() throws IOException {
        getWords().stream().map(String::toUpperCase).forEach(System.out::println);
        getWords().stream().map( s -> s.charAt(0)).forEach(System.out::println);
    }

    public static Stream<Character> characterStream(String s) {
        List<Character> result = new ArrayList<>();
        for(char c : s.toCharArray()) {
            result.add(c);
        }
        return result.stream();
    }

    /**
     * flatMap的用法
     * @throws IOException
     */
    @Test
    public void flatMap() throws IOException {
        // 返回的是一个stream中的stream
        Stream<Stream<Character>> result = getWords().stream().map(StreamAPI::characterStream);

        // java8中的flatMap与groovy的flatMap有点类似
        Stream<Character> characterStream = getWords().stream().flatMap(StreamAPI::characterStream);
        characterStream.forEach(System.out::println);
    }

    /**
     * 截取子流limit用法
     */
    @Test
    public void limit() {
        // limit截取子流limit方法
        Stream<Double> randoms = Stream.generate(Math::random).limit(100);
        randoms.forEach(System.out::println);
    }

    /**
     * 跳过元素skip用法
     */
    @Test
    public void skip() throws IOException {
        Stream.of(getContent().split("[\\P{L}]+")).skip(1).forEach(System.out::println);
    }

    /**
     * 合并两个流,当然第一个流不能是无限的,否则永远没有机会被添加到第一个流后面
     */
    @Test
    public void concat() {
        Stream<Character> combined = Stream.concat(
                characterStream("hello"), characterStream("world")
        );
        combined.forEach(System.out::println);
    }

    /**
     * peek方法
     */
    @Test
    public void peek() {
        // iterate 产生无限stream的静态方法,
        // peek会昌盛另一个与原始流具有相同元素的流,每次获取一个元素都会调用一个函数
        // limit截取子流
        // 不调用终止操作就不会执行中间的过程,且其中的顺序,并不是按照先后执行顺序,例如下面的代码不调用.toArray(),上面的代码就不会执行
        Object[] result = Stream.iterate(1.0, p -> p * 2)
                .peek(e -> System.out.println("fetching " + e))
                .limit(20)
                .toArray();
        Stream.of(result).forEach(System.out::println);
        List<Person> persons = new ArrayList<>();
        persons.add(new Person(1, "luolibing"));
        persons.add(new Person(2, "liuxiaoling"));
        persons.add(new Person(3, "luominghao"));
        persons.stream().peek( p -> p.name = p.name + "11");
        persons.forEach(System.out::println);
    }

    class Person {
        private int id;
        private String name;
        public Person(int id, String name) {
            this.id = id;
            this.name = name;
        }
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    /**
     * 有状态的转换,distinct会根据原始流中的元素返回一个具有相同顺序\抑制了重复元素的新流,必须记住之前已读取的元素
     * 显然无法对一个无线流进行排序
     */
    @Test
    public void distinct() {
        Stream.of("merrily", "duck", "merrily", "john", "goose", "duck", "duck").distinct().forEach(System.out::println);
    }

    /**
     * 集合排序,使用sorted方法,使用Comparator.comparing()方法,reversed对流进行翻转
     * Collections.sort方法会对原有的集合进行排序,而Stream.sorted方法会返回一个新的已排序的流
     * @throws IOException
     */
    @Test
    public void compare() throws IOException {
        Stream<String> sorted = getWords().stream().sorted(Comparator.comparing(String::length).reversed());
        System.out.println(sorted.findFirst().get());
    }

    /**
     * 聚合方法都是终止操作,当一个流应用了终止操作后,它就不能再应用其他的操作了.
     * max会返回一个Optional对象,Optional有可能封装值,也有可能封装了一个null,但是本身不可能为null,在以前的java,这种情况返回一个null
     * 如果在一个没有校验null值的时候,这种情况会抛出空指针异常
     */
    @Test
    public void max() throws IOException {
        Optional<String> max = getWords().stream().max(String::compareToIgnoreCase);
        if(max.isPresent()) {
            System.out.println("largest: " + max.get());
        }
    }

    /**
     * 获取第一个
     * @throws IOException
     */
    @Test
    public void findFirst() throws IOException {
        final Optional<String> good = getWords().stream().filter(s -> s.startsWith("g")).findFirst();
        if(good.isPresent()) {
            System.out.println(good.get());
        }
    }

    /**
     * 找出任意片段中的第一个匹配元素,对于并行执行时十分有效,只需要找到一个就会结束整个计算
     * @throws IOException
     */
    @Test
    public void findAny() throws IOException {
        // 并行流 parallel()
        final Optional<String> q = getWords().stream().parallel().filter(s -> s.startsWith("j")).findAny();
        if(q.isPresent()) {
            System.out.println(q.get());
        }
    }

    /**
     * 判断是否含有某个元素,或者能够符合条件的元素
     * anyMatch,接收一个predicate参数
     * allMatch,所有通过
     * noneMatch,没有元素匹配
     */
    @Test
    public void anyMatch() throws IOException {
        final boolean we = getWords().parallelStream().anyMatch(s -> s.startsWith("we"));
        System.out.println(we);
    }

    /**
     * Optional<T>对象或者是对一个T类型对象的封装,或者表示不是任何对象,它比一般指向T类型的引用更安全,因为它不会返回null
     * 但是也得正常时会用,如果不存在封装对象,那么调用get方法会抛出一个NoSuchElementException异常
     */
    @Test
    public void optional() {
        // 下面的用法与直接用T操作没什么两样
        Optional<String> optional = Optional.empty();
        optional.get().toLowerCase();

        //
        String str = null;
        str.toLowerCase();
    }

    /**
     * 高效实用optional的方式
     * 使用一个或者接受正确值,或者返回另一个替代值的方法
     */
    @Test
    public void optionalGood() {
        Optional<Object> empty = Optional.of("aabbcc");
        // 如果存在一个值,则传递给函数里面执行
        empty.ifPresent(System.out::println);

        List result = new ArrayList<>();
        empty.ifPresent(result::add);
        System.out.println(result);

        // 对stream进行转换
        Optional<Boolean> add = empty.map(result::add);
        add.ifPresent(System.out::println);

        // 当不存在一个值时,使用默认的值
        Object defaultStr = empty.orElse("");
        System.out.println(defaultStr);

        // 还可以调用代码来产生默认的值
        Optional<String> stringOptional = Optional.empty();
        String dir = stringOptional.orElseGet(() -> System.getProperty("user.dir"));
        System.out.println(dir);

        final Properties properties = System.getProperties();
        for(Map.Entry entry : properties.entrySet()) {
            System.out.println(entry.getKey() + "=" + entry.getValue());
        }

        // 不存在还可以抛出一个异常
        stringOptional.orElseThrow(NoSuchElementException::new);
    }

    /**
     * 创建可选值
     */
    @Test
    public void optionalChoice() {
        // 静态方法创建Optional
        Optional.of("luolibing");
        Optional.empty();

        String name = null;
        // ofNullable被设计为null值和可选值之间的桥梁,当obj不为null时,返回Optional.of(obj),否则返回Optional.empty()
        Optional<String> nameOptional = Optional.ofNullable(name);
        System.out.println(nameOptional.isPresent());
    }

    public static Optional<Double> squareRoot(Double x) {
        return x < 0 ? Optional.empty() : Optional.of(x);
    }

    public static Optional<Double> inverse(Double x) {
        return x < 0 ? Optional.empty() : Optional.of(x);
    }

    /**
     * flatMap组合可选值函数,形成一个流水线式调用,只有每个调用都成功才返回成功
     * s.f()返回的是一个Optional<T>而不是T,只能通过flatMap来调用
     * s.f().flatMap(T::g) 如果s.f()存在才会继续调用flatMap,否则返回一个空的Optional
     */
    @Test
    public void flatMapOptional() {
        Optional<Double> integerOptional = Optional.of(10d);
        // 可以这样安全的链式调用这个流水线,
        Optional<Double> doubleOptional = integerOptional.flatMap(StreamAPI::squareRoot).flatMap(StreamAPI::inverse);
        doubleOptional.ifPresent(System.out::println);
    }


    /**
     * 对元素求和reduce联合操作,联合操作与组合顺序无关
     * v0 op v1 op v2 op ...
     * 减法是一个特例
     * 联合操作都有一个起点,一般情况下起点为 e op x = x,例如0 + 1 = 1,即起点等于0
     */
    @Test
    public void sum1() {
        Stream<BigInteger> values = Stream.iterate( BigInteger.ZERO, (x) -> x.add(BigInteger.ONE)).limit(100);
        Optional<BigInteger> sum = values.reduce(BigInteger::add);
        sum.ifPresent(System.out::println);
    }

    /**
     * 累加器形式的联合操作
     * @throws IOException
     */
    @Test
    public void sum2() throws IOException {
        // 累加器形式
        Integer sum = getWords().parallelStream().reduce(
                // 0为联合操作的起点
                0,
                // 累加器,因为word为字符串,需要计算长度之后再累加
                (total, word) -> total + word.length(),
                // (x, y) -> Integer.sum(x, y)
                // 求和  x + y
                Integer::sum);
        System.out.println("sum=" + sum);

        // 映射数字流
        sum = getWords().parallelStream().mapToInt(String::length).sum();
        System.out.println("sum=" + sum);
    }


    /**
     *
     * @throws IOException
     */
    @Test
    public void toArray() throws IOException {
        //final ZonedDateTime zonedDateTIme = ZonedDateTime.of(localDatetTime, ZoneId.of("Meiguo/fddf")).withZoneSameInstant(ZoneId.of("Beijing"));
        // 可以生成对应的数组
        String[] words = getWords().stream().toArray(String[]::new);
        System.out.println(words);

        // 或者是生成对应的迭代器
        Iterator<String> iterator = getWords().stream().iterator();

        // 收集结果到hashSet当中,如果这个过程是并行的,并不能直接将元素放到一个单独的hashSet当中,因为hashSet本身不是线程安全的
        // 并行收集的每一段都需要从所属的空hashSet开始,聚合函数只能允许你提供一个标示值
        // 需要给collect三个参数
        // 1 创建目标类型实例的方法,HashSet::new
        // 2 将元素添加到目标中的方法,HashSet::add
        // 3 将两个集合对象整合到一起的方法,例如HashSet::addAll
        HashSet<String> result = getWords().stream().collect(
                HashSet<String>::new, HashSet::add, HashSet::addAll);
        System.out.println("result=" + result);

        // collect也可以整合StringBuilder
        StringBuilder builder = getWords().stream().collect(StringBuilder::new, StringBuilder::append, StringBuilder::append);
        System.out.println("words= " + builder.toString());

        // Collectors帮我们将这个过程进行了封装 ArrayList::new, List::add, List::addAll
        List<String> resultList = getWords().stream().collect(Collectors.toList());
        System.out.println("resultList=" + resultList);

        Set<String> resultSet = getWords().stream().collect(Collectors.toSet());
        System.out.println("resultSet=" + resultSet);

        // 如果想对集合的类型进行控制,可以使用如下格式Collectors.toCollection(ArrayList<Integer>::new)
        ArrayList<String> collect = getWords().stream().collect(Collectors.toCollection(ArrayList<String>::new));
        System.out.println("resultSet=" + collect);
    }


    @Test
    public void stringCollect() throws IOException {
        // 字符串拼接
        String joinString1 = getWords().stream().collect(Collectors.joining("-"));
        System.out.println("joinString1=" + joinString1);

        // 如果是对象不是String对象,还需要先进行转换map().然后再进行collect
        String joinString2 = getWords().stream().map(String::toString).collect(Collectors.joining(", "));
        System.out.println(joinString2);
    }

    /**
     * 如果想讲流的结果聚合为一个综合,平均值,最大值或者最小值,那么可以使用summarizing()方法,这会产生一系列的汇总值
     * @throws IOException
     */
    @Test
    public void summarizing() throws IOException {
        IntSummaryStatistics summary = getWords().stream().collect(
                Collectors.summarizingInt(String::length));
        System.out.println("min=" + summary.getMin());
        System.out.println("max=" + summary.getMax());
        System.out.println("avg=" + summary.getAverage());
    }

    /**
     * 有序遍历
     */
    @Test
    public void forEachSorted() throws IOException {
        getWords().stream().forEachOrdered(System.out::println);
    }

    /**
     * 无序遍历,可以并行执行
     * forEach和forEachSorted都是终止操作.调用他们之后都不能再使用这个流,如果希望继续使用,可以使用peek
     * @throws IOException
     */
    @Test
    public void forEach() throws IOException {
        getWords().stream().peek(System.out::println).forEach(System.out::println);
    }

    /**
     * toMap(),将集合收集为一个Map,使用Collectors.toMap,帮我们封装了toMap的过程即
     * toMap(keyMapper,valueMapper, throwingMerger,HashMap::new)
     * 如果拥有多个相同的键,那么收集方法会抛出一个IllegalStateException
     */
    @Test
    public void toMap() {
        // 1 toMap当有同一个键时抛出IllegalStateException异常
        Stream<Person> personStream = Stream.of(
                new Person(1, "luolibing"), new Person(2, "liuxiaoling"),
                new Person(3, "luominghao"), new Person(4, "luoaiyun"));
        Map<Integer, Person> collect = personStream.collect(
                Collectors.toMap(Person::getId, Function.identity())
        );
        collect.forEach((key,value) -> System.out.println("key=" + value));

        // 2 第三个参数,解决冲突,当出现一样的key时,返回旧值
        Stream<Locale> locales = Stream.of(Locale.getAvailableLocales());
        Map<String, String> localeMap = locales.collect(
                Collectors.toMap(
                        Locale::getDisplayLanguage,
                        locale -> locale.getDisplayLanguage(locale),
                        (oldValue, newValue) -> oldValue
                )
        );
        localeMap.forEach((key, value) -> System.out.println(key + "=" + value));

        // 3 分组组合Map<String,Set<String>>
        locales.collect(
                Collectors.toMap(
                        Locale::getDisplayLanguage,
                        // 获取value,是一个Set<T>集合
                        l -> Collections.singleton(l.getDisplayLanguage()),
                        // a和b都是集合,进行合并
                        (a, b) -> {
                            Set<String> r = new HashSet<>(a);
                            r.addAll(b);
                            return r;
                        }
                )
        );

        // 4 如果我们希望得到一个TreeMap,我们可以加第4个参数进行
        personStream.collect(
              Collectors.toMap(
                      Person::getId,
                      Function.identity(),
                      (existingValue, newValue) -> {throw new IllegalStateException();},
                      TreeMap::new
              )
        );

        // 5 toConcurrentMap()方法,用来产生一个并发的map.在并行手机中应只使用一个并发的map,一个共享的map要比合并map效率更高,但是无法得到有序的结果
        ConcurrentMap<Integer, Person> collect1 = personStream.collect(
                Collectors.toConcurrentMap(
                        Person::getId,
                        Function.identity(),
                        (existingValue, newValue) -> existingValue
                )
        );
    }

    class Persons {
        Integer id;
        String name;

        public Persons(Integer id, String name) {
            this.id = id;
            this.name = name;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }


    /***************  grouping by 分组和分片 *********************/

    /**
     * 对具有相同特性的值进行分组是一个很常见的任务,使用groupingBy对locales使用国家进行分组
     */
    @Test
    public void groupingBy1() {
        Stream<Locale> locales = Stream.of(Locale.getAvailableLocales());
        // 使用groupingBy函数分组
        Map<String, Set<Locale>> countryToLocales
                // 如果想使用一个Set来接收而不是List,可以使用Collectors.toSet()
                = locales.collect(Collectors.groupingBy(Locale::getDisplayCountry, Collectors.toSet()));
        countryToLocales.forEach((country, localeList) -> System.out.println(country + "=" + localeList));

        locales = Stream.of(Locale.getAvailableLocales());
        // 使用partitioningBy()进行分区,分片,效率会比使用groupingBy更高,因为只是区分了两组列表,是或者不是
        Map<Boolean, List<Locale>> partionLocales = locales.collect(Collectors.partitioningBy(l -> l.getLanguage().equals("en")));
        List<Locale> english = partionLocales.get(true);
        System.out.println("english=" + english);
    }

    /**
     * 如果我们想对map值列表进行处理,我们需要提供一个downstream收集器
     * groupingBy(分组条件,聚合操作)
     * 分组有一个分组条件,例如以名字分组,颜色分组
     * 还有一个聚合操作,例如求和,求平均,求最大值等等
     */
    @Test
    public void downstream() {

        City[] citys = {new CityImpl("美洲", 100000), new CityImpl("亚洲", 200000), new CityImpl("非洲", 5000)};
        // 分组统计个数
        Locale[] locales = Locale.getAvailableLocales();
        Map<String, Long> counts = Stream.of(locales).collect(
                Collectors.groupingBy(Locale::getCountry, Collectors.counting()));
        counts.forEach((name, count) -> System.out.println(name + "=" + count));

        // 对分组的数据进行求和
        Stream<City> cityStream = Stream.of(citys);
        Map<String, Integer> cityPopulation = cityStream.collect(
                Collectors.groupingBy(City::getState, Collectors.summingInt(City::getPopulation))
        );
        cityPopulation.forEach((state,polution) -> System.out.println(state + "=" + polution));

        // 对分组求出最大值
        Stream<City> maxByCityStream = Stream.of(citys);
        Map<String, Optional<City>> maxPopulation = maxByCityStream.collect(
                // Collectors.maxBy(Comparator.comparing(City::getPopulation))
                Collectors.groupingBy(City::getState, Collectors.maxBy(Comparator.comparing(City::getPopulation)))
        );
        maxPopulation.forEach((name, max) -> System.out.println(name + "=" + max.orElseGet(NoneCity::new).getPopulation()));

        // mapping方法会将一个函数应用到downstream结果上,并且需要另一个收集器来处理结果.
        Stream<City> longestCityNameStream = Stream.of(citys);
        Map<String, Optional<String>> longestCityName = longestCityNameStream.collect(
                // mapping将downstream结果进行处理转换,例如如下对downstream分组,求名字最大长度的城市名
                Collectors.groupingBy(City::getState, Collectors.mapping(City::getState, Collectors.maxBy(Comparator.comparing(String::length))))
        );
        longestCityName.forEach((name, cityName) -> System.out.println(name + "=" + cityName.orElse("")));

        Stream<Locale> availableLocales = Stream.of(Locale.getAvailableLocales());
        // 返回的结果是Map<String, Set<String>>
        availableLocales.collect(
                groupingBy(Locale::getDisplayCountry, mapping(Locale::getDisplayLanguage, toSet()))
        ).forEach((name, set) -> System.out.println(name + "=" + set));

        // grouping求统计信息 summarizingInt
        Map<String, IntSummaryStatistics> cityStatistics = Stream.of(citys).collect(groupingBy(City::getState, summarizingInt(City::getPopulation)));
        cityStatistics.forEach((name, statistics) -> {
            System.out.println("max." + name + "=" + statistics.getMax());
            System.out.println("min." + name + "=" + statistics.getMin());
            System.out.println("avg." + name + "=" + statistics.getAverage());
        });

        // reducing()会对downstream进行一次普通的聚合操作,第一个参数为identity,为当值为空时返回identity
        Map<String, String> reducingCitys = Stream.of(citys).collect(
                groupingBy(City::getState, reducing("", City::getState, (s, t) -> s.length() == 0 ? t : s + ", " + t))
        );
        reducingCitys.forEach((name, title) -> System.out.println(name + "=" + title));

        // 与reducing类似,使用下面这种方式更自然一些
        Stream.of(citys).collect(
                groupingBy(City::getState, mapping(City::getState, joining(", ")))
        ).forEach((name, str) -> System.out.println(name + "=" + str));

        // 将对象流通过mapToInt(),mapToLong或者mapToDouble方法转换为一个原始类型流
        IntStream intStream = Stream.of(citys).mapToInt(City::getPopulation);
        intStream.forEach(System.out::println);
    }

    class CityImpl implements City {
        String state;
        Integer population;

        public CityImpl(String state, int population) {
            this.state = state;
            this.population = population;
        }

        public String getState() {
            return state;
        }

        public Integer getPopulation() {
            return population;
        }
    }

    interface City {
        String getState();

        Integer getPopulation();
    }

    class NoneCity implements City {
        String state = "未知";
        Integer population = 0;

        public String getState() {
            return state;
        }

        public Integer getPopulation() {
            return population;
        }
    }

    /**
     * 不需要都将原始类型封装成对应的对象类型
     * IntStream,LongStream,DoubleStream;
     * short char byte boolean类型的值,可以使用IntStream
     * 如果需要float,可以使用DoubleStream
     */
    @Test
    public void originalStream() {
        IntStream intStream = IntStream.of(1, 2, 4, 5, 1);
        IntStream stream = Arrays.stream(new int[]{1, 5, 1, 2}, 0, 2);
        // 使用IntStream静态方法IntStream.range或者IntStream.rangeClosed
        IntStream range = IntStream.range(0, 100);
        // 包括上限
        IntStream intStream1 = IntStream.rangeClosed(0, 100);
        String sentence = "\uD835\uDD46 is the set of octonions.";
        // 用于将两个UTF-16代码单元转换为一个代码点
        IntStream codes = sentence.codePoints();
        codes.forEach(System.out::println);

        // 将原始类型流转换为一个对象流,例如将IntStream转换为Stream<Integer>可以使用boxed()方法
        final Stream<Integer> boxed = IntStream.range(0, 100).boxed();
        // 对象流返回的是一个Object[]数组,如果需要转换为指定类型的数组,需要toArray(Integer[]::new)
        final Integer[] integers = boxed.toArray(Integer[]::new);

        // 将原始类型流转换为一个数组toArray()方法,返回一个原始类型的数组
        final int[] ints = IntStream.range(0, 100).toArray();

        // 同样Optional也拥有原始类型例如 OptionalInt,OptionalLong,OptionalDouble类型,他们的get方法可以使用getAsLong这种方法
        OptionalInt.of(1).getAsInt(); OptionalDouble.of(1d).getAsDouble(); OptionalLong.of(1L).getAsLong();

        // 随机数生成器
        final Stream<Random> randomStream = Stream.generate(Random::new);
        randomStream.map(Random::nextInt).limit(100).forEach(System.out::println);
    }

    /**
     * 默认情况下,流操作会创建一个串行流
     */
    @Test
    public void parallelStream() throws IOException {
        // Collection.parallelStream()创建出并行流
        getWords().parallelStream();
        // 可以将一个串行流转换为一个并行流parallel()方法
        Stream.of("a", "b", "c", "d").parallel();
        // 并行运行流操作,应该返回与串行运行时相同的结果,这些操作都应该是无状态的,可以使用AtomicInteger作为对象数组为计数器
        AtomicInteger[] shortWords = Stream.generate(AtomicInteger::new).limit(12).toArray(AtomicInteger[]::new);
        getWords().parallelStream().forEach( s -> {
            // 竞争条件,共享了同一数组
                if(s.length() < 12) shortWords[s.length()].incrementAndGet() ;
            });
        System.out.println(shortWords.length);
        System.out.println(Arrays.toString(shortWords));

        // 默认情况下,从有序集合数组或列表\范围值\生成器及迭代器,或者调用Stream.sorted所产生的流,都是有序的,只要有序将同一个操作运行两次,会得到一模一样的结果
        getWords().stream().sorted();

        // getWords().parallelStream().map()时,流会被分片为n段,每一段都会被并发处理,然后再按顺序将结果组合起来.
        final Stream<Integer> integerStream = getWords().parallelStream().map(String::length);

        // 不考虑有序时,一些操作可以更有效地并行运行,调用Stream.unordered()方法可以不关心顺序
        getWords().parallelStream().unordered().distinct();

        // 合并map的开销很大,使用Collectors.groupingByConcurrent()使用了一个共享的并发map,ConcurrentHashMap
        final ConcurrentMap<String, List<String>> result = getWords().parallelStream().collect(
                Collectors.groupingByConcurrent(w -> w)
        );
        result.forEach((name, list) -> System.out.println(name + "=" + list));

        // 流操作并不会修改底层的集合,JDK称这种方式为不干扰noninterference,以下的代码是正确的
        // 因为延迟操作,所以words还可以进行add操作
        List<String> words = getWords();
        System.out.println("size1=" + words.size());
        Stream<String> stream = words.stream();
        words.add("luolibing");
        System.out.println("size2=" + stream.count());

        // 干扰了底层的集合
        List<String> words1 = getWords();
        words1.stream().forEach(s -> {if(shortWords.length < 12) {words1.remove(s);}});
    }


    /**
     * 函数式接口
     */
    @Test
    public void function() throws IOException {
        // filter中使用的是函数式接口Predicate<? super T> boolean test(T arg)
        AtomicInteger atomicInteger = new AtomicInteger(0);
        Stream<String> longWords = getWords().stream().filter(s -> {if(s.length()>3) {
            atomicInteger.incrementAndGet();
        }return s.length() > 3;}).limit(10);
        longWords.forEach(System.out::println);
        System.out.println(atomicInteger.get());
    }

    @Test
    public void parallelStream1() {
        final int[] array = IntStream.range(0, 100).parallel().map(i -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return i + 1;}).toArray();
        System.out.println(array);
    }

    @Test
    public void streamList() {
        IntStream.range(0, 100).parallel().forEach(System.out::println);
    }

}
