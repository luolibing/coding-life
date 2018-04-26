package cn.tim.thinking.io;

import java.io.*;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.TreeSet;

/**
 * Created by LuoLiBing on 16/12/2.
 * 一个实用的文本操作类
 */
public class TextFileDemo1 extends ArrayList<String> {
    public TextFileDemo1() {
    }

    // 读取
    public static String read(String filename) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String s;
            // 实用了两个try嵌套保证reader会被及时关闭
            try {
                while ((s = reader.readLine()) != null) {
                    sb.append(s);
                    sb.append("\n");
                }
            } finally {
                reader.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }

    // 写入文件
    public static void write(String filename, String text) {
        try {
            PrintWriter writer = new PrintWriter(new File(filename));
            try {
                writer.print(text);
            } finally {
                writer.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public TextFileDemo1(String filename, String splitter) {
        super(Arrays.asList(read(filename).split(splitter)));
        // 如果第一个为空串,去掉第一个空串
        if(Objects.equals(get(0), "")) remove(0);
    }

    public TextFileDemo1(String filename) {
        this(filename, "\n");
    }

    // 将list写入到文件
    public void write(String fileName) {
        try {
            PrintWriter out = new PrintWriter(new File(fileName));
            try {
                for(String item : this) {
                    out.println(item);
                }
            } finally {
                out.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main1(String[] args) {
        // 读取
        String text = read("/share/match.txt");
        System.out.println(text);

        // 写入
        write("/share/test.txt", text);

        TextFileDemo1 textFile = new TextFileDemo1("/share/test.txt");
        // 将list写入到test2.txt中
        textFile.write("/share/test2.txt");

        TreeSet<String> words = new TreeSet<>(new TextFileDemo1("/share/test2.txt", "\\W+"));
        System.out.println(words.headSet("a"));
    }


    public static void main(String[] args) throws IOException {
        rename();
    }

    public static void rename() throws IOException {
        Path path = Paths.get("/Users/luolibing/Documents/github/tim-cloud");
        Files.walk(path, FileVisitOption.FOLLOW_LINKS).parallel().filter(p -> {
            try {
                return Files.isDirectory(p) && !Files.isHidden(p);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }).forEach(p -> {
            if(p.getFileName().toString().equals("tim")) {
                Path basePath = p.getParent();
                p.toFile().renameTo(Paths.get(basePath.toString(), "tim").toFile());
                System.out.println("rename from " + p.toString() + " to tim");
            }
        });
    }
}
