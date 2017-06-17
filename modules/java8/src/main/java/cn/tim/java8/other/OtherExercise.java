package cn.tim.java8.other;

import cn.tim.java8.lambda.Exercise;
import com.google.common.collect.Lists;
import javafx.geometry.Point2D;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Created by LuoLiBing on 16/7/25.
 */
public class OtherExercise implements Exercise {

    @Test
    public void removeIf() {
        final List<String> wordsAsList = Lists.newArrayList(getWordsAsList());
        wordsAsList.removeIf((w) -> w.length() <12);
        wordsAsList.forEach(System.out::println);
    }

    @Test
    public void comparator() {
        Point2D point1 = new Point2D(10, 4);
        Point2D point2 = new Point2D(5, 50);
        Comparator<Point2D> comparator = Comparator.comparing(Point2D::getX).thenComparing(Point2D::getY);
        comparator.compare(point1, point2);
    }

    @Test
    public void arrays() {
        String[] words = {
                "bbb",
                "aaa",
                null,
                "ccc"
        };
        Arrays.sort(words, Comparator.nullsLast(Comparator.reverseOrder()));
        Arrays.stream(words).forEach(System.out::println);
    }

    @Test
    public void checkQueue() {
        // 通过一个checkedQueue方式,产生一个可以验证的queue,当add不是对应类型的对象时抛出ClassCastException异常
        Queue<URL> queue = Collections.checkedQueue(new LinkedList<>(), URL.class);
        // 当将queue传入一个并没有泛型的方法中时,类型会被擦除,导致,不是URL类型的数据也被加入进来就形成了脏数据
//         Queue<URL> queue = new LinkedList<>();
        push(queue);
        queue.forEach(System.out::println);
    }

    public void push(Queue queue) {
        queue.add("aaa");
    }

    @Test
    public void walk() throws IOException {
        // Files.walk()产生一个stream延迟IO, 同样Files.lines()
        try(Stream<Path> pathStream = Files.walk(Paths.get("/share/jdk"))) {
            pathStream.parallel().filter(path -> !Files.isDirectory(path)).forEach( p -> {
                try {
                    if(Files.lines(p).anyMatch(line -> {
                        try{
                            return line.contains("transient") || line.contains("volatile");
                        } catch (Exception e) {
                            return false;
                        }})) {
                        System.out.println(p);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @Test
    public void base64() {
        try {
            URL url = new URL("http", "localhost", 8080, "/manager/html");
            URLConnection connection = url.openConnection();
            String auth = "tomcat:s3cret";
            connection.setRequestProperty("Authorization", "Basic " + Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8)));
            connection.connect();
            try(BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))){
                reader.lines().forEach(System.out::println);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCase() {
        Class clazz = Math.class;
        Method[] methods = clazz.getDeclaredMethods();
        for(Method method : methods) {
            TestCase[] testCases = method.getAnnotationsByType(TestCase.class);
            assert testCases.length > 0;
            for(TestCase testCase : testCases) {
                assert testCase.expected() == Math.factorial(testCase.params());
            }
        }
    }

    @Test(expected = NullPointerException.class)
    public void requiredNonNullTest() {
        requiredNonNull(null);
    }

    /**
     * requireNonNull方法接收一个对象参数,当obj为空时抛出空指针异常,消息为exceptionMsg闭包产生的值
     * Objects.requireNonNull(obj, exceptionMsg)
     * @param a
     */
    public void requiredNonNull(String a) {
        Objects.requireNonNull(a, () -> "[" + LocalDateTime.now() + "]: arg must not be null!!!");
    }

    @Test
    public void grepTest() throws URISyntaxException, IOException {
        grep(Pattern.compile("(?m)^Alice"), Paths.get(this.getClass().getResource("/alice.txt").toURI()));
    }

    /**
     * pattern.asPredicate()将正则表达式作为过滤条件
     * @param pattern
     * @param path
     * @throws IOException
     */
    public void grep(Pattern pattern, Path path) throws IOException {
        Files.lines(path).filter(pattern.asPredicate()).forEach(System.out::println);
    }

    @Test
    public void match() {
        final Optional<Address> addressOptional = parse("Niagara Falls, NY 14305");
        System.out.println(addressOptional);
    }

    /**
     * 正则表达式,pattern.matcher(Str)  match.find()匹配  match.group("field")
     * @param in
     * @return
     */
    public Optional<Address> parse(String in) {
        Pattern pattern = Pattern.compile("(?<city>[\\p{L} ]+),\\s*(?<state>[A-Z]{2})\\s*(?<zip>[\\d]{5}|[\\d]{9})");
        Matcher matcher = pattern.matcher(in);
        if(matcher.find()) {
            Address address = new Address(
                    matcher.group("city"),
                    matcher.group("state"),
                    matcher.group("zip")
            );
            return Optional.of(address);
        }
        return Optional.empty();
    }

    class Address {
        String city;
        String state;
        String zipCode;
        public Address(String city, String state, String zipCode) {
            this.city = city;
            this.state = state;
            this.zipCode = zipCode;
        }
    }
}
