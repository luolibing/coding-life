package cn.tim.thinking.io;

import org.junit.Test;

import java.io.*;
import java.util.Random;

/**
 * Created by LuoLiBing on 16/12/9.
 * 对象持久化
 *
 * Java的对象序列化将那些实现了Serializable接口的对象转换成一个字节序列,并能够在以后将这个字节序列完全恢复为原来的对象.设置可以通过网络进行传输;
 * 这意味着序列化机制能自动解决不同操作系统之间的差异.
 *
 * 对象序列化可以实现轻量级持久性.
 * 对象序列化概念加入java是为了支持两种主要特性:
 * 1 JAVA的远程方法调用  RMI
 * 2 JAVA beans对象序列化也是必需的
 *
 */
public class SerializableDemo1 implements Serializable {

    // 数据
    static class Data implements Serializable {
        private int n;

        public Data(int n) {
            this.n = n;
        }

        public String toString() {
            return Integer.toString(n);
        }
    }

    static class Worm implements Serializable {
        private static Random rand = new Random(47);

        private Data[] d = {
                new Data(rand.nextInt(10)),
                new Data(rand.nextInt(10)),
                new Data(rand.nextInt(10))
        };

        private Worm next;

        private char c;

        public Worm(int i, char x) {
            System.out.println("Worm constructor: " + i);
            c = x;
            if(--i > 0) {
                next = new Worm(i, (char) (x + 1));
            }
        }

        @Override
        public String toString() {
            StringBuilder result = new StringBuilder(":");
            result.append(c);
            result.append("(");
            for(Data dat : d) {
                result.append(dat);
            }
            result.append(")");
            if(next != null) {
                result.append(next);
            }
            return result.toString();
        }

        public static void main(String[] args) throws IOException, ClassNotFoundException {
            // 序列化进文件
            Worm w = new Worm(6, 'a');
            System.out.println("w= " + w);
            // 将对象的字节序列写入文件
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("worm.out"));
            out.writeUTF("Worm storage");
            out.writeObject(w);
            out.close();

            // 从文件中反序列化对象
            System.out.println("\nstore in File and re-serializable");
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("worm.out"));
            System.out.println(in.readUTF());
            // Worm中关联的对象也被自动序列化了.
            // 当数据恢复的时候,会自动去寻找类,如果没有找到对应额类会抛出ClassNotFoundException异常
            // 所以反序列化的时候,必须保证在jvm上能找到相关的.class文件
            Worm worm = (Worm) in.readObject();
            System.out.println("worm=" + worm);

            // 写入字节数组OutputStream, 然后恢复.
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out2 = new ObjectOutputStream(bos);
            out2.writeUTF("Hello world!");
            out2.writeObject(w);
            out2.close();

            System.out.println("\nstore in byteArrayOutputStream and re-serializable");
            ObjectInputStream in2 = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
            System.out.println("Message = " + in2.readUTF());
            Worm w2 = (Worm) in2.readObject();
            System.out.println("worm = " + w2);
            bos.close();
            in2.close();
        }
    }

    class School implements Serializable {
        private String name;

        public School(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "school [" + name +"]";
        }
    }

    class Teacher implements Serializable {

        private School school;

        private String name;

        public Teacher(School school, String name) {
            this.school = school;
            this.name = name;
        }

        @Override
        public String toString() {
            return "Teacher{" +
                    "school=" + school +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    @Test
    public void teacherSerialize() throws IOException, ClassNotFoundException {
        School school = new School("清华大学");
        Teacher teacher = new Teacher(school, "jack");

        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("teacher.out"));
        out.writeUTF("teacher Info");
        out.writeObject(teacher);
        out.close();

        ObjectInputStream in = new ObjectInputStream(new FileInputStream("teacher.out"));
        String message = in.readUTF();
        System.out.println("message = " + message);
        Teacher t = (Teacher) in.readObject();
        System.out.println("teacher = " + t);
        System.out.println("school = " + t.school);
    }
}
