package cn.tim.java8.lambda;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * Created by LuoLiBing on 16/7/4.
 */
public interface Exercise {

    default List<String> getWordsAsList() {
        String content;
        try {
            content = new String(Files.readAllBytes(Paths.get(this.getClass().getResource("/alice.txt").toURI())), StandardCharsets.UTF_8);
            return Arrays.asList(content.split("[\\P{L}]+"));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    default String[] getWordsAsArray() {
        String content;
        try {
            content = new String(Files.readAllBytes(Paths.get(this.getClass().getResource("/alice.txt").toURI())), StandardCharsets.UTF_8);
            return content.split("[\\P{L}]+");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
