package cn.tim.java8.lambda;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by LuoLiBing on 16/7/5.
 */
public class FunctionLambda extends Application implements Exercise {

    private final static Logger logger = LoggerFactory.getLogger(FunctionLambda.class);

    /**
     * 延迟执行,并不像java7一样执行info方法的时候就先把参数给执行一遍
     */
    @Test
    public void delay() {
        info(logger, this::createMessage);
    }

    /**
     * 将lambda作为参数传递
     */
    @Test
    public void param() {
        Arrays.sort(getWordsAsArray(), (s, t) -> Integer.compare(s.length(), t.length()));
        repeat(10, i -> System.out.println("luolibing" + i));
    }

    /**
     * 延迟日志
     * @param logger
     * @param message
     */
    public static void info(Logger logger, Supplier<String> message) {
        if(logger.isInfoEnabled()) {
            logger.info(message.get());
        }
    }

    public static void repeat(int n, IntConsumer action) {
        for(int i = 0; i < n; i++) action.accept(i);
    }

    private String createMessage() {
        System.out.println("getMessage");
        return "罗立兵:" + "您好!";
    }

    @Test
    public void onAction() {
        final JButton button = new JButton("确定");
        button.addActionListener(event -> System.out.println(event.getID()));
        button.addActionListener(event -> System.out.println(event.getID()));
        button.doClick();
    }

    /**
     * 函数式接口
     * void Runnable.run()                  () -> {}
     * T Supplier<T>.get()                  () -> {return T}
     * void Consumer<T>.accept(T)           (T) -> {action(T)}
     * void BiConsumer<T,U>.accept(T,U)     (T,U) -> {action(T,U)}
     * R Function<T,R>.apply(T)             (T) -> {return R}
     * R BiFunction<T,U,R>.apply(T,U)       (T,U) -> {return R}
     * T UnaryOperator<T>.apply(T)          (T) -> {return T}       一元操作
     * T BinaryOperator<T>.apply(T,T)       (T,T) -> {return T}     二元操作
     * boolean Predicate<T>.test(T)         (T) -> {return true}
     * boolean BiPredicate<T,U>.test(T,U)   (T,U) -> {return true}
     */
    @Test
    public void functions() throws IOException {
        try(InputStream in = Files.newInputStream(Paths.get("/Users/tim/Downloads/symphony of color 色彩的和谐.jpg"))){
            Image image = new Image(in);
            Image transform = transform(image, Color::brighter);

        }
    }

    public static Image transform(Image in, UnaryOperator<Color> f) {
        int width = (int) in.getWidth();
        int height = (int) in.getHeight();
        WritableImage out = new WritableImage(width, height);
        IntStream heightStream = IntStream.range(0, height);
        IntStream.range(0, width).forEach( x ->
                heightStream.forEach( y ->
                        out.getPixelWriter().setColor(x, y, f.apply(in.getPixelReader().getColor(x, y))))
        );
        return out;
    }

    /**
     * 函数式接口
     * java并不是一个完全的函数式变成语言,因为它使用了函数式接口
     */
    @FunctionalInterface
    public interface ColorTransformer {
        Color apply(int x, int y, Color colorAtXy);
    }

    /**
     * 组合转换,组合链式转换
     */
    @Test
    public void combine() {
        Image image = new Image("");
        Image image1 = transform(image, Color::brighter);
        Image image2 = transform(image1, Color::grayscale);

        // compose()返回接收了两个UnaryOperator参数,进行链式处理
        final Image transform = transform(image, compose(Color::brighter, Color::grayscale));
    }

    @Test
    public void combineAdd() {
        final int sum = operateNum(1, (x) -> x + 4);
        System.out.println(sum);

        // 链式编程
        final int i = operateNum(1, compose((x) -> x + 4, (x) -> x / 2, (x) -> x * 2));
        System.out.println(i);
    }

    public static int operateNum(int x, UnaryOperator<Integer> operator) {
        return operator.apply(x);
    }

    /**
     * 返回一个函数式接口进行了组合链式转换
     * @param <T>
     * @return
     */
    public static <T> UnaryOperator<T> compose(UnaryOperator<T>...ops) {
        // op1处理完之后,结果交给op2处理最终返回
        return (t) -> {
            for(UnaryOperator<T> op : ops) {
                t = op.apply(t);
            }
            return t;
        };
    }

