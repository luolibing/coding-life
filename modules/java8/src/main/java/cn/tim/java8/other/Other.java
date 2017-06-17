package cn.tim.java8.other;

import cn.tim.java8.lambda.Exercise;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Repeatable;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by LuoLiBing on 16/7/21.
 */
public class Other implements Exercise {

    @Test
    public void string() {
        final String join = String.join(",", "luo", "li", "bing");
        System.out.println(join);
        final String zoneIds = String.join(", ", ZoneId.getAvailableZoneIds());
        System.out.println(zoneIds);
    }

    /**
     * 基本数字类型添加了BYTES常量
     */
    @Test
    public void number() {
        // 给基本类型的封装类型添加了静态方法hashCode,这样就不需要经理装箱 拆箱的过程
        System.out.println(Integer.hashCode(1));
        System.out.println(new Integer(1).hashCode());
        System.out.println(Byte.BYTES);
        System.out.println(Character.BYTES);
        System.out.println(Short.BYTES);
        System.out.println(Integer.BYTES);
        System.out.println(Float.BYTES);
        System.out.println(Long.BYTES);
        System.out.println(Double.BYTES);

        // Short Integer Long Float Double都提供了sum max min方法
        System.out.println(Integer.sum(1, 2));
        System.out.println(Integer.max(2, 3));

        // &&
        System.out.println(Boolean.logicalAnd(true, false));
        // ||
        System.out.println(Boolean.logicalOr(true, false));
        // ^ xor
        System.out.println(Boolean.logicalXor(true, false));

        // Byte.toUnsignedInt()获取无符号值的, 负数会获取到
        System.out.println(Byte.toUnsignedInt((byte)(-20)));

        // 新增了静态方法isFinite(),如果x不是正无穷大\小或者NaN非数字 Double.isFinite(111d)
        System.out.println(Double.isFinite(111d));

        System.out.println(Double.isNaN(1d) && Double.isInfinite(2d));

        // BigInteger添加了实例方法,byte|int|long|short ValueExact返回byte,当值不在范围之内,则抛出ArithmeticException异常
        try {
            System.out.println(new BigInteger("1111111111111").byteValueExact());
        } catch (ArithmeticException e) {

        }

        // Math.multiplyExact(),若100000*100000会超出范围抛出异常. 100000L*10000L
        // Math.toIntExact()将long转换为int
        try {
//            Math.multiplyExact(100000L, 100000);
//            Math.toIntExact(1000L);
        } catch (ArithmeticException e) {
            e.printStackTrace();
        }

        System.out.println(-5%5);
    }

    @Test
    public void collection() {
        List<String> words = Lists.newArrayList("a", "ab", "abc", "abcd", "abcde", "bcd");
        // Collection只有removeIf添加了lambda方法, Stream的大部分方法都是将一个原始流转换成其他流
        // removeIf会在原来集合的基础上删除
        words.removeIf(w -> w.startsWith("a"));
        words.forEach(System.out::println);

        // 去重,distinct对任何集合消耗都比较大
        getWordsAsList().stream().distinct().forEach(System.out::println);
    }

    @Test
    public void newCollection() {
        // Iterator.forEach()
        getWordsAsList().forEach(System.out::println);

        // Collection removeIf过滤删除
        Lists.newArrayList("a", "ab", "abc", "abcd", "abcde", "bcd").removeIf(s -> s.startsWith("a"));

        // replaceAll(一元操作)
        List<String> words = getWordsAsList();
        words.replaceAll(s -> s + "1111111");
        words.forEach(System.out::println);

        // sort排序,使用sort(Comparator)
        words.sort(String::compareTo);
        words.forEach(System.out::println);

        //Map  添加了forEach, replace, replaceAll, remove(key, value)当key和value映射存在时才删除,putIfAbsent, compute, computeIfAbsent, merge

    }

    /**
     * Arrays.sort(array, comparator)数组排序
     * Comparator.comparing(Person::getAge)
     */
    @Test
    public void comparator() {
        final ArrayList<Person> persons = Lists.newArrayList(new Person("luolibing", 21), new Person("liuxiaoling", 21), new Person("luominghao", 4), new Person("luolibing", 67));
        final Person[] personArray = persons.toArray(new Person[persons.size()]);
        // thenComparing() 组合排序,进行多级比较 Comparator.comparing(Person::getName, (f,s) -> Integer.compare(f.length(),s.length()))
        Arrays.sort(personArray, Comparator.comparing(Person::getAge).thenComparing(Person::getName));
        // int类型比较, Comparator.nullsFirst(),将Null排前面
        Arrays.sort(personArray, Comparator.comparingInt(Person::getAge));
        Arrays.stream(personArray).forEach(System.out::println);

        // Comparator.nullsFirst()将Null排在前面,Comparator.naturalOrder()产生Comparator
        // Comparator.reverseOrder()反序排序
        ArrayList<Person> personList = Lists.newArrayList(new Person("luolibing", 21), new Person("liuxiaoling", 21), new Person("luominghao", 4), new Person("luolibing", 67), new Person(null, 67));
        final Person[] personArray1 = personList.toArray(new Person[personList.size()]);
        Arrays.sort(personArray1, Comparator.comparing(Person::getName, Comparator.nullsFirst(Comparator.naturalOrder())));
        Arrays.sort(personArray1, Comparator.comparing(Person::getName, Comparator.nullsFirst(Comparator.reverseOrder())));
        Arrays.stream(personArray1).forEach(w -> {if(w!=null) System.out.println(w); else System.out.println("null");});
    }

    class Person {
        String name;
        int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

