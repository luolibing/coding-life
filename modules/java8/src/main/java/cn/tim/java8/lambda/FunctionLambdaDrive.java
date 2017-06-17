package cn.tim.java8.lambda;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by LuoLiBing on 16/7/11.
 */
public class FunctionLambdaDrive extends Application implements Exercise {

    public final static Logger log = LoggerFactory.getLogger(FunctionLambdaDrive.class);

    @Test
    public void test1() {
        logIf(Level.DEBUG, () -> "罗立兵,您好!!!");
    }

    public static void logIf(Level level, Supplier<String> supplier) {
        if(level == Level.INFO) {
            log.info(supplier.get());
        } else if(level == Level.DEBUG) {
            log.debug(supplier.get());
        } else if(level == Level.ERROR) {
            log.error(supplier.get());
        }
    }

    //@Override
    public void start1(Stage stage) throws Exception {
        Image image = new Image("http://192.168.0.111:8060/repository/default/%E8%B5%84%E6%BA%90/%E5%9B%BE%E7%89%87/195/to%20renovate%20the%20pagoda%20%E7%BF%BB%E4%BF%AE%E5%AE%9D%E5%A1%94.jpg");
        Image image2 = transform(image, (x, y, color) -> color.brighter());
        Image image3 = transform(image2, (x, y, color) -> color.grayscale());
        Image image4 = transform(image,
                (x, y, c) -> (
                        x <= 10 || x >= image.getWidth() - 10
                                || y <= 10 || y >= image.getHeight() - 10) ? Color.GREY : c);
        stage.setScene(new Scene(new HBox(new ImageView(image), new ImageView(image2), new ImageView(image3), new ImageView(image4))));
        stage.show();
    }

