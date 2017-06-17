package cn.tim.phonenumber.generate;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by LuoLiBing on 16/8/24.
 */
public class NumberBookGenerator {

    private Path bookFile = Paths.get("/share/number_book.txt");

    public void initNumberBook() {
        if(Files.notExists(bookFile)) {
            try {
                Files.createFile(bookFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void generateBook() {
        try(FileWriter fileWriter = new FileWriter(bookFile.toFile())) {
            // 894784853
            for(int i = 0; i< 1000000000; i++) {
                fileWriter.append(NumberGenerator.generate() + "\n");
                if(i % 1000 == 0) {
                    fileWriter.flush();
                }
            }
        } catch (Exception e) {

        }
    }
}
