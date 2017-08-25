package cn.tim.thinking.io;

import org.junit.Test;
import sun.nio.ch.DirectBuffer;

import java.io.*;
import java.nio.*;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Iterator;
import java.util.SortedMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by LuoLiBing on 16/12/5.
 * NIO性能的提高来自于所使用的结构更接近于操作系统执行IO的方式: 通道和缓冲器
 * 通道就是包含数据的存储,而缓冲器则是运送数据的载体.我们并没有直接和通道交互;我们只是和缓冲器交互,并把缓冲器派送到通道.通道要么像缓冲器获得数据要么向缓冲器发送数据
 * 唯一直接与通道交互的缓冲器是ByteBuffer,也就是说可以存储未加工字节的缓冲器.
 *
 * FileChannel
 *  旧IO类库三个被修改的类是FileInputStream,FileOutputStream,和RandomAccessFile.
 *  Reader和Writer这种字符模式类不能产生通道;但是Channels类提供了使用方法,用以在通道中产生Reader和Writer
 *
 */
public class NioDemo1 {

    /**
     * 通道
     * 缓冲器:
     *  将字节存放于ByteBuffer的方法之一是:使用put方法直接对他们进行填充,填入一个或多个字节,或基本数据类型的值.
     *  也可以使用wrap()方法将已存在的字节数组包装进ByteBuffer中,这样就不需要复制底层的数据,而是将数组作为ByteBuffer的存储器
     */
    @Test
    public void getChannel() throws IOException {
        final int BSIZE = 1024;

        // 通过FileOutputStream获取写FileChannel
        FileChannel fc = new FileOutputStream("data.txt").getChannel();
        // 通过ByteBuffer缓冲器向通道写入数据
        fc.write(ByteBuffer.wrap("Hello world!".getBytes()));
        fc.close();

        // 通过RandomAccessFile获取读写通道
        fc = new RandomAccessFile("data.txt", "rw").getChannel();
        // 移动到文件最后,进行附加写操作
        fc.position(fc.size());
        fc.write(ByteBuffer.wrap("Hello world!".getBytes()));
        fc.close();

        // 通过FileInputStream获取可读通道
        fc = new FileInputStream("data.txt").getChannel();
        // 给缓冲器制定大小
        ByteBuffer bytes = ByteBuffer.allocate(BSIZE);
        // 通道将数据写入缓冲器
        fc.read(bytes);
        // 翻转回到头位置,当填充完数据之后,必须调用flip()以便让其他人获取数据
        bytes.flip();

        while (bytes.hasRemaining()) {
            System.out.print((char) bytes.get());
        }
    }

    /**
     * 通道缓冲器文件拷贝DEMO
     * 当ByteBuffer缓冲器准备让其他人读取时,需要flip;
     * 当需要被再次写入数据时,需要clear,以便下次能写入数据
     *
     * @throws IOException
     */
    @Test
    public void channelCopy() throws IOException {
        final int BSIZE = 1024 * 64;
        FileChannel
                in = new FileInputStream("/share/rms/jackrabbit20160325.tar.gz").getChannel(),
                out = new FileOutputStream("/share/rms/jackrabbit20161206.tar.gz").getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(BSIZE);
        // 达到通道的末尾,没有数据时返回-1
        while (in.read(buffer) != -1) {
            // flip以便out能够读取到buffer的数据
            buffer.flip();
            out.write(buffer);
            // clear以便下一次能正常的往buffer中写入数据.以便内部指针重新安排
            buffer.clear();
        }
    }


    /**
     * 直接使用transferTo或者transferFrom将两个通道直接相连
     * @throws IOException
     */
    @Test
    public void transferTo() throws IOException {
        FileChannel
                in = new FileInputStream("data.txt").getChannel(),
                out = new FileOutputStream("data_bak.txt").getChannel();
        // 将in中的数据写入到out当中
        in.transferTo(0, in.size(), out);
        // 从in中读取数据到out当中，效果与transferTo一致
        // out.transferFrom(in, 0, in.size());
    }


