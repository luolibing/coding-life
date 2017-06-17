package cn.tim.phonenumber.generate;

import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.function.Consumer;

/**
 * Created by LuoLiBing on 16/8/25.
 */
public class NumberBookReader {


    public static void readData(Path bookFile, Consumer<Integer> consumer) {
        try(FileReader reader = new FileReader(bookFile.toFile()); BufferedReader br = new BufferedReader(reader)) {
            String line;
            while (!StringUtils.isEmpty(line = br.readLine())) {
                try {
                    consumer.accept(Integer.valueOf(line));
                } catch (Exception e) {
                    // 转换异常
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
