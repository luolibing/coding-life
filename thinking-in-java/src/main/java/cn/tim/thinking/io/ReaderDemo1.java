package cn.tim.thinking.io;

import org.junit.Test;

import java.io.*;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * Created by LuoLiBing on 16/12/1.
 * Reader和Writer提供了兼容Unicode与面向字符的I/O功能.
 * 设计Reader和Writer继承层次结构主要是为了国际化.
 *
 * InputStream和Reader对应关系
 *
 * InputStream                  Reader,InputStreamReader
 * OutputStream                 Writer,OutputStreamWriter
 * FileInputStream              FileReader
 * FileOutputStream             FileWriter
 * StringBufferInputStream      StringReader
 *                              StringWriter
 * ByteArrayInputStream         CharArrayReader
 * ByteArrayOutputStream        CharArrayWriter
 * PipedInputStream             PipedReader
 * PipedOutputStream            PipedWriter
 *
 * 更改流的行为FilterInputStream修饰类对应关系
 *
 * FilterInputStream            FilterReader
 * FilterOutputStream           FilterWriter
 * BufferedInputStream          BufferedReader
 * BufferedOutputStream         BufferedWriter
 * DataInputStream              BufferedReader
 * PrintStream                  PrintWriter
 * LineNumberInputStream        LineNumberReader
 * StreamTokenizer              StreamTokenizer
 * PushbackInputStream          PushbackReader
 *
 *
 * RandomAccessFile类适用于由大小已知的记录组成的文件
 *
 */
public class ReaderDemo1 {