    /**
     * 缓冲器容纳的是普通的字节,为了把他们转换成字符,有一下几种方式
     * @throws IOException
     */
    @Test
    public void bufferToText() throws IOException {
        // 方式一: 直接写入,读取,结果乱码
        // 写文件
        FileChannel fc = new FileOutputStream("data.txt").getChannel();
        fc.write(ByteBuffer.wrap("hello world!".getBytes()));
        fc.close();

        // 读取文件
        fc = new FileInputStream("data.txt").getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        fc.read(buffer);
        buffer.flip();
        // 这种方式打印不出文本
        System.out.println(buffer.asCharBuffer());


        // 方式二: 输出的时候进行解码,结果正常
        // 倒带(返回数据开始部分), 然后使用系统默认的编码进行解码
        buffer.rewind();
        String encoding = System.getProperty("file.encoding");
        System.out.println("Decoded using " + encoding + ": " + Charset.forName(encoding).decode(buffer));


        // 方式三: 写入的时候制定好编码,可打印的字符集UTF-16BE???????
        // 制定写入的编码格式
        fc = new FileOutputStream("data2.txt").getChannel();
        fc.write(ByteBuffer.wrap("Good night!".getBytes("UTF-16BE")));
        fc.close();
        // 重新再次读取
        fc = new FileInputStream("data2.txt").getChannel();
        buffer.clear();
        fc.read(buffer);
        buffer.flip();
        // 可以读取
        System.out.println(buffer.asCharBuffer());


        // 方式四: 将buffer作为CharBuffer直接输入字符,这样也不会出现乱码
        fc = new FileOutputStream("data2.txt").getChannel();
        buffer = ByteBuffer.allocate(24);
        buffer.asCharBuffer().put("Some text");
        fc.write(buffer);
        fc.close();

        // 读取
        fc = new FileInputStream("data2.txt").getChannel();
        buffer.clear();
        fc.read(buffer);
        buffer.flip();
        System.out.println(buffer.asCharBuffer());
    }

    /**
     * 编码集
     *
     */
    @Test
    public void availableCharSets() {
        SortedMap<String, Charset> charSets = Charset.availableCharsets();
        Iterator<String> it = charSets.keySet().iterator();
        while (it.hasNext()) {
            String csName = it.next();
            System.out.print(csName);
            Iterator<String> aliases = charSets.get(csName).aliases().iterator();
            if(aliases.hasNext()) {
                System.out.print(": ");
            }
            while (aliases.hasNext()) {
                System.out.print(aliases.next());
                if(aliases.hasNext()) {
                    System.out.print(",");
                } else {
                    System.out.println();
                }
            }
        }
    }


    static class PrintCharBuffer {
        // 使用bitSet来存储可以打印的值
        static BitSet isPrintable = new BitSet(127);
        static String encoding = System.getProperty("file.encoding");

        static {
            for(int i = 32; i <= 127; i++) {
                isPrintable.set(i);
            }
        }

        public static void setPrintableLimit(CharBuffer cb) {
            cb.rewind();
            // 遇到第一个不可打印的值,即为限制位置
            while (isPrintable.get(cb.get()));
            cb.limit(cb.position() -1);
            cb.rewind();
        }

        public static void main(String[] args) {
            System.out.println("Default Encoding is : " + encoding);
            CharBuffer buffer = ByteBuffer.allocate(16).asCharBuffer();
            buffer.put("ABCDE" + (char) 0x01 + "FG");
            buffer.flip();
            System.out.println(buffer);
            setPrintableLimit(buffer);
            System.out.println(buffer);
        }
    }

