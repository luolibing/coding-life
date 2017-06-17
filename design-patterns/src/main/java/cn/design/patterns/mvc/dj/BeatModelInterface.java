package cn.design.patterns.mvc.dj;

/**
 * Created by LuoLiBing on 16/6/25.
 */
public interface BeatModelInterface {

    void initialize();

    void on();

    void off();

    void setBPM(int bpm);

    int getBPM();

    void registerObserver(BeatObserver observer);

    void removeObserver(BeatObserver observer);

    void registerObserver(BPMObserver observer);

    void removeObserver(BPMObserver observer);
}
