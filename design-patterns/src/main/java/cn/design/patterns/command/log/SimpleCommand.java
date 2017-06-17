package cn.design.patterns.command.log;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

/**
 * Created by LuoLiBing on 16/6/10.
 */
public abstract class SimpleCommand implements Command {

    private String message = "sayHello";

    private final static String objectStorPath = "/share/objectstore/";

    public abstract void execute();

    public abstract void undo();

    public String getMessage() {
        return message;
    }

    @Override
    public void store() {
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(Paths.get(objectStorPath, "command", this.toString()).toFile());
            bos = new BufferedOutputStream(fos);
            oos = new ObjectOutputStream(bos);
            oos.writeObject(this);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void load(Path path) throws IOException {
        if(Files.notExists(path)) {
            throw new FileNotFoundException("文件不存在");
        }
        FileInputStream fileInputStream = new FileInputStream(path.toFile());
        ObjectInputStream oos = new ObjectInputStream(fileInputStream);
        try {
            Command command = (SimpleCommand) oos.readObject();
            System.out.println(command);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
