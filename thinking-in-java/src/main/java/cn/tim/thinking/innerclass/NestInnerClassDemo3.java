package cn.tim.thinking.innerclass;

/**
 * Created by LuoLiBing on 16/12/19.
 */
public class NestInnerClassDemo3 {

    // 第一个内部类
    private static class Inner {
        public void f() {
            System.out.println("Inner.f()");
        }

        // NestInnerClassDemo3$Inner$Inner2.class 内部类class文件名
        private class Inner1 {
            public void f1() {
                System.out.println("Inner1.f1()");
            }
        }

        private static class Inner2 {
            public void f2() {
                System.out.println("Inner2.f2()");
            }
        }
    }

    public static void main(String[] args) {
        Inner inner = new Inner();
        inner.f();

        Inner.Inner1 inner1 = inner.new Inner1();
        inner1.f1();

        Inner.Inner2 inner2 = new Inner.Inner2();
        inner2.f2();
    }

}
