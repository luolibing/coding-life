package cn.tim.thinking.clazz14.interfaces;

/**
 * Created by LuoLiBing on 16/10/8.
 */
public class InterfaceDemo {

    public interface A{
        void f();
    }

    static class B implements A {

        @Override
        public void f() {

        }

        public void g() {}
    }

    public static void main(String[] args) {
        A a = new B();
        a.f();

        System.out.println(a.getClass().getName());
        // 判断类型,向下转型
        if(a instanceof B) {
            ((B) a).g();
        }
    }

    static class C implements A {

        @Override
        public void f() {
            System.out.println("public C.f()");
        }

        public void g() {
            System.out.println("public C.g()");
        }

        void u() {
            System.out.println("package C.u()");
        }

        protected void v() {
            System.out.println("protected C.v()");
        }

        private void w() {
            System.out.println("private C.w()");
        }
    }

    public static A makeA() {
        return new C();
    }
}
