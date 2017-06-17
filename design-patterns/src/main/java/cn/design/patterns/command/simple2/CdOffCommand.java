package cn.design.patterns.command.simple2;

/**
 * Created by LuoLiBing on 16/6/6.
 */
public class CdOffCommand implements Command {

    private Cd cd;

    public CdOffCommand(Cd cd) {
        this.cd = cd;
    }

    @Override
    public void execute() {
        cd.off();
    }

    @Override
    public void undo() {
        cd.on();
        cd.setCd();
        cd.setVolume(12);
    }
}