    /**
     * Byte只能保存字节类型的数据,但是拥有将字节转换为各种基本类型值的方法,但是没有boolean类型的buffer
     * 可以转换为CharBuffer,IntBuffer,DoubleBuffer等等
     */
    @Test
    public void getData() {
        final int BSIZE = 1024;
        // 分配了1024个值.并且每个值初始都为0
        ByteBuffer buffer = ByteBuffer.allocate(BSIZE);
        int i = 0;
        while (i++ < buffer.limit()) {
            if(buffer.get() != 0) {
                System.out.println("nonzero");
            }
        }
        System.out.println("i= " + i);

        buffer.rewind();
        buffer.asCharBuffer().put("Howdy!");
        char c;
        while ((c = buffer.getChar()) != 0) {
            System.out.println(c + " ");
        }
        System.out.println();

        buffer.rewind();
        buffer.asShortBuffer().put((short) 455433);
        System.out.println(buffer.getShort());

        buffer.rewind();
        buffer.asIntBuffer().put(99454353);
        System.out.println(buffer.getInt());

        buffer.rewind();
        buffer.asLongBuffer().put(443232423L);
        System.out.println(buffer.getLong());
    }

    /**
     * 缓冲视图器
     */
    @Test
    public void intBuffer() {
        final int BSIZE = 1024;
        ByteBuffer buffer = ByteBuffer.allocate(BSIZE);
        IntBuffer ib = buffer.asIntBuffer();
        ib.put(new int[] {11, 42, 47, 99, 143, 811, 1016});
        System.out.println(ib.get(3));
        ib.put(3, 1811);
        ib.flip();
        while (ib.hasRemaining()) {
            int i = ib.get();
            System.out.println(i);
        }
    }


    @Test
    public void doubleBuffer() {
        final int BSIZE = 1024;
        ByteBuffer buffer = ByteBuffer.allocate(BSIZE);
        DoubleBuffer ib = buffer.asDoubleBuffer();
        ib.put(new double[] {11d, 42d, 47d, 99d, 143d, 811d, 1016d});
        System.out.println(ib.get(3));
        ib.put(3, 1811);
        ib.flip();
        while (ib.hasRemaining()) {
            double i = ib.get();
            System.out.println(i);
        }
    }

    /**
     * ByteBuffer通过一个被包装过的8字节数组产生,然后通过各种不同的基本类型的视图缓冲器显示出来
     */
    @Test
    public void viewBuffers() {
        ByteBuffer bb = ByteBuffer.wrap(new byte[]{0, 0, 0, 0, 'a'});
        // 回到起始位置
        bb.rewind();
        System.out.println("Byte Buffer");
        while (bb.hasRemaining()) {
            System.out.print(bb.position() + "->" + bb.get() + ",");
        }
        System.out.println();

        CharBuffer cb = ((ByteBuffer) bb.rewind()).asCharBuffer();
        while (cb.hasRemaining()) {
            System.out.println(cb.position() + "->" + cb.get() + ",");
        }
        System.out.println();
    }


    /**
     * 字节存放次序
     * 高位优先: 重要的字节存放在地址的最低存储单元
     * 低位优先: 重要的字节存放在地址的最高存储单元
     *
     * ByteBuffer是以高位优先的形式存储数据的,数据在网上传送时也常常使用高位优先的形式.
     * 我们可以使用带有ByteOrder.BIG_ENDIAN或者ByteOrder.LITTLE_ENDIAN的order方法改变ByteBuffer的字节排序方式
     *
     */
    @Test
    public void endians() {
        // ByteBuffer是byte是以两字节存储,使用高位优先, a为0, 97 使用低位优先,a为97,0
        ByteBuffer bb = ByteBuffer.wrap(new byte[12]);
        bb.asCharBuffer().put("abcdef");
        System.out.println(Arrays.toString(bb.array()));
        bb.rewind();
        bb.order(ByteOrder.BIG_ENDIAN);
        bb.asCharBuffer().put("abcdef");
        System.out.println(Arrays.toString(bb.array()));

        bb.rewind();
        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.asCharBuffer().put("abcdef");
        System.out.println(Arrays.toString(bb.array()));
    }


