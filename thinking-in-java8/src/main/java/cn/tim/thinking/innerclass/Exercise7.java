package cn.tim.thinking.innerclass;

/**
 * Created by LuoLiBing on 16/12/18.
 */
public class Exercise7 {

    private String name = "luo";

    private void f() {
        System.out.println("Exercise.f()");
    }

    class Inner {
        public void change(String n) {
            System.out.println("change name from " + name + " to " + n);
            name = n;
            f();
        }
    }

    public Inner inner() {
        Inner inner = new Inner();
        inner.change("liuxiaoling");
        return inner;
    }

    public Inner inner1() {
        return new Inner() {
            @Override
            public void change(String n) {
                System.out.println("Inner1 change name from " + name + " to " + n);
            }
        };
    }
}