    /**
     * Collections.checkedQueue(queue, Class)
     */
    @Test
    public void collections() {
        final Queue queue1 = Collections.checkedQueue(new ArrayBlockingQueue(10), Integer.class);
        queue1.add("4343");
        final List integers = Collections.checkedList(new ArrayList<>(), Integer.class);
        integers.add("aaa");
        System.out.println(queue1);
        Collections.emptySortedMap();
    }

    /**
     * File延迟读取一个文件中的行Files.lines(path),延迟读取目录Files.list(path)
     * @throws IOException
     */
    @Test
    public void file() throws IOException {
        // Files.lines(path)读取文件中的所有行流
        final Optional<String> password = Files.lines(Paths.get("/share/rms_resource.log")).filter(s -> s.contains("coffee")).findFirst();
        password.ifPresent(System.out::println);
        try(Stream<String> stream = Files.lines(Paths.get("/share/rms_resource.log")).onClose(() -> System.out.println("closing inputstream"))) {
            stream.filter(s -> s.contains("check")).findFirst().ifPresent(System.out::println);
        }

        // Files.list(path) 读取文件目录下文件集合流
        Files.list(Paths.get("/share")).forEach(System.out::println);

        // java7 DirectoryStream类有iterator()方法,所以可以使用forEach()方式遍历
        try(DirectoryStream<Path> paths = Files.newDirectoryStream(Paths.get("/share"))) {
            for(Path entry : paths) {
                System.out.println(entry);
            }
        }

        // Files.walk(path) 遍历文件流
        Files.walk(Paths.get("/share"), 3);
        // Files.find(path, dept, (p,attributes) -> p.toString().contains("/share/data")),查找文件,
        Files.find(Paths.get("/share"), 5, (p, attributes) -> p.toString().contains("/share/data"));
        // 递归遍历文件目录
        try(Stream<Path> entries = Files.walk(Paths.get("/share"))) {
            entries.forEach(System.out::println);
        }
    }

    /**
     * java8之前有一个 java.util.prefs.Base64, sun.misc.BASE64Encoder
     * Base64使用64个字符来编码6bit信息
     */
    @Test
    public void base64() throws IOException {
        // 编码字符串
        final Base64.Encoder encoder = Base64.getEncoder();
        final String original = String.join(":", "admin", "password111111111");
        final String target = encoder.encodeToString(original.getBytes(StandardCharsets.UTF_8));
        System.out.println(target);

        // 编码outputstream
        Path originalPath = Paths.get("/share/rms_resource.log");
        Path targetFile = Paths.get("/share/rms_resource_base64.log");
        Base64.Encoder mimeEncoder = Base64.getMimeEncoder();
        try(OutputStream out = Files.newOutputStream(targetFile)) {
            Files.copy(originalPath, mimeEncoder.wrap(out));
        } catch (IOException e) {

        }

        // 解码
        final Base64.Decoder mimeDecoder = Base64.getMimeDecoder();
        try(InputStream in = Files.newInputStream(targetFile)) {
            Files.copy(mimeDecoder.wrap(in), originalPath);
        }
    }

    /**
     * java8注解加入了课重复注解及可用于类型的注解
     */
    @Test
    public void annotation() throws NoSuchMethodException {
        // java8之前有时候不得不编写一些丑陋的代码例如  @PrimaryKeyJoinColumns({PrimaryKeyJoinColumn(name="id"),PrimaryKeyJoinColumn(name="REGION")})
        Class clazz = Test1.class;
        System.out.println(clazz.getAnnotation(TestCase.class));
        System.out.println(clazz.getAnnotationsByType(TestCases.class).length);
    }

    /**
     * 添加两个@TestCase注解,会自动转换为诶TestCases注解
     * @return
     */
    @TestCase(params = "4", expect = "24")
    @TestCase(params = "0", expect = "1")
    public class Test1 {

    }

    @Repeatable(TestCases.class)
    @interface TestCase {
        String params();

        String expect();
    }

    @interface TestCases {
        TestCase[] value();
    }

//    private @NonNull List<String> names = new ArrayList<>();

    @Test
    public void typeAnnotation() {

    }

    @Test
    public void paramName() {

    }

    Person getEmployee() {
        return new Person("lll", 28);
    }

    @Test
    public void nullable() {
        List<String> names = Lists.newArrayList("luolibing", "liu", null, "luo");
        // Objects添加了静态方法isNull方法
        System.out.println(names.stream().anyMatch(Objects::isNull));
        // nonNull过滤获取非空元素
        System.out.println(names.stream().filter(Objects::nonNull));
    }

    /**
     * 延迟消息
     */
    @Test
    public void logger() {
        final Logger logger = Logger.getGlobal();
        // 只有当日志级别达到要求是,才会对消息进行格式化或者 + 操作处理
        logger.finest(() -> "saba");

        // null处理,如果name为null,调用闭包里面的代码,并且抛出异常将闭包中的返回字符串作为异常输出消息
        String name = null;
        Objects.requireNonNull(name, () -> {
            System.out.println("NullpointException !!!");
            return "default message!!!";
        });
    }

    @Test
    public void match() throws IOException {
        // java8正则Pattern添加了splitAsStream()方法获取一个流
        final String contents = new String(Files.readAllBytes(Paths.get("/share/rms_resource.log")), StandardCharsets.UTF_8);
        Pattern.compile("[\\P{L}]+").splitAsStream(contents).forEach(System.out::println);
    }

    @Test
    public void lanEnv() {
        final List<Locale.LanguageRange> ranges = Stream.of("de", "*-CH")
                .map(Locale.LanguageRange::new)
                .collect(Collectors.toList());
    }
}