    /**
     * 缓冲器的细节
     * Buffer由数据和可以高效地访问及操纵这些数据的四个索引组成: mark, position, limit 和 capacity
     *
     */
    @Test
    public void test() {
        ByteBuffer bb = ByteBuffer.wrap(new byte[] {100, 89, 48, 60 ,70});
        // 容量
        System.out.println("capacity: " + bb.capacity());

        bb.put(4, (byte) 80);

        // 设置界限为4
        bb.limit(4);
        bb.put((byte) 5);
        System.out.println(Arrays.toString(bb.array()));

        System.out.println("limit: " + bb.limit());
        while (bb.hasRemaining()) {
            System.out.println(bb.get());
        }

        // 将limit设置为position当前位置, position设置为0
        bb.flip();
        bb.limit(5);
        while (bb.hasRemaining()) {
            System.out.println(bb.get());
        }

        // 将mark设置为当前位置
        bb.mark();

        // position()返回当前位置
        System.out.println(bb.position());

        // 设置当前位置
        bb.position(0);
        System.out.println();
        // 返回limit - position
        System.out.println(bb.remaining());
        // 判断是否有
        while (bb.hasRemaining()) {
            System.out.println(bb.get());
        }
    }

    @Test
    public void read() throws IOException {
        Path path = Paths.get("/share/updateCourseInfoByWorkOrderId.txt");
        String workOrderIds = new String(Files.readAllBytes(path));
        String[] array = workOrderIds.split(",");
        for(String workOrderId : array) {
            System.out.println(Long.valueOf(workOrderId));
        }
    }

    @Test
    public void random() throws IOException {
        Path path = Paths.get("/share/updateCourseInfoByWorkOrderId.txt");
        String workOrderIds = new String(Files.readAllBytes(path));
        String[] array = workOrderIds.split(",");
        for(String workOrderId : array) {
            System.out.println(Long.valueOf(workOrderId));
        }
    }

    /**
     * ByteBuffer,使用asCharBuffer,产生ByteBuffer的一个CharBuffer视图
     */
    static class UsingBuffers {
        private static void symmetricScramble(CharBuffer buffer) {
            while (buffer.hasRemaining()) {
                // 将mark位置设置为position
                buffer.mark();
                char c1 = buffer.get();
                char c2 = buffer.get();
                // 将position回退到mark位置
                buffer.reset();
                System.out.println("position: " + buffer.position());
                // 交换位置
                buffer.put(c2).put(c1);
            }
        }

        public static void main(String[] args) {
            char[] data = "UsingBuffers".toCharArray();
            // 分配2倍的大小
            ByteBuffer buffer = ByteBuffer.allocate(data.length * 2);
            CharBuffer cb = buffer.asCharBuffer();
            cb.put(data);
            // 倒带到开始, position=0,mark=-1
            System.out.println(cb.rewind());
            symmetricScramble(cb);
            System.out.println(cb.rewind());
            symmetricScramble(cb);
            System.out.println(cb.rewind());
        }
    }

    /**
     * get()和put()函数,position指针就会随之改变.
     * get(index)和put(index)的方法,并不会引起position变化
     */
    @Test
    public void position() {
        ByteBuffer buffer = ByteBuffer.wrap(new byte[]{100, 89, 49, 98, 10});
        buffer.get();
        System.out.println(buffer.position());
        buffer.get(3);
        System.out.println(buffer.position());

        // position = 0; mark = -1;
        buffer.rewind();
    }

    /**
     * 内存映射文件: 允许我们创建和修改那些因为太大而不能放入内存的文件.
     * 有了内存映射文件,我们就可以嘉定整个文件都在内存中,而且可以完全把它当作非常大的数组来访问,这种方法极大地简化了用于修改文件的代码.
     * 内存映射文件这种方式,可以很容易地修改大文件(2),因为只有一部分文件放入到了内存. 我们必须制定映射文件的起始位置和映射区域的长度.这样我们可以映射某个大文件的较小的部分
     *
     * @throws IOException
     */
    @Test
    public void largeMappedFiles() throws IOException {
        // 128MB大小
        final int length = 0x8FFFFFF;
        // 内存映射文件,MappedByteBuffer映射ByteBuffer,直接缓冲器.
        MappedByteBuffer out = new RandomAccessFile("test.dat", "rw")
                .getChannel()
                .map(FileChannel.MapMode.READ_WRITE, 0, length);
        // MappedByteBuffer可以作为一个大数组来访问,写入MappedByteBuffer
        for(int i = 0; i < length; i++) {
            out.put((byte) 'x');
        }
        System.out.println("Finished writing");

        // 获取数组
        for(int i = length / 2; i < length/2 + 6; i++) {
            System.out.println((char) out.get(i));
        }
    }

