package cn.tim.java8.lambda;

import org.junit.Test;

import javax.swing.*;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Created by LuoLiBing on 16/6/29.
 */
public class LambdaExercises implements Exercise {

    /**
     * 在同一个线程
     */
    @Test
    public void sortLambda() {
        Arrays.sort(new Integer[] {1,2,6,3,2,7}, (a, b) -> {
            System.out.println("测试1");
            Thread thread = Thread.currentThread();
            System.out.println(thread.getId());
            return 1;
        });
        System.out.println("ceshi");
        Thread thread = Thread.currentThread();
        System.out.println(thread.getId());
    }

    @Test
    public void listFilter() {
        File[] files1 = Paths.get("/share").toFile().listFiles((f) -> f.isDirectory());
        File[] files = Paths.get("/share").toFile().listFiles(File::isDirectory);
        for(File file : files) {
            System.out.println(file.getName());
        }
    }

    @Test
    public void listFile() {
        Path targetPath = Paths.get("/share");
        String extension = "log";
        String[] fileNames = targetPath.toFile().list((f, name) -> {
            System.out.println(name);
            // (f.isFile()) &&
            return name.endsWith("." + extension);
        });
        for(String name: fileNames) {
            System.out.println("name:" + name);
        }
    }

    @Test
    public void compartor1() {
        File[] files = Paths.get("/share").toFile().listFiles(File::isDirectory);
//        Arrays.sort(files, (a, b) -> {
//            StringComparable.getComparator()});
    }

    @Test
    public void actionListener() {
        JButton button = new JButton("aaa");
        button.addActionListener(System.out::print);
        button.doClick();
    }

    /**
     * 捕获了一个不同的值,因为传递给闭包的值必定是一个final不可变类型
     */
    @Test
    public void forEach() {
        Integer[] ages = new Integer[] {1,2,3,5,};
        List<Runnable> runners = new ArrayList<>();
        // 因为传递给闭包的是一个变化的i参数,所以必须转换成final 之后才可以执行
        for(int i =0; i<ages.length; i++) {
            final int finalI = i;
            runners.add(() -> System.out.println(ages[finalI].toString()));
        }

        for(Integer age: ages) {
            System.out.println("age=" + age);
        }
        runners.forEach((runnable) ->new Thread(runnable).start());
    }

    /*************参考解法**************/
    /**
     * Array.sort和调用sort方法是同一线程吗
     * 1 使用串行排序,使用的是同一个线程
     * 2 使用并行排序,使用了多个线程,其中主线程也参与了其中的一部分,一般不超过最大可用处理器个数
     */
    @Test
    public void test1() {
        long id = Thread.currentThread().getId();
        System.out.println("test1 threadId=" + id);

        CopyOnWriteArraySet<Long> threadIds = new CopyOnWriteArraySet<>();
        String[] wordsAsArray = getWordsAsArray();
        Arrays.sort(wordsAsArray, (a, b) -> {
            threadIds.add(Thread.currentThread().getId());
            return a.compareTo(b);
        });
        assert threadIds.size() == 1;
        threadIds.clear();

        Arrays.parallelSort(wordsAsArray, (a, b) -> {
            threadIds.add(Thread.currentThread().getId());
            return a.compareTo(b);
        });
        threadIds.forEach(System.out::println);

        System.out.println("processors: " + Runtime.getRuntime().availableProcessors());
    }

    /**
     * File.listFiles和isDirectory方法
     */
    @Test
    public void test2() {
        Arrays.asList(getChildDirsWithLambda(".")).forEach(System.out::println);
    }

    private static File[] getChildDirsWithLambda(String dir) {
        return new File(dir).listFiles(File::isDirectory);
    }

    /**
     * 过滤某个文件夹下所有指定扩展名文件
     */
    @Test
    public void test3() {
        File[] list = list(".", ".class");
        Stream.of(list).forEach(System.out::println);
    }

    private static File[] list(String dir, String ext) {
        File file = new File(dir);
        return file.listFiles(f -> f.getName().endsWith(ext));
    }

    /**
     * 目录排序
     * 先排目录,然后再排文件,同类型按照字典序
     */
    @Test
    public void test4() {
        Stream.of(sort(".")).forEach(System.out::println);
    }

    private static File[] sort(String dir) {
        File dirFile = new File(dir);
        File[] files = dirFile.listFiles();
        Arrays.sort(files, (a, b) -> {
            if(a.isDirectory() && !b.isDirectory()) {
                return -1;
            } else if(!a.isDirectory() && b.isDirectory()) {
                return 1;
            } else {
                return a.getName().toLowerCase().compareTo(b.getName().toLowerCase());
            }
        });
        return files;
    }


    /***
     * 忽略异常
     */
    @Test
    public void runnableEx() {
        new Thread(uncheck(
                () -> {
                    System.out.println("uncheck");
                    Thread.sleep(1000);
                }
        )).start();
    }

    @FunctionalInterface
    interface RunnableEx {
        void run() throws Exception;
    }

    public static Runnable uncheck(RunnableEx runnableEx) {
        return () -> {
            try {
                runnableEx.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }

    /**
     * andThen方法,接收两个runnable作为参数,并返回一个分别运行这两个实例的Runnable方法
     */
    @Test
    public void andThenTest() {
        new Thread(andThen(
                () -> System.out.println("first"),
                () -> System.out.println("second")
        )).start();
    }

    private static Runnable andThen(Runnable first, Runnable second) {
        return () -> {
            first.run();
            second.run();
        };
    }

    /**
     * final 不变值
     */
    @Test
    public void forTest() {
        String[] names = {"Peter", "Paul", "Mary"};
        List<Runnable> runners = new ArrayList<>();
        for(String name: names) {
            // 闭包会复制一个值到自由变量区,所以当前复制的这个值应该是final类型,但是java8并没有强制要求name为final
            runners.add(() -> System.out.println(name));
        }
        runners.forEach(Runnable::run);
        runners.clear();

        for(int i=0; i<names.length; i++) {
            // i不是一个final不变值,所以会报错,这个地方要么使用一个中间的final类型,或者通过一个对象来接收names[i]
            final int index = i;
            runners.add(() -> System.out.println(names[index]));
        }
        runners.forEach(Runnable::run);
    }

    /**
     *
     */
    @Test
    public void collection2() {
        Collection2<String> strings = new ArrayList2<>();
        strings.add("aaaa");
        strings.add("bbbb");
        strings.add("cccc");

        strings.forEachIf(System.out::println, (s) -> s.length()>3);
    }



    interface Collection2<T> extends Collection<T> {
        default void forEachIf(Consumer<T> action, Predicate<T> filter) {
            forEach(e -> {
                if(filter.test(e)) {
                    action.accept(e);
                }
            });
        }
    }

    public class ArrayList2<T> extends ArrayList<T> implements Collection2<T> {
    }
}
