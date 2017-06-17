package cn.tim.phonenumber.simple;

import cn.tim.phonenumber.generate.NumberBookReader;
import cn.tim.phonenumber.generate.NumberBookWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by LuoLiBing on 16/8/25.
 */
public class PhoneNumberResolver {

    private final static String basePath = "/share/phonenumber/";

    private static Path bookFile = Paths.get("/share/number_book.txt");

    private final static FileWriter[] fileWriterArr = new FileWriter[100];

    static  {
        if(Files.notExists(Paths.get(basePath))) {
            try {
                Files.createDirectory(Paths.get(basePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for(int i = 0; i< 100; i++) {
            Path numberBook = Paths.get(basePath, i + ".txt");
            if(Files.notExists(numberBook)) {
                try {
                    Files.createFile(numberBook);
                    fileWriterArr[i] = new FileWriter(numberBook.toFile());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void resolve() {
        NumberBookReader.readData(bookFile, i -> {
            try {
                NumberBookWriter.writeNumber(i, fileWriterArr[i % 100]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static void release() {
        for(FileWriter fileWriter : fileWriterArr) {
            try {
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
            }
        }
    }
}
