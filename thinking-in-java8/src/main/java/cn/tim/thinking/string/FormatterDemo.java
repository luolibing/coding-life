package cn.tim.thinking.string;

import org.junit.Test;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Formatter;

/**
 * Created by LuoLiBing on 16/12/22.
 * 在java中, 所有新的格式化功能都由java.util.Formatter类处理.
 * 可以把它看成翻译器,它将格式化字符串和数据翻译成你想要的结果. 创建Formatter时需要传递一些信息, 告诉它最终的结果将向哪里输出.
 *
 */
public class FormatterDemo {

    class Turtle {
        private String name;
        private Formatter f;

        public Turtle(String n, Formatter f) {
            this.name = n;
            this.f = f;
        }

        public void move(int x, int y) {
            f.format("%s The Turtle is at (%d, %d)\n", name, x, y);
        }
    }

    @Test
    public void turtle() {
        PrintStream out = System.err;
        // 制定Formatter输出到System.out当中
        Turtle tommy = new Turtle("Tommy", new Formatter(System.out));
        Turtle terry = new Turtle("Terry", new Formatter(out));
        tommy.move(0, 0);
        terry.move(4, 8);
        tommy.move(2, 2);
        terry.move(10, 12);
    }


    /**
     *
     * %[argument_index$][flags][width][.precision]conversion
     *
     * 默认向右对齐, 使用"-"向左对齐, %15.15s中使用.号String表示最大长度, 浮点数.表示小数位数, %05x 不够的补0
     *
     * 格式转换
     * %s   字符串
     * %c   Unicode字符
     * %b   Boolean值
     * %d   整数
     * %f   浮点数(十进制)
     * %e   浮点数(科学计数)
     * %x   整数(十六进制)
     * %h   散列码(十六进制)
     * %    字符%
     *
     */
    @Test
    public void receipt() {
        Formatter f = new Formatter(System.out);
        f.format("%-15s %5s %10s\n", "Item", "Qty", "Price");
        f.format("%-15s %5s %10s\n", "----", "---", "-----");
        f.format("%-15.15s %5d %10.2f\n", "Jack's Magic Beans", 3, 4.26);
        f.format("%-15.15s %05d %10.2f\n", "King", 100, 25.50);

        char u = 'a';
        System.out.println("u = 'a'");
        f.format("s: %s\n", u);
        f.format("c: %c\n", u);
        f.format("");
    }


    /**
     * String.format()是一个static方法, 它接受与Formatter.format()方法一样的参数, 但是返回一个String对象
     */
    class DatabaseException extends Exception {
        public DatabaseException(int transactionID, int queryId, String message) {
            super(String.format("(t%d, q%d) %s", transactionID, queryId, message));
        }
    }


    /**
     * String.format()内部, 其实是创建了一个Formatter对象, 然后将传入的参数转给该Formatter.
     */
    @Test
    public void stringFormat() {
        try {
            throw new DatabaseException(3, 7, "Write failed");
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    static class Hex {
        public static String format(byte[] data) {
            StringBuilder result = new StringBuilder();
            int n = 0;
            for(byte b: data) {
                if(n % 16  == 0) {
                    result.append(String.format("%05X: ", n));
                }
                result.append(String.format("%02X ", b));
                n++;
                if(n % 16 == 0) {
                    result.append("\n");
                }
            }
            return result.toString();
        }

        public static void main(String[] args) throws IOException {
            byte[] data = Files.readAllBytes(Paths.get(
                    "/Users/tim/Documents/samples-tim/thinking-in-java/target/classes/cn/tim/thinking/string/FormatterDemo.class"));
            String format = Hex.format(data);
            System.out.println(format);
        }
    }

    @Test
    public void timestamp() {
        Timestamp timestamp = new Timestamp(new Date().getTime());
        System.out.println(timestamp.toString());
    }

}
