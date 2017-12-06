package cn.tim.thinking.clazz14.transaction;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Random;
import java.util.UUID;

/**
 * Created by LuoLiBing on 16/10/8.
 */
public class FileHandler implements Handler {

    private Path file;

    private Path tempFile;

    public FileHandler(Path file) {
        this.file = file;
    }

    /**
     * 开启事务的时候,写在另外一个临时文件,当需要提交的时候,替换文件的方式
     * @param str
     * @throws IOException
     */
    @Override
    public void append(String str) throws IOException {
        tempFile = Files.createTempFile(UUID.randomUUID().toString(), ".txt");
        Files.copy(file, tempFile, StandardCopyOption.REPLACE_EXISTING);
        try(FileWriter fw = new FileWriter(tempFile.toFile(), true)) {
            fw.append(str).append("\n");
            randomException();
        }
    }


    @Override
    public void commit() throws IOException {
        Files.move(tempFile, file, StandardCopyOption.REPLACE_EXISTING);
    }

    private void randomException() {
        int i = new Random().nextInt(2);
        if(i == 1) {
            throw new RuntimeException();
        }
    }
}
