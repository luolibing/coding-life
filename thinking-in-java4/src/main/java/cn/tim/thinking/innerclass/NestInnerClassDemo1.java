package cn.tim.thinking.innerclass;

/**
 * Created by LuoLiBing on 16/12/19.
 * 嵌套类
 */
public class NestInnerClassDemo1 {

    interface Destination {
        String readLabel();
    }

    interface Contents {
        int value();
    }

    /**
     * 嵌套类: 如果不需要内部类对象与其外围类对象之间有联系, 可以将内部类声明为static, 这就是嵌套类.
     * static内部类特性:
     * 1 要创建嵌套类的对象,并不需要其外围类的对象. 当使用outerObj.new InnerClass();外部对象创建内部对象时, 编译器会报错
     * 2 不能从嵌套类的对象中访问非静态的外围类对象
     *
     * 普通内部类不能有静态方法, 如果有静态字段或方法, 则内部类必须为嵌套类
     *
     */
    private static class ParcelContents implements Contents {
        private int i = 11;

        @Override
        public int value() {
            // NestInnerClassDemo1.this.f(); 嵌套类内部无法访问外部类对象, 因为嵌套类内部没有保持外部类对象的引用了, 当然也无法访问外部类的非静态对象. 嵌套类就类似于一个static方法.
            NestInnerClassDemo1.contents(); // 嵌套类可以访问外部类的静态方法
            return i;
        }
    }

    public static class ParcelDestination implements Destination {

        private String label;

        public ParcelDestination(String whereTo) {
            this.label = whereTo;
        }

        @Override
        public String readLabel() {
            return label;
        }

        public static void f() {}

        static int x = 10;

        static class AnotherLevel {
            public static void f() {}
            static int x = 10;
        }
    }

    public void f() {}

    public static Destination destination(String s) {
        return new ParcelDestination(s);
    }

    public static Contents contents() {
        return new ParcelContents();
    }

    public static void main(String[] args) {
        Contents c = contents();
        Destination d = destination("Tasmania");
        NestInnerClassDemo1 n = new NestInnerClassDemo1();
        // n.new ParcelDestination("aaa");
        ParcelDestination d1 = new ParcelDestination("aaa");
    }
}
