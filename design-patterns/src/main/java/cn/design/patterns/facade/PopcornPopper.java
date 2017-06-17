package cn.design.patterns.facade;

/**
 * Created by LuoLiBing on 16/6/10.
 */
public class PopcornPopper {
    public void on() {
        System.out.println("popcornPopper on");
    }

    public void pop() {
        System.out.println("PopcornPopper 弹出");
    }

    public void off() {
        System.out.println("PopcornPopper 关闭");
    }
}
