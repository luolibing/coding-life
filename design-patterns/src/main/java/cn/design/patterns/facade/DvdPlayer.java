package cn.design.patterns.facade;

/**
 * Created by LuoLiBing on 16/6/10.
 */
public class DvdPlayer {

    private boolean sound;

    private int volume;

    public void setSurroundSound() {
        sound = true;
        System.out.println("turn sound");
    }

    public void setVolume(int volume) {
        this.volume = volume;
        System.out.println("turn volume " + volume );
    }

    public void on() {
        System.out.println("dvdPlayer on");
    }

    public void play(String movie) {
        System.out.println("start play " + movie);
    }

    public void stop() {
        System.out.println("Dvdplayer stop");
    }

    public void eject() {
        System.out.println("Dvdplayer eject");
    }

    public void off() {
        System.out.println("Dvdplayer off");
    }
}
