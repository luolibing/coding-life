package cn.tim.thinking.io;

import org.junit.Test;

import java.io.*;
import java.util.Optional;

/**
 * Created by LuoLiBing on 16/12/2.
 * 标准I/O
 * Unix中"程序锁使用的单一信息流". 程序的所有输入都可以来自于标准输入,它的所有输出也都可以发送到标准输出,以及所有错误信息都可以发送到标准错误
 * 标准IO的意义在于: 可以很容易地把程序串联起来,一个程序的标准输出可以成为另一个程序的标准输入.
 *
 */
public class StandStreamDemo1 {

    /**
     * 从标准输入中读取
     * System.in, System.out, System.err
     * System.out和System.err都是用了包装的printStream对象. 而System.in并没有进行加工,是用的是InputStream
     * 将System.in包装成BufferedReader来是用这要求我们必须用InputStreamReader来把System.in转换成Reader.
     */
    @Test
    public void echo() throws IOException {
        // 是用BufferedReader和InputStreamReader封装System.in,以便可以一行一行的接收输入
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String s;
        while ((s = reader.readLine()) != null && s.length() != 0) {
            System.out.println(s);
        }
    }

    @Test
    public void echo2() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String s;
        while ((s = reader.readLine()) != null && s.length() != 0) {
            System.out.println(s.toUpperCase());
        }
    }

    /**
     * 将PrintStream转换成PrintWriter
     */
    @Test
    public void changeSystemOut() {
        // PrintWriter(PrintStream, flag), 第二个参数自动清理功能
        PrintWriter out = new PrintWriter(System.out, true);
        System.out.println();
        out.println("Hello");
    }


    /**
     * 标准IO重定向
     * SetIn(input)
     * SetOut(out)
     * SetErr(printStream)
     * IO重定向操纵的是字节流,而不是字符流;因此我们使用的是InputStream和OutputStream,而不是Reader和Writer
     */
    @Test
    public void redirecting() throws IOException {
        PrintStream console = System.out;
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(
                "/Users/tim/Documents/samples-tim/thinking-in-java/src/main/java/cn/tim/thinking/io/FileDemo1.java"));
        PrintStream out = new PrintStream(new BufferedOutputStream(new FileOutputStream("test.out")), true);
        PrintStream err = new PrintStream(new BufferedOutputStream(new FileOutputStream("err.out")), true);

        // 设置标准输入为FileDemo1文件输入, 标准输出和标准错误输出分别重定向到不同的文件
        System.setIn(in);
        System.setOut(out);
        System.setErr(err);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s;
        // 相当于从FileDemo1.java文件中读取内容
        while ((s = br.readLine()) != null) {
            System.out.println(s);
            System.err.println(s);
        }
        out.close();
        // 重置标准IO
        System.setOut(console);
        System.setErr(System.err);
        System.setIn(System.in);
        System.out.println("end");
    }


    @Test
    public void empty() {
        Optional.empty().get();
    }

}
