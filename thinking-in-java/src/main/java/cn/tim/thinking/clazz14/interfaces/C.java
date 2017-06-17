package cn.tim.thinking.clazz14.interfaces;

/**
 * Created by LuoLiBing on 16/10/8.
 */
class C implements InterfaceDemo.A {

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