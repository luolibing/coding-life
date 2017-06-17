package cn.tim.thinking.multiplex;

import org.junit.Test;

/**
 * Created by LuoLiBing on 16/12/13.
 * 代码复用:
 * 1 组合, 新的类由现有的类的对象所组成
 * 2 继承, 继承自已有的父类
 * 3 代理, 继承和组合之间的中庸之道
 *
 * 一个类都是由任意个String,基本数据类型,以及另一个类的对象组成
 *
 *
 */
public class MultiplexDemo1 {


    class WaterSource {
        private String s;

        WaterSource() {
            System.out.println("WaterSource()");
            s = "Constructed";
        }

        @Override
        public String toString() {
            return s;
        }
    }

    /***
     * 组合方式, 一个类包含任意个String, 和基本数据类型, 以及其他类的对象
     *
     * 初始化引用的位置
     * 1 在对象定义的对象
     * 2 在类的构造器中
     * 3 在要使用这些对象之前, 惰性初始化
     * 4 使用实例初始化
     *
     */
    class SprinklerSystem {
        private String val1, val2, val3, val4;

        private WaterSource source = new WaterSource();

        private int i;

        private float f;

        // 每一个非基本类型的对象都有一个toString()方法
        @Override
        public String toString() {
            return "SprinklerSystem{" +
                    "val1='" + val1 + '\'' +
                    ", val2='" + val2 + '\'' +
                    ", val3='" + val3 + '\'' +
                    ", val4='" + val4 + '\'' +
                    ", source=" + source +
                    ", i=" + i +
                    ", f=" + f +
                    '}';
        }
    }


    @Test
    public void sprinklerSystem() {
        SprinklerSystem system = new SprinklerSystem();
        // 自动调用SprinklerSystem.toString()
        System.out.println(system);
    }


    class Soap {
        // 对象初始化
        private String s = "aaa";

        // 实例初始化
        {
            s = "ddd";
        }

        Soap() {
            // 构造函数
            s = "bbb";
        }

        @Override
        public String toString() {
            // 惰性初始化
            if(s == null) {
                s = "ccc";
            }
            return "SprinklerSystem{" +
                    "s='" + s + "'}";
        }
    }


    /**
     * 继承语法
     * 当创建一个类时,如果没有指定继承的父类, 默认从标准根类Object继承.
     *
     *
     *
     */
    static class Cleanser {

        private String s = "Cleanser";

        public void append(String a) {
            // String重载操作符 + +=
            s += a;
        }

        public void dilute() {
            append(" dilute()");
        }

        public void apply() {
            append(" apply()");
        }

        public void scrub() {
            append(" scrub()");
        }

        @Override
        public String toString() {
            return s;
        }

        public static void main(String[] args) {
            Cleanser x = new Cleanser();
            x.dilute(); x.apply(); x.scrub();
            System.out.println(x);
        }
    }


    static class Detergent extends Cleanser {

        // 重载
        @Override
        public void scrub() {
            // 调用继承过来的方法
            append(" Detergent.scrub()");
            super.scrub();
        }

        // 在导出类中定义了独有的一些方法
        public void foam() {
            append(" foam()");
        }

        public static void main(String[] args) {
            Detergent x = new Detergent();
            x.dilute();
            x.apply();
            x.scrub();
            x.foam();
            System.out.println(x);
            System.out.println("Testing base class: ");
            // 这个地方可以调用父类的public static main()方法
            Cleanser.main(args);
        }
    }
}
