package cn.tim.thinking.innerclass;

/**
 * Created by LuoLiBing on 16/12/19.
 */
public class NestInnerClassDemo2 {

    private static class Inner1 {

    }

    private class Inner2 {

    }

    public static void main(String[] args) {
        // 嵌套类, 不需要使用外部类对象来初始化内部类对象
        Inner1 inner = new Inner1();
        NestInnerClassDemo2 x = new NestInnerClassDemo2();
        // 普通内部类需要外部类对象
        Inner2 inner2 = x.new Inner2();
    }
}
