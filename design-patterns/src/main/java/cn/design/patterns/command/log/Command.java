package cn.design.patterns.command.log;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Path;

/**
 * Created by LuoLiBing on 16/6/10.
 */
public interface Command extends Serializable {

    void execute();
    void undo();
    void store();
    void load(Path path) throws IOException;
}
