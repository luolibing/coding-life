package cn.design.patterns.command.log;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * Created by LuoLiBing on 16/6/10.
 * 日志请求,在command上添加了store和load方法,序列化到持久化设备和反序列化重新执行
 * 命令不是直接执行,而是先持久化,然后再load出来进行执行,这样可以在死机的时候能够恢复命令并且继续执行
 */
public class LogTester {

    public static void main(String[] args) throws IOException {
        // store
//        Command command = new GoCommand();
//        command.store();

        Command loadCommand = new GoCommand();
        loadCommand.load(Paths.get("/share/objectstore/command/cn.design.patterns.command.log.GoCommand@61064425"));
        loadCommand.execute();
    }
}
