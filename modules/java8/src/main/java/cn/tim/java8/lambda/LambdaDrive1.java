package cn.tim.java8.lambda;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by LuoLiBing on 16/6/25.
 */
public class LambdaDrive1 {

    class Worker implements Runnable {
        @Override
        public void run() {
            for(int i=0; i<100; i++) {
                System.out.println(i);
            }
        }
    }

    class LenghtCompator implements Comparator<String> {

        @Override
        public int compare(String first, String second) {
            return Integer.compare(first.length(), second.length());
        }
    }

    /**
     * int 比较大小不应该使用 x-y来比较,容易产生溢出
     * JavaFX
     */
    @Test
    public void compare() {
        String[] array = {"abcde", "cd", "111"};
        Arrays.sort(array, new LenghtCompator());
        System.out.println(array);
    }

    @Test
    public void compareLambda() {
        // 单行可以省略大括号
        Comparator<String> comparator = (String first, String second) ->
                Integer.compare(first.length(), second.length());
        String[] array = {"abcde", "cd", "111"};
        Arrays.sort(array, comparator);
        System.out.println(array);

        // 多行可以用大括号
        Comparator<String> comparator1 = (String first, String second) -> {
            if(first.length() < second.length()) {
                return -1;
            } else if(first.length() > second.length()) {
                return 1;
            } else {
                return 0;
            }
        };
        Arrays.sort(array, comparator1);

        // 类型猜测
        Comparator<String> comparator2 = (first, second) -> Integer.compare(first.length(), second.length());

        // 直接加到方法当中
        Arrays.sort(array, (first, second) -> Integer.compare(first.length(), second.length()));
    }

    /**
     * 无参闭包
     */
    @Test
    public void runnableLambda() {
        Runnable runnable = () -> {for(int i=0; i< 100; i++) {
            System.out.println("i=" + i);
        }};
        new Thread(runnable).start();
    }

    /**
     * eventHandler闭包,参数event没有参数类型,可以推到出来(event),(ActionEvent event)
     */
    @Test
    public void actionEvent() {
        EventHandler<ActionEvent> listener = event ->
                System.out.println("thanks for clicking!");
    }

    /**
     * (final String name) -> ...
     * (@NonNull String name) -> ...
     * 函数式接口: 对于只包含一个抽象方法的接口,你可以通过lambda表达式来创建该接口的对象.这种接口被称为函数式接口
     *
     * lambda是一个函数,而不是一个对象,所以lambda不能赋值给Object,lambda是一个函数式接口
     */
    @Test
    public void buttonLambda() {
        new JButton().addActionListener( event -> System.out.println("event:" + event.getSource()));
    }

    /**
     * java.util.function包中定义了许多通用的函数式接口
     * BiFunction<T,U,R>,接收T,U参数返回R类型结果
     */
    @Test
    public void functionLambda1() {
        BiFunction<String, String, Integer> compator = (first, second) -> Integer.compare(first.length(), second.length());
        Integer apply = compator.apply("abcd", "cd");
        System.out.println(apply);
    }

    @Test
    public void functionalInterface1() {
        TestAction test1 = ((first, second) -> System.out.println(first + ":" + second));
        test1.action("aaa", "bbb");
    }

    /**
     * @FunctionalInterface的作用
     * 1 编译器会检查标注该注解的实体,是否只包含一个抽象方法.
     * 2 在javadoc页面也会包含一条声明,说明这个接口是一个函数式接口
     */
    @FunctionalInterface
    interface TestAction {
        void action(String first, String second);
    }


