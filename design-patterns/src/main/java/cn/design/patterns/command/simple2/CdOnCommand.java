package cn.design.patterns.command.simple2;

/**
 * Created by LuoLiBing on 16/6/6.
 */
public class CdOnCommand implements Command {
    private Cd cd;

    public CdOnCommand(Cd cd) {
        this.cd = cd;
    }

    @Override
    public void execute() {
        cd.on();
        cd.setCd();
        cd.setVolume(12);
    }

    @Override
    public void undo() {
        cd.off();
    }
}
