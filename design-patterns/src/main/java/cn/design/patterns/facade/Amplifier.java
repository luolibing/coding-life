package cn.design.patterns.facade;

/**
 * Created by LuoLiBing on 16/6/10.
 */
public class Amplifier {

    private DvdPlayer dvdPlayer;

    public void on() {
        System.out.println("Amplifier on");
    }

    public void setDvd(DvdPlayer dvdPlayer) {
        this.dvdPlayer = dvdPlayer;
        System.out.println("设置dvd");
    }

    public void setSurroundSound() {
        dvdPlayer.setSurroundSound();
    }

    public void setVolume(int volume) {
        dvdPlayer.setVolume(volume);
    }

    public void off() {

    }
}
