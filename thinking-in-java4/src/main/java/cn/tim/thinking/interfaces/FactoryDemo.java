package cn.tim.thinking.interfaces;

/**
 * Created by LuoLiBing on 16/12/16.
 * 生成遵循某个接口的对象典型的方式就是工厂模式
 *
 * 确定接口是理想选择,因而应该总是选择接口而不是具体的类这是一种错误的想法 ,过早的优化设计.
 * 恰当的原则应该是优先选择类而不是接口, 从类开始,如果接口的必需性变得非常明确,那么久进行重构. 接口是一种重要的工具,但是他们容易被滥用.
 */
public class FactoryDemo {

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

        @Override
        public boolean move() {
            System.out.println("Checkers move " + moves);
            return ++ moves != MOVES;
        }
    }

    static class CheckerFactory implements GameFactory {

        @Override
        public Game getGame() {
            return new Checker();
        }
    }


    static class Chess implements Game {

        private int moves = 0;

        private final static int MOVES = 4;

        @Override
        public boolean move() {
            System.out.println("Chess move " + moves);
            return ++ moves != MOVES;
        }
    }

    static class ChessFactory implements GameFactory {

        @Override
        public Game getGame() {
            return new Chess();
        }
    }

    static class Games {
        // 传入一个工厂接口
        public static void playGame(GameFactory factory) {
            // 返回的也是一个接口
            Game game = factory.getGame();
            while (game.move()) ;
        }

        public static void main(String[] args) {
            playGame(new ChessFactory());
            playGame(new CheckerFactory());
        }
    }

    static class MessageHolder {
        enum Message {
            MSG1("发放%s"),
            MSG2("fagg%s");

            private String template;


            Message(String s) {
                this.template = s;
            }

            public String getMessage() {
                return template;
            }
        }
    }

    public static void main(String[] args) {
        MessageHolder holder = new MessageHolder() {
            @Override
            protected Object clone() throws CloneNotSupportedException {
                return super.clone();
            }
        };
    }

}