    /**
     * 链式编程,延迟调用,transform()只是将闭包函数加入到集合当中,toImage调用转换链
     */
    @Test
    public void latentImage() {

    }

    public static void main(String[] args) {
        launch(args);
    }

    public static class LatentImage {
        private Image in;
        private List<UnaryOperator<Color>> pendingOperations;
        private LatentImage(Image in) {
            this.in = in;
            pendingOperations = Lists.newArrayList();
        }

        /**
         * 链式编程需要返回对象自身
         * @param f
         * @return
         */
        public LatentImage transform(UnaryOperator<Color> f) {
            pendingOperations.add(f);
            return this;
        }

        public Image toImage() {
            int width = (int) in.getWidth();
            int height = (int) in.getHeight();
            WritableImage out = new WritableImage(width, height);
            for(int i = 0; i < width; i++) {
                for(int j = 0; j < height; j++) {
                    Color color = in.getPixelReader().getColor(i, j);
                    for(UnaryOperator<Color> f: pendingOperations) {
                        color = f.apply(color);
                    }
                    out.getPixelWriter().setColor(i, j, color);
                }
            }
            return out;
        }

        public static LatentImage from(Image in) {
            return new LatentImage(in);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Image image = new Image("http://192.168.0.111:8060/repository/default/%E8%B5%84%E6%BA%90/%E7%9C%8B%E5%9B%BE%E8%AF%B4%E8%AF%9D%E5%9B%BE%E7%89%87/215/to%20check%20the%20consistency%20%E6%A3%80%E6%9F%A5%E6%B5%93%E5%BA%A6.jpg");
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        StackPane root = new StackPane();
        root.setLayoutX(2);
        root.setLayoutY(2);

        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
//        Color[][] colors = IntStream.range(0, width)
//                .mapToObj(w -> IntStream.range(0, height).mapToObj(h -> image.getPixelReader().getColor(w, h)))
//                .toArray(Color[][]::new);

        Color[][] colors = new Color[width][height];
        for(int i=0; i<width; i++) {
            for(int j=0; j<height; j++) {
                colors[i][j] = image.getPixelReader().getColor(i,j);
            }
        }
        Color[][] outColors = parallelTransform(colors, Color::grayscale);
        WritableImage out = new WritableImage(width, height);
        for(int i=0; i<width; i++) {
            for(int j=0; j<height; j++) {
                if(outColors[i][j] != null) {
                    out.getPixelWriter().setColor(i, j, outColors[i][j]);
                }
            }
        }
        imageView.setImage(out);
        root.getChildren().add(imageView);
        Scene scene = new Scene(root, 600, 300);
        primaryStage.setTitle("Image");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * 并行处理,大图片可以一横条一横条多线程处理
     * @param in
     * @param f
     * @return
     * @throws InterruptedException
     */
    public static Color[][] parallelTransform(Color[][] in, UnaryOperator<Color> f) throws InterruptedException {
        int cores = Runtime.getRuntime().availableProcessors();
        int height = in.length;
        int width = in[0].length;

        Color[][] out = new Color[height][width];
        // 线程池
        ExecutorService pool = Executors.newCachedThreadPool();
        // 线程数
        for(int i = 0; i<cores; i++) {
            // 一次处理一整条
            int fromY = i * height / cores;
            int toY = (i + 1) * height / cores;
            pool.submit(() -> {
                for(int x = 0; x<width; x++) {
                    for(int y = fromY; y < toY; y++) {
                        out[y][x] = f.apply(in[y][x]);
                    }
                }
            });
        }
        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.HOURS);
        return out;
    }