    /**
     * 映射文件访问往往可以更加显著地加快速度
     */
    static class MappedIO {
        private static int numOfInts = 4000000;
        private static int numOfUbuffInts = 200000;
        private abstract static class Tester {
            private String name;
            public Tester(String name) {
                this.name = name;
            }

            // 测试框架
            public void runTest() {
                System.out.println(name + ": ");
                try {
                    long start = System.nanoTime();
                    test();
                    long duration = System.nanoTime() - start;
                    System.out.println(String.format("%.2f\n", duration / 1.0e9));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            public abstract void test() throws IOException;
        }

        private static Tester[] tests = {
                // 使用Stream写
                new Tester("Stream Write") {
                    @Override
                    public void test() throws IOException {
                        // DataOutputStream,BufferedOutputStream, FileOutputStream
                        DataOutputStream dos = new DataOutputStream(
                                // 如果FileOutputStream不设置append属性,默认是append false,再次写会覆盖掉之前的数据
                                new BufferedOutputStream(
                                        new FileOutputStream("temp.tmp")));
                        for(int i = 0; i < numOfInts; i++) {
                            dos.write(i);
                        }
                        dos.close();
                    }
                },

                // 内存映射文件
                new Tester("Mapped Write") {
                    @Override
                    public void test() throws IOException {
                        FileChannel fc = new RandomAccessFile("temp1.tmp", "rw").getChannel();
                        IntBuffer ib = fc.map(FileChannel.MapMode.READ_WRITE, 0, numOfInts).asIntBuffer();
                        for(int i = 0; i < numOfInts; i++) {
                            ib.put(i);
                        }
                        fc.close();
                    }
                },

                // Stream写
                new Tester("Stream Read") {
                    @Override
                    public void test() throws IOException {
                        DataInputStream in = new DataInputStream(
                                new BufferedInputStream(
                                        new FileInputStream("temp.tmp")));
                        for(int i = 0; i < numOfInts; i++) {
                            in.readInt();
                        }
                        in.close();
                    }
                },

                // 映射文件读
                new Tester("Mapped Read") {
                    @Override
                    public void test() throws IOException {
                        // 通道单独写
                        FileChannel fc = new RandomAccessFile("temp.tmp", "r").getChannel();
                        // 内存映射, asIntBuffer()
                        IntBuffer ib = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size()).asIntBuffer();
                        while (ib.hasRemaining()) {
                            ib.get();
                        }
                        fc.close();
                    }
                },

                // 读写
                new Tester("Stream Read/Write") {
                    @Override
                    public void test() throws IOException {
                        RandomAccessFile raf = new RandomAccessFile("temp.tmp", "rw");
                        raf.writeInt(1);
                        for(int i = 0; i < numOfUbuffInts; i++) {
                            raf.seek(raf.length() - 4);
                            raf.writeInt(raf.readInt());
                        }
                        raf.close();
                    }
                },

                // 内存映射读写
                new Tester("Mapped Read/Write") {
                    @Override
                    public void test() throws IOException {
                        FileChannel fc = new RandomAccessFile("temp.tmp", "rw").getChannel();
                        IntBuffer ib = fc.map(FileChannel.MapMode.READ_WRITE, 0, fc.size()).asIntBuffer();
                        ib.put(0);
                        for(int i = 0; i < numOfUbuffInts; i++) {
                            ib.put(ib.get(i - 1));
                        }
                        fc.close();
                    }
                }
        };

        public static void main(String[] args) {
            for(Tester test : tests) {
                test.runTest();
            }
        }
    }


    /**
     * allocateDirect() 直接分配系统内存,不使用JVM内存
     * @throws InterruptedException
     */
    @Test
    public void allocateDirect() throws InterruptedException {
        // 1G
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024 * 1024 * 1024);
        System.out.println();
        ((DirectBuffer) buffer).cleaner().clear();
        TimeUnit.SECONDS.sleep(100);

        System.out.println("ok");
    }
}
