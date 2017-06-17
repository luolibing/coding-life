package cn.tim.java8.java7;

import edu.emory.mathcs.backport.java.util.Collections;
import org.junit.Test;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * Created by LuoLiBing on 16/7/27.
 */
public class Java7Test {

    /**
     * Objects.equals(obj1, obj2)
     * equals不需要再担心抛出空指针异常了
     */
    @Test
    public void equalsTest() {
        final boolean equals = Objects.equals("aaa", "bb");
        System.out.println(equals);
    }

    /**
     * Objects.hashCode(null)==0
     * 添加了静态方法hashCode,null的hashCode为0,也可以避免产生空指针异常
     * Objects.hash(objs...) 计算一个集合的hashCode,调用的是Arrays.hashCode(array)
     * Objects.toString(null, "default null value")
     */
    @Test
    public void hashCodeTest() {
        System.out.println(Objects.hash(null));
        System.out.println(Objects.hash("aaa", "bbb"));
        System.out.println(Objects.toString(null, "null value"));
    }

    @Test
    public void comparator() {
        System.out.println(Integer.compare(1, 2));
    }

    @Test
    public void test1() {
        final double v = Double.parseDouble("+1.0");
        System.out.println(v);
    }

    /**
     * 全局日志消息
     */
    @Test
    public void logger() {
        Logger.getGlobal().info("luolibing");
    }

    /**
     * ProcessBuilder(command...)
     * 标准输入,标准输出,标准错误输出
     * builder.inheritIO()输出到控制台
     * @throws IOException
     * @throws InterruptedException
     */
    @Test
    public void processBuilder() throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder("ls", "/share");
        // builder.redirectInput(Paths.get("/share").toFile());
        //builder.redirectOutput(Paths.get("/share/output.log").toFile());
        // 删除到System.out中
        builder.inheritIO();
        Process process = builder.start();
        process.waitFor();
    }

    /**
     * URLClassLoader类加载器,URLClassLoader加载jar包,一般不要在Loader关闭之后还使用对应包里面的类,容易出现加载失败的情况
     * @throws MalformedURLException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    @Test
    public void urlClassLoader() throws MalformedURLException, IllegalAccessException, InstantiationException {
        URL[] urls = {
                new URL("file:junit-4.11.jar")
        };
        Class<?> clazz = null;
        try( URLClassLoader loader = new URLClassLoader(urls)) {
            clazz = loader.loadClass("org.junit.runner.JUnitCore");
            System.out.println(clazz.getName());
        } catch (Exception e) {

        }
        if(clazz != null) {
            final Object o = clazz.newInstance();
            System.out.println(o);
        }
    }

    @Test
    public void tryCatchFinally() throws URISyntaxException, IOException {
        Scanner in = null;
        PrintWriter out = null;
        try {
            in = new Scanner(Paths.get(this.getClass().getResource("/alice.txt").toURI()));
            try {
                out = new PrintWriter("/fake/alice.txt");
                while (in.hasNext()) out.println(in.next().toLowerCase());
            } finally {
                if (out != null) {
                    out.close();
                }
            }
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }

    @Test
    public void test5() throws URISyntaxException, IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(this.getClass().getResource("/alice.txt").toURI()));
        byte[] reverseBytes = new byte[bytes.length];
        for(int i=0, j = bytes.length-1; i < bytes.length; i++, j--) {
            reverseBytes[j] = bytes[i];
        }
        Path tmpPath = Paths.get(System.getProperty("java.io.tmpdir"), "alice.txt");
        System.out.println(tmpPath);
        Files.write(tmpPath, reverseBytes);
    }

    @Test
    public void test6() throws URISyntaxException, IOException {
        List<String> lines = Files.readAllLines(Paths.get(this.getClass().getResource("/alice.txt").toURI()));
        Collections.reverse(lines);
        Path tmpPath = Paths.get(System.getProperty("java.io.tmpdir"), "alice.txt");
        System.out.println(tmpPath);
        Files.write(tmpPath, lines);
    }

    /**
     * url.openConnection()获取一个连接
     * @throws IOException
     */
    @Test
    public void urlTest1() throws IOException {
        URL url = new URL("http", "www.baidu.com", 80, "/");
        URLConnection connection = url.openConnection();
        connection.connect();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            System.out.println(reader.readLine());
        }
    }

    /**
     * url.openStream()获取InputStream
     * @throws IOException
     */
    @Test
    public void urlTest2() throws IOException {
        URL url = new URL("http", "www.baidu.com", 80, "/");
        Path tmpPath = Paths.get(System.getProperty("java.io.tmpdir"), "index.html");
        System.out.println(tmpPath);
        try(InputStream in = url.openStream()) {
            Files.copy(in, tmpPath);
        }
    }

    @Test
    public void objects() {
        LabeledPoint a = new LabeledPoint(null, 1, 4);
        LabeledPoint b = new LabeledPoint(null, 1, 4);
        System.out.println(Objects.equals(a, b));
        System.out.println(a.compareTo(b));
    }

    class LabeledPoint implements Comparable<LabeledPoint> {
        String label;
        int x;
        int y;

        public LabeledPoint(String label, int x, int y) {
            this.label = label;
            this.x = x;
            this.y = y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(label, x, y);
        }

        @Override
        public boolean equals(Object other) {
            if(this == other) return true;
            if(other == null) return false;
            if(getClass() != other.getClass()) return false;
            LabeledPoint otherPoint = (LabeledPoint) other;
            return Objects.equals(this.label, otherPoint.label)  && Objects.equals(this.x, otherPoint.x) && Objects.equals(this.y, otherPoint.y);
        }

        @Override
        public int compareTo(LabeledPoint other) {
            int diff = Integer.compare(x, other.x);
            if(diff != 0) return diff;
            diff = Integer.compare(y, other.y);
            if(diff != 0) return diff;
            return Objects.compare(label, other.label, (a, b) -> {
                if(a == null) return -1;
                if(b == null) return 1;
                return a.compareTo(b);
            });
        }
    }

    @Test
    public void scan() throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder("grep",
                "-ohr",
                "-E",
                "-I",
                "--color=never",
                "(\\d{4}[ -]){3}\\d{4}",
                System.getProperty("java.io.tmpdir"));
        builder.inheritIO();
        builder.start().waitFor(5, TimeUnit.MINUTES);
    }

    @Test
    public void breakLabel() {
        outer:
        for(int i=0; i< 10; i++) {
            for(int j =0; j< 10; j++) {
                if(i + j >10) {
                    break outer;
                }
            }
        }
    }
}
