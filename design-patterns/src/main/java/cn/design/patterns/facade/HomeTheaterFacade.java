package cn.design.patterns.facade;

/**
 * Created by LuoLiBing on 16/6/10.
 * 外观类:
 * 使用外观类来操作一系列子系统的组件类
 */
public class HomeTheaterFacade {
    private Amplifier amplifier;
    private Tuner tuner;
    private DvdPlayer dvdPlayer;
    private CdPlayer cdPlayer;
    private Projector projector;
    private TheaterLights lights;
    private Screen screen;
    private PopcornPopper popper;

    public HomeTheaterFacade(Amplifier amplifier, Tuner tuner, DvdPlayer dvdPlayer, CdPlayer cdPlayer, Projector projector, TheaterLights lights, Screen screen, PopcornPopper popper) {
        this.amplifier = amplifier;
        this.tuner = tuner;
        this.dvdPlayer = dvdPlayer;
        this.cdPlayer = cdPlayer;
        this.projector = projector;
        this.lights = lights;
        this.screen = screen;
        this.popper = popper;
    }

    /**
     * 播放电影
     * @param movie
     */
    public void watchMovie(String movie) {
        System.out.println("Get ready to watch a movie...");
        popper.on();
        popper.pop();

        lights.dim(10);
        screen.down();
        projector.on();
        projector.wideScreenMode();

        amplifier.on();
        amplifier.setDvd(dvdPlayer);
        amplifier.setSurroundSound();
        amplifier.setVolume(20);

        dvdPlayer.on();
        dvdPlayer.play(movie);
    }

    /**
     * 停止播放
     */
    public void endMovie() {
        System.out.println("shutting movie theater down");
        popper.off();
        lights.on();

        screen.on();
        projector.off();

        amplifier.off();
        dvdPlayer.stop();
        dvdPlayer.eject();
        dvdPlayer.off();
    }
}
