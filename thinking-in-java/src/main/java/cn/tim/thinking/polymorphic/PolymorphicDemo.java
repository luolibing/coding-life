package cn.tim.thinking.polymorphic;

/**
 * Created by LuoLiBing on 16/12/15.
 * 面向对象三大特性: 抽象,继承和多态
 * 多态通过分离做什么和怎么做, 从另一个角度讲接口和实现分离开来.
 * 封装通过合并特性和行为来创建新的数据类型. 实现隐藏则通过将细节私有化把接口和实现分离开来.
 * 多态的作用则是消除类型之间的耦合关系.
 *
 */
public class PolymorphicDemo {

    enum Note {
        MIDDLE_C, C_SHAPE, B_FLAT
    }

    static class Instrument {
        public void play(Note n) {
            System.out.println("Instrument.play()");
        }
    }

    static class Wind extends Instrument {
        public void play(Note n) {
            System.out.println("Wind.play() " + n);
        }
    }

    static class Music {
        public static void tune(Instrument i) {
            i.play(Note.MIDDLE_C);
        }

        public static void main(String[] args) {
            Wind flute = new Wind();
            Music.tune(flute);
            // tune方法参数本来只接收Instrument类型, 这个地方wind向上转型了.
            // Wind向上转型到Instrument可能会缩小接口. 但不会比Instrument的全部接口更窄.
            // 如果不使用向上转型,任何给Instrument新增的类型都得添加添加一套方法.
            // 这样我们就可以针对接口编写代码,而不是针对实现编写代码.
        }
    }


    static class Cycle {

    }

    static class Unicycle extends Cycle {

    }

    class Bicycle extends Cycle {

    }

    class Tricycle extends Cycle {

    }

    static class Cycles {
        static void ride(Cycle c) {

        }

        public static void main(String[] args) {
            Unicycle cycle = new Unicycle();
            Cycles.ride(cycle);
        }
    }
}
