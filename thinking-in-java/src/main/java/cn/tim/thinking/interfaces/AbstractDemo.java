package cn.tim.thinking.interfaces;

import org.junit.Test;

/**
 * Created by LuoLiBing on 16/12/16.
 * 接口和内部类为我们提供了一种将接口和实现分离的更加结构化的方法
 * 抽象类是普通类与接口之间的一种中庸之道, 如果一个类有一个或者多个抽象方法,那么这个类必定是抽象类. 反之抽象类可以没有抽象方法.
 * 抽象类只能被继承,不能被实例化.
 */
public class AbstractDemo {

    enum Note {
        MIDDLE_C, C_SHAPE, B_FLAT
    }

    abstract class Instrument {
        private int i;

        // 抽象方法
        public abstract void play(Note note);

        // 实例方法
        public String what() {
            return "Instrument";
        }

        public abstract void adjust();
    }

    class Wind extends Instrument {

        @Override
        public void play(Note note) {
            System.out.println("Wind.play() " + note);
        }

        @Override
        public String what() {
            return "Wind";
        }

        @Override
        public void adjust() {

        }
    }

    class Percussion extends Instrument {

        @Override
        public void play(Note note) {
            System.out.println("Percussion.play() " + note);
        }

        @Override
        public String what() {
            return "Percussion";
        }

        @Override
        public void adjust() {

        }
    }

    class Stringed extends Instrument {

        @Override
        public void play(Note note) {
            System.out.println("Stringed.play() " + note);
        }

        @Override
        public String what() {
            return "Stringed";
        }

        @Override
        public void adjust() {

        }
    }

    static class Music4 {
        static void tune(Instrument i) {
            i.play(Note.B_FLAT);
        }
    }

    @Test
    public void music() {
        Music4.tune(new Stringed());
    }

    abstract class A {
        public void a() {}
        public void b() {}
    }

    @Test
    public void a() {
    }
}