    /**
     * 缓冲读取一个文件
     * BufferedReader和FileReader联合使用
     */
    static class BufferedInputFile {
        public static String read(String filename) {
            // BufferedReader起缓冲作用, FileReader读取文件
            try (BufferedReader reader = new BufferedReader(new FileReader(filename)) ){
                StringBuilder sb = new StringBuilder();
                String s;
                while ((s = reader.readLine()) != null) {
                    sb.append(s);
                    sb.append("\n");
                }
                return sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    @Test
    public void bufferedInputFile() {
        String str = BufferedInputFile.read("/Users/tim/Downloads/unmatch_bak.txt");
        System.out.println(str);
    }

    static class BufferedLineReader {
        public static void read(String filename, Consumer<String> action) {
            try (BufferedReader reader = new BufferedReader(new FileReader(filename)) ){
                String s;
                while ((s = reader.readLine()) != null) {
                    action.accept(s);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void bufferedLineReader() {
        LinkedList list = new LinkedList();
        BufferedLineReader.read("/Users/tim/Downloads/unmatch_bak.txt", list::add);
        while (!list.isEmpty()) {
            System.out.println(list.removeLast());
        }
    }

    // 从内存输入,StringReader,read()每次读取一个字符
    @Test
    public void memoryInput() throws IOException {
        StringReader reader = new StringReader(BufferedInputFile.read("/Users/tim/Downloads/unmatch_bak.txt"));
        int c;
        while ((c = reader.read()) != -1) {
            System.out.println((char) c);
        }
    }

    // 格式化的内存输入
    @Test
    public void formattedMemoryInput() throws IOException {
        // DataInputStream支持格式化的输出,例如readBoolean(),readByte(),readLong(),readUTF()等等
        // 使用ByteArrayInputStream来支持字节数组输入
        try (DataInputStream in = new DataInputStream(
                new ByteArrayInputStream(
                        BufferedInputFile.read("/Users/tim/Downloads/unmatch_bak.txt").getBytes()))) {
            // 任何字节的值都是合法的结果,所以不能通过返回值来判断是否结束(in.readByte() != 1),可以使用in.available()来获取字节长度
            while (in.available() != 0) {
                System.out.println((char) in.readByte());
            }
        }
    }

    // 基本文件输出, FileWriter对象可以向文件写入数据.但是我们通常会使用BufferedWriter将其包装起来用以缓冲输出,提升性能.
    // 同事为了提供格式化机制,又被修饰为PrintWriter
    @Test
    public void basicFileOutput() throws IOException {
        // 用BufferedReader读取一个文件
        BufferedReader in = new BufferedReader(
                new StringReader(
                        BufferedInputFile.read("/Users/tim/Downloads/unmatch_bak.txt")));
        // 使用PrintWriter/BufferedWriter/FileWriter共同来完成文件的格式化写入
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("/share/match.txt")));
        int line = 0;
        String s;
        while ((s = in.readLine()) != null) {
            out.println(line++ + ":" + s);
        }
        // 如果不适用close,则缓冲区内容不会被刷新清空,那么它们也就不完整.
        out.close();
        System.out.println(BufferedInputFile.read("/share/match.txt"));
    }

    /**
     * 文本文件输出的快捷方式
     * PrintWriter
     * 提供了一个接收文件的构造函数PrintWriter(file),提供了默认的4层装饰...
     * this(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file))),false);
     *
     */
    @Test
    public void fileOutputShortCut() throws IOException {
        BufferedReader in = new BufferedReader(
                new StringReader(
                        BufferedInputFile.read("/Users/tim/Downloads/unmatch_bak.txt")));
        PrintWriter out = new PrintWriter(new File("/share/match.txt"));
        int lineCount = 1;
        String s;
        while ((s = in.readLine()) != null) {
            out.println(lineCount++ + ": " + s);
        }
        out.close();
        System.out.println(BufferedInputFile.read("/share/match.txt"));
    }

    /**
     * 存储与恢复数据
     * 使用DataOutputStream和DataInputStream对数据进行格式化存储与格式化恢复. 读取的顺序与写入的顺序要一样.
     * DataOutputStream能够保证不管使用的平台如何不同,写入的数据都能够通过对应的DataInputStream读取出来.
     * 用DataOutputStream写入的数据用非java读取会出现一些问题. 一般使用对象序列化和XML可能是更容易的存储和读取负责数据结构的方式
     * @throws IOException
     */
    @Test
    public void storingAndRecoveringData() throws IOException {
        DataOutputStream out = new DataOutputStream(
                new BufferedOutputStream(
                        new FileOutputStream("/share/match.txt")));
        out.writeDouble(3.14159d);
        out.writeUTF("That was pi");
        out.writeDouble(1.41413);
        out.writeUTF("Square root of 2");
        out.close();

        DataInputStream in = new DataInputStream(
                new BufferedInputStream(
                        new FileInputStream("/share/match.txt")));
        System.out.println(in.readDouble());
        System.out.println(in.readUTF());
        System.out.println(in.readDouble());
        System.out.println(in.readUTF());
    }


    /**
     * 读写随机访问文件
     * 可以使用seek()移动到文件任何位置然后进行读取或者修改
     */
    @Test
    public void randomAccessFile() throws IOException {
        String file = "rtest.dat";
        RandomAccessFile rf = new RandomAccessFile(file, "rw");
        for(int i = 0; i < 7; i++) {
            rf.writeDouble(i * 1.414);
        }
        rf.writeUTF("The end of the file");
        rf.close();
        display(file);

        System.out.println();
        rf = new RandomAccessFile(file, "rw");
        // seek()偏移多少个字节,因为double是8字节,所以5*8用来查找第6个双进度值
        rf.seek(5*8);
        rf.writeDouble(100.00001);
        rf.close();

        display(file);
    }

    public static void display(String file) throws IOException {
        RandomAccessFile rf = new RandomAccessFile(file, "r");
        for(int i = 0; i < 7; i++) {
            System.out.println("Value " + i + ": " + rf.readDouble());
        }
        rf.close();
    }


    /**
     * 管道流
     * PipedInputStream, PipedOutputStream, PipedReader, PipedWriter主要用于多线程任务之间的通信.
     *
     */
    @Test
    public void piped() throws InterruptedException {
        // 发送者
        class Sender implements Runnable {

            public PipedWriter writer = new PipedWriter();

            private Random rand = new Random(47);

            @Override
            public void run() {
                try {
                    while (true) {
                        for (int i = 'A'; i < 'z'; i++) {
                            writer.write(i);
                            TimeUnit.MILLISECONDS.sleep(rand.nextInt(1000));
                        }
                    }
                } catch (IOException|InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        // 接收者
        class Receiver implements Runnable {

            private PipedReader reader;

            public Receiver(PipedWriter writer) {
                try {
                    reader = new PipedReader(writer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void run() {
                try {
                    while (true) {
                        System.out.println("receive message [" + (char) reader.read() + "]");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Sender sender = new Sender();
        Receiver receiver = new Receiver(sender.writer);
        new Thread(sender).start();
        new Thread(receiver).start();
        Thread.sleep(1000*10);
    }
}
