package cn.tim.thinking.interfaces;

import org.junit.Test;

import java.io.IOException;
import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by LuoLiBing on 16/12/16.
 * interface是一个完全抽象的类. 接口允许多个实现,从而实现类似多重继承的变种特性.
 * interface也可以包含域,但是隐式地是final和static的. 接口中所有方法都是public, 即使不显示声明为public, 默认就是public
 *
 * 接口相对于继承来说实现了完全解耦, 类可以实现多个接口, 意味着类可以向上转型为多种类型的接口
 *
 */
public class InterfacesDemo {

    interface A1 {
        void say();
    }

    interface B {
        void say();
    }

    class C implements A1, B {

        @Override
        public void say() {

        }
    }


    /**
     * 策略模式: 创建一个能够根据所传递的参数对象的不同而具有不同行为的方法, 称为策略模式
     *
     */
    static class Processor {
        public String name() {
            return getClass().getSimpleName();
        }

        Object process(Object input) {
            return input;
        }
    }

    static class Upcase extends Processor {
        @Override
        Object process(Object input) {
            return ((String) input).toUpperCase();
        }
    }

    static class DownCase extends Processor {
        @Override
        Object process(Object input) {
            return ((String) input).toLowerCase();
        }
    }

    static class Splitter extends Processor {
        @Override
        String process(Object input) {
            return Arrays.toString(((String) input).split(" "));
        }
    }

    static class Apply {
        // 可以接收任何类型的Processor, 包含所要执行的算法中固定不变的部分, 而策略包含了变化的部分. 策略就是传递进去的参数对象, 它包含所要执行的代码.
        public static void process(Processor p, Object input) {
            System.out.println("Using Processor " + p.name());
            System.out.println(p.process(input));
        }

        public static String s = "Disagreement with beliefs is by definition incorrect";

        public static void main(String[] args) {
            process(new Upcase(), s);
            process(new DownCase(), s);
            process(new Splitter(), s);
        }
    }


    static class Waveform {
        private static long counter = 0;

        private final long id = counter ++;

        @Override
        public String toString() {
            return "Waveform " + id;
        }
    }

    class Filter implements ProcessorX {
        public String name() {
            return getClass().getSimpleName();
        }

        @Override
        public Object process(Object input) {
            return null;
        }

        public Waveform process(Waveform input) {
            return input;
        }
    }

    class LowPass extends Filter {
        double cutoff;
        public LowPass(double cutoff) {
            this.cutoff = cutoff;
        }

        @Override
        public Waveform process(Waveform input) {
            return input;
        }
    }

    class HighPass extends Filter {
        double cutoff;
        public HighPass(double cutoff) {
            this.cutoff = cutoff;
        }

        @Override
        public Waveform process(Waveform input) {
            return input;
        }
    }


    interface ProcessorX {
        String name();

        Object process(Object input);
    }


    // 适配器
    class FilterAdapter implements ProcessorX {

        Filter filter;

        public FilterAdapter(Filter filter) {
            this.filter = filter;
        }

        @Override
        public String name() {
            return filter.name();
        }

        @Override
        public Object process(Object input) {
            return filter.process(input);
        }
    }


    /***
     * java中的多重继承
     * 接口可以间接实现多重继承
     */
    interface CanFight {
        void fight();
    }

    interface CanSwim {
        void swim();
    }

    interface CanFly {
        void fly();
    }

    class ActionCharacter {
        public void fight() {

        }
    }

    // 继承的类里面也有fight()方法, 接口中也有对应的方法
    class Hero extends ActionCharacter implements CanFight, CanSwim, CanFly {

        @Override
        public void fly() {

        }

        @Override
        public void swim() {

        }
    }

    static class Adventure {
        static void t(CanFight x) {
            x.fight();
        }

        static void u(CanSwim x) {
            x.swim();
        }

        static void v(CanFly x) {
            x.fly();
        }

        static void w(ActionCharacter x) {
            x.fight();
        }
    }

    @Test
    public void adventure() {
        Hero hero = new Hero();
        Adventure.t(hero);
        Adventure.u(hero);
        Adventure.v(hero);
        Adventure.w(hero);
    }


    /**
     * 通过继承扩展接口
     *
     * 通过继承, 可以很容易地在接口中添加新的方法声明, 还可以通过继承在新接口中组合数个接口.
     * 在使用实现组合接口时,使用相同的方法名通常会产生代码可读性的混乱, 尽量避免这种情况.
     */
    interface Monster {
        void menace();
    }

    interface DangerousMonster extends Monster {
        void destory();
    }

    interface Lethal {
        void kill();
    }

    class DragonZilla implements DangerousMonster {

        @Override
        public void destory() {

        }

        @Override
        public void menace() {

        }
    }

    interface Vampire extends DangerousMonster, Lethal {
        void drinkBlood();
    }

    class VeryBadVampire implements Vampire {

        @Override
        public void drinkBlood() {

        }

        @Override
        public void destory() {

        }

        @Override
        public void kill() {

        }

        @Override
        public void menace() {

        }
    }

    class HorrorShow {
        void u(Monster b) {
            b.menace();
        }

        void v(DangerousMonster d) {
            d.menace();
            d.destory();
        }

        void w(Lethal l) {
            l.kill();
        }
    }