    /**
     * 使用一个ColorTransformer函数式接口
     * @param oldImage
     * @param transformer
     * @return
     */
    public static Image transform(Image oldImage, ColorTransformer transformer) {
        int width = (int) oldImage.getWidth();
        int height = (int) oldImage.getHeight();
        WritableImage out = new WritableImage(width, height);
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                Color newColor = transformer.apply(x, y, oldImage.getPixelReader().getColor(x, y));
                out.getPixelWriter().setColor(x, y, newColor);
            }
        }
        return out;
    }

    enum Level {
        ERROR, INFO, DEBUG
    }

    @Test
    public void test2() {
        withLock(new ReentrantLock(), () -> {
            System.out.println("aaaa");
            throw new RuntimeException("测试错误");
        });
    }

    public static void withLock(ReentrantLock reentrantLock, Runnable action) {
        reentrantLock.lock();
        try {
            action.run();
        } finally {
            reentrantLock.unlock();
        }
    }

    @Test
    public void test3() {
        assertThat(() -> 2 * 3 == 7);
    }

    /**
     * BooleanSupplier返回boolean的producer
     * @param supplier
     */
    public void assertThat(BooleanSupplier supplier) {
        if(!supplier.getAsBoolean()) {
            throw new AssertionError();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * 比较两个对象
     * @param fieldNames
     * @param <T>
     * @return
     */
    public <T> Comparator<T> lexComparator(String...fieldNames) {
        // 返回一个lambda闭包
        return (x, y) -> {
            for(String filedName : fieldNames) {
                  try {
                      Field field = x.getClass().getDeclaredField(filedName);
                      field.setAccessible(true);
                      Object objX = field.get(x);
                      Object objY = field.get(y);

                      if(objX == null && objY == null) continue;
                      if(objX == null || objY == null) return objX == null ? 1: -1;
                      int compare = objX.toString().compareTo(objY.toString());
                      if(compare != 0) {
                          return compare;
                      }
                  } catch (Exception e) {
                      e.printStackTrace();
                  }
            }
            return 0;
        };
    }

    @Test
    public void test8() {
        Person[] persons = {
                new Person("John", "Green"),
                new Person(null, "Black"),
                new Person("Adam", "White")
        };
        Arrays.sort(persons, lexComparator("firstname", "lastname"));
        Stream.of(persons).forEach(System.out::println);
    }

    @Test
    public void test9() {
        UnaryOperator<Color> op = Color::brighter;
        // 组合闭包调用,compose返回的是一个Function<T, U>函数
        transform(new Image(""), op.compose(Color::grayscale));
    }

    public static Image transform(Image oldImage, Function<Color, Color> action) {
        int width = (int) oldImage.getWidth();
        int height = (int) oldImage.getHeight();
        WritableImage out = new WritableImage(width, height);
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                Color newColor = action.apply(oldImage.getPixelReader().getColor(x, y));
                out.getPixelWriter().setColor(x, y, newColor);
            }
        }
        return out;
    }

    /**
     * 组合模式
     * @param first
     * @param second
     * @return
     */
    public static ColorTransformer compose(ColorTransformer first, ColorTransformer second) {
        return (x, y, color) -> second.apply(x,y, first.apply(x, y, color));
    }

    public static ColorTransformer map(UnaryOperator<Color> op) {
        return (x, y, color) -> op.apply(color);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Image image = new Image("http://192.168.0.111:8060/repository/default/%E8%B5%84%E6%BA%90/%E5%9B%BE%E7%89%87/195/to%20renovate%20the%20pagoda%20%E7%BF%BB%E4%BF%AE%E5%AE%9D%E5%A1%94.jpg");
        Image image1 = transform(image, compose(map(Color::grayscale),
                (x, y, c) -> (
                        x <= 10 || x >= image.getWidth() - 10
                                || y <= 10 || y >= image.getHeight() - 10) ? Color.GREY : c));
        stage.setScene(new Scene(new HBox(new ImageView(image), new ImageView(image1))));
        stage.show();
    }

    public <T> Color extendTest(Supplier<? extends UnaryOperator<Color>> supplier, Color color) {
        return supplier.get().apply(color);
    }

    @Test
    public void test10() {
        doInAsync(
                () -> Integer.valueOf("aaa"),
                (t, e) -> {
                    // t不为空
                    if(t != null) {
                        System.out.println("valueOf " + t);
                    } else {
                        // 异常
                        if(e instanceof NumberFormatException) {
                            System.out.println("转换错误");
                        } else {
                            System.out.println("未知错误!!!");
                        }
                    }
                }
        );
    }

    /**
     * 在这个地方使用BiConsumer的好处在于可以处理Throwable
     * @param supplier
     * @param consumer
     * @param <T>
     */
    public static <T> void doInAsync(Supplier<T> supplier, BiConsumer<T,Throwable> consumer) {
        new Thread( () -> {
            try {
                T t = supplier.get();
                consumer.accept(t, null);
            } catch (Throwable e) {
                consumer.accept(null, e);
            }
        }).start();
    }

    @Test
    public void test17() {
        doInAsync1(
                () -> System.out.println("aaa"),
                () -> System.out.println("bbb"),
                (e) -> System.out.println(e.getMessage())
        );
    }

    public static void doInAsync1(Runnable first, Runnable second, Consumer<Throwable> consumer) {
        new Thread(() -> {
            new Thread(() -> {
                try {
                    first.run();
                } catch (Exception e) {
                    consumer.accept(e);
                }
            }).start();
            new Thread(() -> {
                try {
                    second.run();
                } catch (Exception e) {
                    consumer.accept(e);
                }
            }).start();
        }).start();
    }

    @FunctionalInterface
    interface FunctionThrowable<T, U> {
        U apply(T t) throws Exception;
    }

    public <T, R> Function<T, R> unchecked(FunctionThrowable<T,R> action) {
        return (a) -> {
            try {
                return action.apply(a);
            } catch (Exception e) {
                throw new RuntimeException();
            }
        };
    }

    @Test
    public void test18() {
        unchecked((String p) -> Files.readAllBytes(Paths.get(p)).length).apply("/dumy.txt");
    }

    @Test
    public void test20() {
        final List<Integer> map = map(Arrays.asList("1", "2", "3", "4"), Integer::valueOf);
        map.forEach(System.out::println);
    }

    public static <T, U> List<U> map(List<T> list, Function<T, U> mapper) {
        return list.stream().map(mapper).collect(Collectors.toList());
    }

    @Test
    public void test21() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<String> strFuture = executorService.submit(() -> "1");
        // 两个参数,Future和Integer::parseInt函数
        Future<Integer> intFuture = map(strFuture, Integer::parseInt);
        try {
            intFuture.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用代理模式,用Function 和 Future<T>来实现
     * @param future
     * @param mapper
     * @param <T>
     * @param <U>
     * @return
     */
    public static <T, U> Future<U> map(Future<T> future, Function<T, U> mapper) {
        return new Future<U>() {
            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                return future.cancel(mayInterruptIfRunning);
            }

            @Override
            public boolean isCancelled() {
                return future.isCancelled();
            }

            @Override
            public boolean isDone() {
                return future.isDone();
            }

            @Override
            public U get() throws InterruptedException, ExecutionException {
                return mapper.apply(future.get());
            }

            @Override
            public U get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
                return mapper.apply(future.get(timeout, unit));
            }
        };
    }

    @Test
    public void test22() {
        ExecutorService executor = Executors.newCachedThreadPool();
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "罗立兵", executor);
        future.thenApply(Integer::parseInt)                                                 // map
                // flatMap 将前面的函数返回的结果作为第二个函数的参数,一起执行
                .thenCompose((a) -> CompletableFuture.supplyAsync(() -> 1 + a, executor))   // flatMap
                .thenAccept(System.out::println);
    }

    @Test
    public void test23() {
        Pair<String> strPair = new Pair<>("1", "2");
        Pair<Integer> intPair = strPair.map(Integer::parseInt);
        System.out.println(intPair.one + ":" + intPair.two);
    }

    @Test
    public void test24() {
        Pair<String> strPair = new Pair<>("1", "2");
        Pair<Integer> intPair = strPair.flatMap((a, b) -> new Pair<>(Integer.parseInt(a), Integer.parseInt(b)));
    }

    private class Pair<T> {
        T one;
        T two;

        private Pair(T one, T two) {
            this.one = one;
            this.two = two;
        }

        <U> Pair<U> map(Function<? super T, ? extends U> mapper) {
            return new Pair<>(mapper.apply(one), mapper.apply(two));
        }

        // BiFunction调用的另外一个方法
        <U> Pair<U> flatMap(BiFunction<? super T, ? super T, Pair<U>> mapper) {
            return mapper.apply(one, two);
        }
    }

}

class Person {
    private String firstname;
    private String lastname;

    Person(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String toString() {
        return "firstname:" + firstname + ",lastname:" + lastname;
    }
}

@FunctionalInterface
interface ColorTransformer {
    Color apply(int x, int y, Color color);
}

@FunctionalInterface
interface ColorTransformer1<Color> extends UnaryOperator<Color> {

}



