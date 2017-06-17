package cn.design.patterns.mvc.dj;

/**
 * Created by LuoLiBing on 16/6/25.
 */
public interface ControllerInterface {

    void start();
    void stop();
    void increaseBPM();
    void decreaseBPM();
    void setBPM(int bpm);
}
