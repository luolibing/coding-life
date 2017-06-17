package cn.design.patterns.mvc.dj;

/**
 * Created by LuoLiBing on 16/6/25.
 * 控制器,接收DJVIew的请求,调用Model方法,控制器是策略模式中控制策略的地方
 * 控制器拥有一个Model和View对象
 *
 */
public class BeatController implements ControllerInterface {

    BeatModelInterface model;
    DJView djView;

    public BeatController(BeatModelInterface model) {
        this.model = model;
        djView = new DJView(this, model);
        djView.createView();
        djView.createControls();
        djView.disableStopMenuItem();
        djView.enableStartMenuItem();
        model.initialize();
    }

    @Override
    public void start() {
        model.on();
        djView.disableStartMenuItem();
        djView.enableStopMenuItem();
    }

    @Override
    public void stop() {
        model.off();
        djView.enableStartMenuItem();
        djView.disableStopMenuItem();
    }

    @Override
    public void increaseBPM() {
        int bpm = model.getBPM();
        model.setBPM(bpm + 1);
    }

    @Override
    public void decreaseBPM() {
        int bpm = model.getBPM();
        model.setBPM(bpm - 1);
    }

    @Override
    public void setBPM(int bpm) {
        model.setBPM(bpm);
    }
}
