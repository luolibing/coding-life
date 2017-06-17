package cn.design.patterns.mvc.dj;

/**
 * Created by LuoLiBing on 16/6/25.
 *
 */
public class DJViewDrive {

    public static void main(String[] args) {
        BeatModelInterface model = new BeatModel();
        ControllerInterface controller = new BeatController(model);
    }
}
