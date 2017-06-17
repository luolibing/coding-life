package cn.design.patterns.facade;

/**
 * Created by LuoLiBing on 16/6/10.
 */
public class TheaterLights {

    private int level;

    public void dim(int d) {
        this.level = d;
        System.out.println("turn " + this.level);
    }

    public void on() {
        System.out.println("theaterLights on");
    }
}
