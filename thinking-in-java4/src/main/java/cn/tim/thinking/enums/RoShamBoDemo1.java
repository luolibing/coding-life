package cn.tim.thinking.enums;

import java.util.Random;

/**
 * Created by LuoLiBing on 16/12/13.
 * 接口多路分发
 */
public class RoShamBoDemo1 {

    enum OutCome { WIN, LOSE, DRAW }

    interface Item {
        // it 和 this都是未知类型
        OutCome compete(Item it);

        // 任意两种类型比较的结果
        OutCome eval(Paper p);

        OutCome eval(Scissors s);

        OutCome eval(Rock r);
    }

    static class Paper implements Item {

        // 第一次分发确定it的类型
        @Override
        public OutCome compete(Item it) {
            // 第二次分发把this作为参数调用eval(), 这样就可以确定第二个未知类型了
            return it.eval(this);
        }

        @Override
        public OutCome eval(Paper p) {
            return OutCome.DRAW;
        }

        @Override
        public OutCome eval(Scissors s) {
            return OutCome.LOSE;
        }

        @Override
        public OutCome eval(Rock r) {
            return OutCome.WIN;
        }

        @Override
        public String toString() {
            return "Paper";
        }
    }

    static class Scissors implements Item {

        @Override
        public OutCome compete(Item it) {
            return it.eval(this);
        }

        @Override
        public OutCome eval(Paper p) {
            return OutCome.WIN;
        }

        @Override
        public OutCome eval(Scissors s) {
            return OutCome.DRAW;
        }

        @Override
        public OutCome eval(Rock r) {
            return OutCome.LOSE;
        }

        @Override
        public String toString() {
            return "Scissors";
        }
    }

    static class Rock implements Item {

        @Override
        public OutCome compete(Item it) {
            return it.eval(this);
        }

        @Override
        public OutCome eval(Paper p) {
            return OutCome.LOSE;
        }

        @Override
        public OutCome eval(Scissors s) {
            return OutCome.WIN;
        }

        @Override
        public OutCome eval(Rock r) {
            return OutCome.DRAW;
        }

        @Override
        public String toString() {
            return "Rock";
        }

        public static class RoShabo1 {
            final static int SIZE = 20;

            private static Random rand = new Random();

            public static Item newItem() {
                switch (rand.nextInt(3)) {
                    default:
                    case 0: return new Scissors();
                    case 1: return new Paper();
                    case 2: return new Rock();
                }
            }

            public static void match(Item a, Item b) {
                System.out.println(a + " vs. " + b + ":" + a.compete(b));
            }

            public static void main(String[] args) {
                for(int i = 0; i < 20; i++) {
                    match(newItem(), newItem());
                }
            }
        }
    }
}
