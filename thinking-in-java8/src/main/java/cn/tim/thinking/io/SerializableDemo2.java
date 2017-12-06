package cn.tim.thinking.io;

import java.io.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by LuoLiBing on 16/12/9.
 * 序列化的控制
 *
 * 如果希望手动控制哪些字段被序列化或者序列化字段,可以通过实现Externalizable接口,代替实现Serializable接口对序列化过程进行控制.
 * Externalizable接口,同时增添了两个方法:writeExternal()和readExternal().这两个方法会在序列化和反序列化还原的过程中自动被调用
 *
 * Serializable和Externalizable的区别:
 * Externalizable在恢复的时候,会调用默认的构造器,如果没有默认的构造器会抛出InvalidClassException异常
 * Serializable在恢复的时候,对象完全以它存储的二进制位位基础来构造,而不用调用构造函数
 *
 */
public class SerializableDemo2 {

    static class Blip1 implements Externalizable {

        public Blip1() {
            System.out.println("Blip1 Constructor");
        }

        @Override
        public void writeExternal(ObjectOutput out) throws IOException {
            System.out.println("Blip1.writeExternal");
        }

        @Override
        public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
            System.out.println("Blip.readExternal");
        }
    }

    static class Blip2 implements Externalizable {

        public Blip2() {
            System.out.println("Blip2 Constructor");
        }

        @Override
        public void writeExternal(ObjectOutput out) throws IOException {
            System.out.println("Blip2.writeExternal");
        }

        @Override
        public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
            System.out.println("Blip2.readExternal");
        }
    }

    public static class Blips {
        public static void main(String[] args) throws IOException, ClassNotFoundException {
            System.out.println("Constructing objects: ");
            Blip1 b1 = new Blip1();
            Blip2 b2 = new Blip2();
            ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream("blip.out"));
            System.out.println("Saving objects:");
            o.writeObject(b1);
            o.writeObject(b2);
            o.close();

            ObjectInputStream in = new ObjectInputStream(new FileInputStream("blip.out"));
            System.out.println("Recovering b1: ");
            b1 = (Blip1) in.readObject();

            // b2如果没有public的默认构造函数,这个地方恢复,会抛出异常java.io.InvalidClassException
            System.out.println("Recovering b2: ");
            b2 = (Blip2) in.readObject();
        }
    }


    static class Blip3 implements Externalizable {

        private int i;
        private String s;

        public Blip3() {
            System.out.println("Blip3 Constructor");
            // 默认的构造函数这,并没有初始化i和s
        }

        public Blip3(String x, int a) {
            System.out.println("Blip3(String x, int a)");
            s = x;
            i = a;
            // 初始化s和i只在带参数的构造函数中进行
        }

        @Override
        public String toString() {
            return s + i;
        }

        /**
         * write的时候需要手动将需要序列化的值手动写在这个地方
         * @param out
         * @throws IOException
         */
        @Override
        public void writeExternal(ObjectOutput out) throws IOException {
            System.out.println("Blip3.writeExternal");
            out.writeUTF(s);
            out.writeInt(i);
        }

        /**
         * 调用Blip3.readExternal()方法之前,会先调用默认的构造函数,而默认构造函数一般是不带参数初始化的.
         * 所以必须在ReadExternal中手动读取对应的值,然后初始化. 否则s=null,i = 0;
         * @param in
         * @throws IOException
         * @throws ClassNotFoundException
         */
        @Override
        public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
            System.out.println("Blip3.readExternal");
            s = in.readUTF();
            i = in.readInt();
        }

        public static void main(String[] args) throws IOException, ClassNotFoundException {
            System.out.println("Constructing Objects: ");
            Blip3 blip3 = new Blip3("Hello", 30);
            ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream("blip3.out"));
            System.out.println("Saving object: ");
            o.writeObject(blip3);
            o.close();

            ObjectInputStream in = new ObjectInputStream(new FileInputStream("blip3.out"));
            System.out.println("Recovering b3: ");
            blip3 = (Blip3) in.readObject();
            System.out.println(blip3);
        }
    }


    class Blip4 extends Blip3 {
        private String name;

        private int age;

        public Blip4(String name, int age) {
            this.name = name;
            this.age = age;
        }

        /**
         * 如果从Externalizable对象继承,通常需要调用基类版本的writeExternal()和readExternal()
         * @param out
         * @throws IOException
         */
        @Override
        public void writeExternal(ObjectOutput out) throws IOException {
            super.writeExternal(out);
            out.writeUTF(name);
            out.writeInt(age);
        }

        @Override
        public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
            super.readExternal(in);
            this.name = in.readUTF();
            this.age = in.readInt();
        }
    }


    /**
     * 如果不需要将一些敏感信息(例如密码),可以使用transient关键词,表示不需要被序列化的字段
     *
     * 控制敏感数据被序列化的方法:
     * 1 通过实现Externalizable接口,指定写入哪些部分,敏感数据不写入,实现敏感数据隐藏.
     * 2 通知关键词transient修改字段,在序列化时,被修饰的字段不再被序列化.
     *
     * Externalizable对象在默认情况下不保存他们的任何字段.,所以transient关键字只能和Serializable对象一起使用
     *
     */
    static class Logon implements Serializable {
        // date被反序列化的时候,并不会再调用new Date()方法,重新调用
        private Date date = new Date();

        private String username;

        // 使用transient阻止password被序列化, 当反序列化恢复时,password会被序列化为null.
        private transient String password;

        public Logon(String username, String pwd) {
            this.username = username;
            this.password = pwd;
        }

        @Override
        public String toString() {
            return "Logon{" +
                    "date=" + date +
                    ", username='" + username + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }

        public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
            Logon a = new Logon("Hulk", "myLittlePony");
            System.out.println("logon a = " + a);
            ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream("logon.out"));
            o.writeObject(a);
            o.close();

            TimeUnit.SECONDS.sleep(1);
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("logon.out"));
            a = (Logon) in.readObject();
            System.out.println("logon a = " + a);
        }
    }


    /**
     * Externalizable的替代方法
     *
     * 如果不是特别坚持实现Externalizable接口,那么还有另一种方法.我们可以实现Serializable接口,并添加名为writeObject()和readObject()方法.
     * 这样一旦对象序列化和反序列化,都会自动调用这两个方法. 其中实现的原理,是使用了反射机制,判断是否有writeObject()方法.有则调用这个方法.
     *
     * private void writeObject(ObjectOutputStream stream) throws IOException;
     * 而且两个方法应该是private,避免被外部调用,为什么不适用实现某个接口来实现这2个方法,这是因为不希望这两个方法被外部调用.同时又想实现接口的效果,所以使用静态方法的方式.
     * 同时可以在writeObject()内部,调用defaultWriteObject()来选择执行默认的writeObject(). 默认的writeObject()只会写入那些非transient,我们可以通过手动写的方式,控制写入transient字段
     *
     */
    static class SerialCtl implements Serializable {
        private String a;

        private transient String b;

        public SerialCtl(String aa, String bb) {
            this.a = aa;
            this.b = bb;
        }

        @Override
        public String toString() {
            return "SerialCtl{" +
                    "a='" + a + '\'' +
                    ", b='" + b + '\'' +
                    '}';
        }

        private void writeObject(ObjectOutputStream o) throws IOException {
            o.defaultWriteObject();
//            o.writeUTF(b);
        }

        private void readObject(ObjectInputStream i) throws IOException, ClassNotFoundException {
            i.defaultReadObject();
//            this.b = i.readUTF();
        }

        public static void main(String[] args) throws IOException, ClassNotFoundException {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(buffer);
            SerialCtl ctl = new SerialCtl("aaa", "bbb");
            System.out.println("ctl = " + ctl);
            out.writeObject(ctl);
            out.close();

            ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(buffer.toByteArray()));
            ctl = (SerialCtl) in.readObject();
            System.out.println("ctl = " + ctl);
        }
    }

}