    /**
     * 链式编程
     * @param primaryStage
     * @throws Exception
     */
    public void start1(Stage primaryStage) throws Exception {
        Image image = new Image("http://192.168.0.111:8060/repository/default/%E8%B5%84%E6%BA%90/%E7%9C%8B%E5%9B%BE%E8%AF%B4%E8%AF%9D%E5%9B%BE%E7%89%87/215/to%20check%20the%20consistency%20%E6%A3%80%E6%9F%A5%E6%B5%93%E5%BA%A6.jpg");
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        StackPane root = new StackPane();
        root.setLayoutX(2);
        root.setLayoutY(2);
        root.getChildren().add(imageView);

        Image image1 = LatentImage.from(image)
                .transform(Color::brighter)
                .transform(Color::invert)
                .transform(Color::grayscale)
                .toImage();
        ImageView imageView1 = new ImageView();
        imageView1.setImage(image1);
        root.getChildren().add(imageView1);


        Scene scene = new Scene(root, 600, 300);
        primaryStage.setTitle("Image");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * 异常处理
     */
    @Test
    public void exceptionHandle1() {
        doInOrderAsync1(() -> System.out.println(1/0),() -> System.out.println("aaa"), Throwable::printStackTrace);
    }

    public static void doInOrderAsync1(Runnable first, Runnable second, Consumer<Throwable> handler) {
        new Thread(()  -> {
           try {
               first.run();
               second.run();
           } catch (Throwable t) {
               // 线程抛出异常之后,父线程并不会继续抛出异常,这个时候需要一个异常处理,这个地方提供一个异常处理的方法
               handler.accept(t);
           }
        }).start();
    }

    /**
     * 函数式接口不支持抛出检查期异常,只能在申明函数式方法的时候申明异常
     */
    @Test
    public void exceptionHandle2() {
        doInOrderAsync2(() -> {int i = 1/0; return 1;}, (l) -> System.out.println("getNum" + l), Throwable::printStackTrace);
    }

    /**
     * 第一个函数返回值作为第二个函数的参数
     * @param first
     * @param second
     * @param handler
     * @param <T>
     */
    public static <T> void doInOrderAsync2(Supplier<T> first, Consumer<T> second, Consumer<Throwable> handler) {
        new Thread(() -> {
            try {
                T result = first.get();
                second.accept(result);
            } catch (Throwable e) {
                handler.accept(e);
            }

        }).start();
    }

    /**
     * Callable是声明了抛出异常,所以可以使用
     * @param f
     * @param <T>
     * @return
     */
    public static <T> Supplier<T> unchecked(Callable<T> f) {
        return () -> {
            try {
                return f.call();
            } catch (Exception e) {
                throw new RuntimeException();
            } catch (Throwable e) {
                throw e;
            }
        };
    }


    @Test
    public void array() {
        IntFunction<String[]> intFunction  = null;
        String[] apply =intFunction.apply(5);

        List<Map> mapList = Lists.newArrayList();
        List<HashMap> hashMapList = Lists.newArrayList();
        // 这样是不被允许的
        // mapList = hashMapList;
        // 假设合法,将hashMap放到map里面了
        // mapList.add(new HashMap())

        List<? extends Map> mapList1 = Lists.newArrayList();
        List<HashMap> hashMapList1 = Lists.newArrayList();
        // 这个时候允许传递List<Map>和List<HashMap>
        mapList1 = hashMapList1;

        // ? extend Map 表示上界,最高可以传递Map类型,最高可以传递到Map,继承Map和map本身都可以传递
        // ? super HashMap 表示下界,最低可以传递HashMap,表示HashMap以上的父类或者接口都可以传递
        // 读取时协变,写入时逆变
    }

    /**
     * http://stackoverflow.com/questions/4343202/difference-between-super-t-and-extends-t-in-java
     *
     * "Producer Extends" - If you need a List to produce T values (you want to read Ts from the list),
     *                      you need to declare it with ? extends T, e.g. List<? extends Integer>. But you cannot add to this list.
     * "Consumer Super" - If you need a List to consume T values (you want to write Ts into the list),
     *                      you need to declare it with ? super T, e.g. List<? super Integer>. But there are no guarantees what type of object you may read from this list.
     * If you need to both read from and write to a list, you need to declare it exactly with no wildcards, e.g. List<Integer>.
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @Test
    public void superTest() throws InstantiationException, IllegalAccessException {
        // ? extends Number 可以接收Number的本身与其子类
        List<? extends Number> foo1 = new ArrayList<Integer>();
        List<? extends Number> foo2 = new ArrayList<Number>();
        List<? extends Number> foo3 = new ArrayList<Double>();

        // 赋值ArrayList,可以使用Person\Employee,添加的时候可以使用Employee的本身和子类Employee\Manager, 读取get用extend,写入add使用super
        List<? super Employee> foo11 = new ArrayList<Employee>();
        foo11.add(new Employee());
        foo11.add(new Manager());
        // 不能添加add方法
        // foo1.add(Integer.valueOf(1));
        // Producer Extends 读取值,不能调用add方法
        List<Number> collect = Stream.of(1, 2, 3, 4).collect(Collectors.toList());
        superDrive(collect);
    }

    @Test
    public void test1() {
        System.out.println(Runtime.getRuntime().availableProcessors());
        System.out.println(Thread.currentThread().getId());
        final HashSet<Object> threads = Sets.newHashSet();
        threads.add(Thread.currentThread().getId());
        IntStream.range(0, 100).parallel().forEach(t -> {
            //System.out.println(Thread.currentThread().getId());
            threads.add(Thread.currentThread().getId());
        });
        threads.forEach(System.out::println);
    }

    public void superDrive(List<? extends Number> list) {
        list.forEach(System.out::println);
    }

    @Test
    public void extendDrive() {
        doInOrderAsync(() -> new Person(), System.out::println, Throwable::printStackTrace);
    }

    class Person {

    }

    class Employee extends Person {

    }

    class Manager extends Employee {

    }

    /**
     * producer读取使用extend, 消费者写入使用super
     * pecs
     * @param first
     * @param second
     * @param handler
     * @param <T>
     */
    public static <T> void doInOrderAsync(Supplier<? extends T> first, Consumer<? super T> second, Consumer<? super Throwable> handler) {}

    /**
     * 读取使用extend,写入使用super
     * @param dest
     * @param src
     * @param <T>
     */
    public static <T> void copy(List<? super T> dest, List<? extends T> src) {
        for (int i=0; i<src.size(); i++)
            dest.set(i,src.get(i));
    }

    @Test
    public void testFinal() {
        final int[] index = {0};
        IntStream.range(0, 100).forEach( i -> {
            index[0] ++;
        });
        System.out.println(index[0]);
    }


    <T> List<List<T>> split1(List<T> input, Predicate<T> predicate) {
        List<List<T>> result = new ArrayList<>();
        int start = 0;

        for(int cur = 0; cur < input.size(); cur++) {
            if(predicate.test(input.get(cur))) {
                result.add(input.subList(start, cur));
                start = cur + 1;
            }
        }
        result.add(input.subList(start, input.size()));
        return result;
    }


    <T> List<List<T>> split2(List<T> input, Predicate<T> predicate) {
        int[] temp = IntStream.range(0, input.size())
                .filter(i -> predicate.test(input.get(i)))
                .toArray();
        // 添加闭合标识
        int[] edges = new int[temp.length + 2];
        System.arraycopy(temp, 0, edges, 1, temp.length);
        edges[0] = -1;
        edges[edges.length - 1] = input.size();
        return IntStream.range(0, edges.length - 1)
                .mapToObj(k -> input.subList(edges[k] + 1, edges[k + 1]))
                .collect(Collectors.toList());
    }


    <T> List<List<T>> split3(List<T> input, Predicate<T> predicate) {
        int[] edges = IntStream.range(-1, input.size() + 1)
                .filter(i -> i == -1 || i == input.size() || predicate.test(input.get(i)))
                .toArray();
        return IntStream.range(0, edges.length - 1)
                .mapToObj(k -> input.subList(edges[k] + 1, edges[k + 1]))
                .collect(Collectors.toList());
    }


    @Test
    public void splitTest1() {
        List<Integer> input = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<List<Integer>> result = split1(input, (index) -> index != 0 && index % 2 == 0);
        System.out.println(result);
    }


    @Test
    public void splitTest2() {
        List<Integer> input = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<List<Integer>> result = split2(input, (index) -> index != 0 && index % 2 == 0);
        System.out.println(result);
    }


    @Test
    public void splitTest3() {
        List<Integer> input = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<List<Integer>> result = split3(input, (index) -> index != 0 && index % 2 == 0);
        System.out.println(result);
    }
}