    @Test
    public void horrShow() {
        HorrorShow h = new HorrorShow();
        DangerousMonster barney = new DragonZilla();
        h.u(barney);
        h.v(barney);
        VeryBadVampire vlad = new VeryBadVampire();
        h.u(vlad);
        h.v(vlad);
        h.w(vlad);
    }


    /**
     * 适配接口
     * 接口最吸引人的原因之一就是允许同一个接口具有多个不同的具体实现.
     * 通常的体现形式是一个接收接口类型的方法, 而该接口的实现和向该方法传递的对象取决于方法的使用者.
     * 接口的一种常见用法就是前面提到的策略设计模式
     */
    static class RandomWords implements Readable {

        private static Random rand = new Random(47);

        private static final char[] capitals = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

        private static final char[] lowers = "abcdefghijklmnopqrstuvwxyz".toCharArray();

        private final static char[] vowels = "aeiou".toCharArray();

        private int count;

        public RandomWords(int count) {
            this.count = count;
        }

        @Override
        public int read(CharBuffer cb) throws IOException {
            if(count -- == 0) {
                return -1;
            }
            cb.append(capitals[rand.nextInt(capitals.length)]);
            for(int i = 0; i < 4; i++) {
                cb.append(vowels[rand.nextInt(vowels.length)]);
                cb.append(lowers[rand.nextInt(lowers.length)]);
            }
            cb.append(" ");
            return 10;
        }

        public static void main(String[] args) {
            // Scanner只要求实现了Readable接口,  read()方法, read方法会传入charBuffer, 让其写入数据
            Scanner s = new Scanner(new RandomWords(10));
            while (s.hasNext()) {
                System.out.println(s.next());
            }
        }
    }

    // 如果没有实现Readable接口, 可以通过接口适配器进行适配
    public static class RandomDoubles {
        private static Random rand = new Random(47);

        public double next() {
            return rand.nextDouble();
        }

        public static void main(String[] args) {
            RandomDoubles rd = new RandomDoubles();
            for(int i = 0; i < 10; i++ ) {
                System.out.print(rd.next() + " ");
            }
        }
    }

    // 通过继承和实现接口, 间接实现伪多重继承机制, 也不需要再传入RandomDoubles类了
    static class AdaptedRandomDoubles extends RandomDoubles implements Readable {

        private int count;

        public AdaptedRandomDoubles(int count) {
            this.count = count;
        }

        @Override
        public int read(CharBuffer cb) throws IOException {
            if(--count == 0) {
                return -1;
            }
            String result = Double.toString(next()) + " ";
            cb.append(result);
            return result.length();
        }

        public static void main(String[] args) {
            Scanner s = new Scanner(new AdaptedRandomDoubles(10));
            while (s.hasNext()) {
                System.out.print(s.nextDouble() + " ");
            }
        }
    }


    /**
     * 接口中的域: 接口中的域都是final static的, 所以通常用来作为创建常量的工具
     * 接口中的域在类第一次被加载时初始化, 这发生在任何域首次被访问时
     */
    interface Months {
        int JANUARY = 1, FEBRUARY = 2;
    }

    class MonthImpl implements Months {
        public void change() {
            // JANUARY = 2; final static类型无法修改
        }
    }

    static class Ut {
        static int getIntRandom() {
            System.out.println("ut.getIntRandom");
            return 10;
        }

        static long getLongRandom() {
            System.out.println("ut.getLongRandom");
            return 10;
        }
    }


    public interface RandVals {

        // 编译器常量同样不会导致类初始化
        int size = 10;

        int RANDOM_INT = Ut.getIntRandom();

        long RANDOM_LONG = Ut.getLongRandom();
    }

    @Test
    public void randVals() {
        System.out.println(RandVals.size);
        System.out.println(RandVals.RANDOM_INT);
        System.out.println(RandVals.RANDOM_LONG);
    }


    /**
     * 嵌套接口
     * 接口可以嵌套在类或其他接口中
     * private的接口
     * 1 可以强制该接口中的方法定义不要添加任何类型信息, 不能向上转型.
     * 2 private接口不能在定义它的类之外被实现.
     */
    public static class A {
        interface B {
           void f();
        }

        public class BImp implements B {

            @Override
            public void f() {

            }
        }

        private class BImp2 implements B {

            @Override
            public void f() {

            }
        }

        interface C {
            void f();
        }

        class CImp implements C {

            @Override
            public void f() {

            }
        }

        private class CImp2 implements C {

            @Override
            public void f() {

            }
        }


        private interface D {
            public void f();
        }

        // 私有的接口
        private class DImp implements D {

            @Override
            public void f() {

            }
        }

        public class DImp2 implements D {

            @Override
            public void f() {

            }
        }

        public D getD() {
            return new DImp2();
        }

        private D dRef;

        public void receiveD(D d) {
            dRef = d;
            dRef.f();
        }
    }


    interface E {
        interface G {
            void f();
        }

        public interface H {
            void f();
        }

        void g();

        // private interface I {}  无法在接口内部嵌套私有的接口
    }

    public class NestingInterfaces {
        public class BImp implements A.B {

            @Override
            public void f() {

            }
        }

        class CImp implements A.C {

            @Override
            public void f() {

            }
        }

        class DImp implements A.D {

            @Override
            public void f() {

            }
        }
    }

}
