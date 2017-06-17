package cn.design.patterns.facade;

/**
 * Created by LuoLiBing on 16/6/10.
 */
public class Projector {

    public void on() {
        System.out.println("popcornPopper on");
    }

    public void wideScreenMode() {
        System.out.println("wideScreenMode 模式");
    }

    public void off() {
        System.out.println("popcornPopper off");
    }
}
