package cn.tim.thinking.multiplex;

/**
 * Created by LuoLiBing on 16/12/14.
 * 向上转型
 *
 * 为新类提供方法, 并不是继承中最重要的方面, 其最重要的是用来表现新类和基类之间的关系. 这种关系可以用"新类是现有类的一种类型"
 * 向上转型, 从导出类转型到基类, 从专用类型专项通用类型,所以总是很安全的
 *
 * 慎用继承, 到底使用组合还是继承,一个最清晰的判断办法就是是否需要从新类向基类进行向上转型.如果必须向上转型,则继承是必要的;
 */
public class UpCastDemo {

    static class Instrument {
        public void play() {}

        static void tune(Instrument i) {
            i.play();
        }
    }

    static class Wind extends Instrument {
        public static void main(String[] args) {
            Wind flute = new Wind();
            Instrument.tune(flute); // 自动向上转型
        }
    }

    private void sayHello() {

    }
}
