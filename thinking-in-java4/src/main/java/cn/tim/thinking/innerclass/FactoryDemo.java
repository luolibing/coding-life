package cn.tim.thinking.innerclass;

import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by LuoLiBing on 16/12/18.
 */
public class FactoryDemo {

    interface Service {
        void method1();
        void method2();
    }

    interface ServiceFactory {
        Service createService();
    }

    // 构造器都是private,并且没有任何要去创建作为工厂的具名类.
    // 要实例化Implementation1, 它本身自带了factory,代替构造函数, 这样我们要创建它,只需要使用factory创建即可, 并且factory是final static类型的
    // 建议: 优先使用类而不是接口. 如果你的设计中需要某个接口, 你必须了解它. 否则不到迫不得已,不要将其放到你的设计中, 过早的优化设计
    static class Implementation1 implements Service {

        private Implementation1() {}

        @Override
        public void method1() {
            System.out.println("Implementation1.method1()");
        }

        @Override
        public void method2() {
            System.out.println("Implementation1.method2()");
        }

        // factory设置为final static确保了factory单例, 并且不能被改变
        public final static ServiceFactory factory = new ServiceFactory() {
            @Override
            public Service createService() {
                return new Implementation1();
            }
        };
    }


    static class Implementation2 implements Service {

        private Implementation2() {}

        @Override
        public void method1() {
            System.out.println("Implementation2.method1()");
        }

        @Override
        public void method2() {
            System.out.println("Implementation2.method2()");
        }

        public final static ServiceFactory factory = Implementation2::new;
    }

    static class Factories {
        public static void serviceConsumer(ServiceFactory factory) {
            Service service = factory.createService();
            service.method1();
            service.method2();
        }

        public static void main(String[] args) {
            serviceConsumer(Implementation1.factory);
            serviceConsumer(Implementation2.factory);
        }
    }




    // 生成对象接口
    interface Game {
        boolean move();
    }

    // 工厂接口
    interface GameFactory {
        Game getGame();
    }

    static class Checker implements Game {

        private int moves = 0;

        private static final int MOVES = 3;

        private Checker() {}

        @Override
        public boolean move() {
            System.out.println("Checkers move " + moves);
            return ++ moves != MOVES;
        }

        public final static GameFactory factory  = new GameFactory() {
            @Override
            public Game getGame() {
                return new Checker();
            }
        };
    }


    static class Chess implements Game {

        private int moves = 0;

        private final static int MOVES = 4;

        private Chess() {}

        @Override
        public boolean move() {
            System.out.println("Chess move " + moves);
            return ++ moves != MOVES;
        }

        public final static GameFactory factory = new GameFactory() {
            @Override
            public Game getGame() {
                return new Chess();
            }
        };
    }


    static class Games {
        // 传入一个工厂接口
        public static void playGame(GameFactory factory) {
            // 返回的也是一个接口
            Game game = factory.getGame();
            while (game.move()) ;
        }

        public static void main(String[] args) {
            playGame(Chess.factory);
            playGame(Checker.factory);
        }
    }

    @Test
    public void date() {
        LocalDate now = LocalDate.now();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("from = " + df.format(convertToDate(now)));
        System.out.println("to = " + df.format(convertToDate(now.plusYears(1))));
    }

    public static Date convertToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
