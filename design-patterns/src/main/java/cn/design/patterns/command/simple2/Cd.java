package cn.design.patterns.command.simple2;

/**
 * Created by LuoLiBing on 16/6/6.
 */
public class Cd {

    private int volume;

    public void on() {
        System.out.println("cd is on");
    }

    public void off() {
        System.out.println("cd is off");
    }

    public void setCd() {
        System.out.println("include cd!!!");
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }
}