    /**
     * 闭包异常处理
     * 1 不能抛出检查期的异常,解决这个问题的方法是在目标接口的抽象方法中进行声明,或者try catch,可以抛出运行时异常runTimeException
     */
    @Test
    public void exceptionLambda() throws Exception {
        // try catch异常
        Runnable sleep = () -> {
            System.out.println("sleep");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        // 在函数式接口方法声明异常
        ExceptionHandler handler = () -> {
            System.out.println("handler");
            Thread.sleep(1000);
        };

        handler.action();

        // Callable的call方法申明了Exception异常,可以在里面抛出所以异常,返回null即可
        Callable<Void> call = () -> {
            System.out.println("call");
            return null;
        };

        call.call();
    }

    @FunctionalInterface
    interface ExceptionHandler {
        void action() throws InterruptedException;
    }

    /**
     * 方法引用
     * System.out::println 只是一个方法引用,等同于lambda表达式x -> System.out.println(x)
     * Math::pwd 等同于 (x,y) -> Math.pow(x, y)
     * String::compareToIgnoreCase 比较大小等同于 (x, y) -> x.compareToIgnoreCase(y)
     *
     * 对象::实例方法
     * 类::静态方法
     * 类::实例方法
     */
    @Test
    public void functionRef() {
        // 1 System.out 是一个对象  PrintStream 它的实例方法println
        new JButton().addActionListener(System.out::println);
        // 2 Math::pow 类::静态方法   Math.pow(x,y)
        PowInterface powInterface = Math::pow;
        powInterface.pow(1, 2);
        // 3 类实例方法 String::compareToIgnoreCase等同于 (first, second) -> first.compareToIgnoreCase(second)
        Arrays.sort(new String[]{"aaa", "bbb"}, String::compareToIgnoreCase );
    }

    interface PowInterface {
        void pow(int x, int y);
    }

    @Test
    public void equalsLambda() {
        new ConcurrentGreeter().greet();
    }

    class Greeter {
        public void greet() {
            System.out.println("Hello, world!");
            Thread th1  = new Thread(this::greet);
            th1.start();
        }
    }

    /**
     * 方法引用同样适用
     * super::greet 等同于() -> super.greet()
     * this::greet 等同于() -> this.greet()
     */
    class ConcurrentGreeter extends Greeter {
        @Override
        public void greet() {
            Thread th = new Thread(super::greet);
            th.start();
        }
    }

    /**
     * 构造方法引用适用 Button::new 等同于 (arg1...) -> new Button(arg1...)
     * int[]::new 等同于 (x) -> new int[x]
     * new T[n] 是错误的,无法构造一个泛型类型T的数组,因为它会被擦除为new Object[n]
     */
    @Test
    public void constructLambda() {
        List<String> labels = new ArrayList<>();
        labels.add("aaa");
        labels.add("bbb");
        Stream<Button> buttonStream = labels.stream().map(Button::new);
        // x -> new Button[x];
        Button[] buttonsArray = buttonStream.toArray(Button[]::new);
        List<Button> buttons = buttonStream.collect(Collectors.toList());
        for(Button button: buttons) {
            System.out.println("button:" + button.getName());
        }

        for(Button button: buttonsArray) {
            System.out.println("buttonarray: " + button.getName());
        }

        List<int[]> arrays = new ArrayList<>();
        arrays.add(new int[] {4,2,5,1});
        arrays.add(new int[] {4,2,5,1});
        arrays.add(new int[] {4,2,5,1});
        Stream<ArrayLambda> arrayStream = arrays.stream().map(ArrayLambda::new);
        List<ArrayLambda> arrayLambdas = arrayStream.collect(Collectors.toList());
        for(ArrayLambda arrayLambda : arrayLambdas) {
            System.out.println(arrayLambda);
        }
    }

    class ArrayLambda {
        private int[] array;

        public ArrayLambda(int[] array) {
            this.array = array;
        }

        public int[] getArray() {
            return array;
        }
    }


    @Test
    public void paramScope() {
        repeatMessage("luo", 10);
    }

    /**
     * js意义上的闭包,可能闭包在repeatMessage方法执行完之后返回再执行,这个时候text,count参数已经失效,这个时候text,count需要存储起来
     *
     * 一个lambda表达式包含三个部分
     * 1 一段代码
     * 2 参数
     * 3 自由变量的值,这里的自由指的是那些不是参数并且没有在代码重定义的变量,例如text,content.
     * (例如可以将lambda表达式转换为一个只含一个方法的对象,这样自由变量的值就会被复制到该对象的实例变量中.)
     *
     * 什么是闭包:
     * 含有自由变量的代码块被称之为闭包closure,Java中lambda表达式就是闭包,事实上,内部类一直都是闭包
     * @param text
     * @param count
     */
    public static void repeatMessage(String text, int count) {
        Runnable r = () -> {
            for(int i = 0; i < count; i++) {
                System.out.println(text);
                Thread.yield();
            }
        };
        new Thread(r).start();
    }

    /**
     * lambda表达式中的变量不是线程安全的,lambda表达式可以捕获闭合作用域中的变量值
     * 内部类也会捕获闭合作用域的值.java8之前,内部类只允许访问final的局部变量,java8可以访问任何不会改变的局部变量,不变就不会报错
     * 改变一个实例变量或者某个闭合类的静态变量,虽然不会报错,但是这样不恰当
     *
     * @param text
     * @param count
     */
    public static void repeatMessageFinal(String text, int count) {
        Runnable r = () -> {
            while (count > 0) {
                // 闭包中的自由变量不可以被更改
                // count--;
            }
        };

        int matchs = 0;
        for(int i =0; i < 100; i ++) {
            //new Thread(() -> {matchs++}).start();
        }
    }

    /**
     * 作用域
     */
    @Test
    public void scopeClass() {
        // 虽然names是不可变的,但是names集合内部是可变的,这样在多线程的情况下调用add可能会出现问题
        List<String> names = new ArrayList<>();
        for(int i = 0; i < 100; i++) {
            // i是可变的,所以必须使用一个final变量赋值,然后再传递给闭包
            final int finalI = i;
            new Thread(() -> names.add("luolibing" + finalI)).start();
        }
    }

    class Greeting {
        int count = 0;

        public void increment() {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count++;
            System.out.println(count);
        }
    }

    @Test
    public void counterLambda() {
        // 这样就不需要使用final了
        int[] counter = new int[1];
        new Button().addActionListener((event) -> counter[0]++);
    }

    /**
     * lambda中的this表示的是,创建该lambda表达式方法的this参数
     */
    @Test
    public void thisLambda() {
        Runnable runner = () -> System.out.println(this.toString());
        new Thread(runner).start();
    }

    /**
     * 默认方法调用
     * java8中所有迭代器都添加了forEach方法
     * 如果在接口中添加一个forEach方法,所有实现这个接口的类都必须得实现这个forEach
     * 这对于那些老接口是无法接受的,于是大家都希望接口能包含具体实现的方法称为默认方法,这样就可以不修改之前的代码就可以给其实现的类添加方法了
     *
     * iterator中添加了forEach方法,这样List中就不需要再手动添加forEach方法了
     * java8默认方法实现,抽象类和接口之间的差别
     */
    @Test
    public void defaultMethod1() {
        List<String> list = new ArrayList<>();
        list.add("aaa");
        list.add("bbb");
        list.add("ccc");
        list.forEach(System.out::println);
    }

    @Test
    public void defaultMethod2() {
        Monitor monitor = new Monitor();
        System.out.println(monitor.getName() + ":" + monitor.getAge());
    }

    /**
     * 1 父类和接口都实现了的方法,已父类的为准
     * 类优先规则,不能为Object中的方法重新定义一个默认方法,例如toString()和equals()
     * 这样的方法不可能优先于Object.toString()或者Object.equals()
     * 2 两个接口都定义了这个方法,需要通过覆盖来解决冲突,尽管有默认的实现
     */
    class Monitor extends Student implements Person, Child {

        @Override
        public String getName() {
            // 通过接口名.super.方法名来决定调用哪一个默认实现
            return Child.super.getName();
        }
    }

    class Student {

        public int getAge() {
            return 10;
        }

    }

    interface Child {
        default String getName() {
            return "child : getName";
        }
    }

    interface Person {
        int getAge();
        // 接口方法默认的实现
        default String getName() {
            return "John Q . Public";
        }
    }

    /**
     * java8中可以给接口添加静态方法,从技术角度说这是合法的,只是看起来违背了接口作为一个抽象定义的理念
     * 很多的java8API提供了这样的接口静态方法,例如Path.get()
     */
    @Test
    public void interfaceStaticMethod() {
        Collection.sayHello();
    }

    interface Collection {
        static void sayHello() {
            System.out.println("aaaa");
        }
    }

    /**
     * Comparator接口的静态方法
     */
    @Test
    public void compareStaticMethod() {
        Comparator<String> comparing = Comparator.comparing(String::length);
        System.out.println(comparing.compare("aaaa", "bb"));
    }
}
