package cn.tim.nio;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by luolibing on 2018/3/23.
 */
public class FileChannelSample {

    /**
     * fileChannel的大多数操作时线程安全的，但是影响通道位置和影响文件大小的操作都是单线程的。
     *
     */
    @Test
    public void fileHole() throws IOException {
        /**
         * 文件空洞，现代操作系统只会为实际写入的数据分配磁盘空间
         */
        File temp = File.createTempFile("holy", null);
        RandomAccessFile file = new RandomAccessFile(temp, "rw");
        FileChannel channel = file.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(100);
        putData(1, byteBuffer, channel);
        putData(10000, byteBuffer, channel);
        putData(5000, byteBuffer, channel);

        System.out.println("file path = " + temp.getPath() + ", file size = "
                + file.length() + ", channel length = " + channel.size());
    }

    private void putData(int position, ByteBuffer byteBuffer, FileChannel fileChannel) throws IOException {
        byteBuffer.clear();
        String str = "*------> " + position;
        byteBuffer.put(str.getBytes());
        byteBuffer.flip();

        // 指定通道的位置
        fileChannel.position(position);
        // 写入到通道
        fileChannel.write(byteBuffer);
    }

    @Test
    public void resize() throws IOException {
        File file = new File("/var/folders/q1/f3c1626s21l0hyc_wfxm20540000gn/T/holy2141668058151328866.tmp");
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
        randomAccessFile.setLength( 1024 * 1024 * 10);
    }


    @Test
    public void op() throws IOException {
        // FileChannel的position是从底层文件描述符获得的，所以FileChannel和randomAccess共享的同一个position
        File randomFile = File.createTempFile("random", null);
        RandomAccessFile randomAccessFile = new RandomAccessFile(randomFile, "rw");
        FileChannel channel = randomAccessFile.getChannel();

        randomAccessFile.seek(1024);
        System.out.println("channel position = " + channel.position());

        channel.position(2048);
        System.out.println("randomAccessFile position = " + randomAccessFile.getFilePointer());

        // 不同的文件描述符，自然不能通用同一个position
        File randomFile2 = new File(randomFile.getPath());
        RandomAccessFile randomAccessFile2 = new RandomAccessFile(randomFile2, "rw");
        System.out.println(randomAccessFile2.getFilePointer());
    }

    @Test
    public void truncate() throws IOException {
        File randomFile = File.createTempFile("random", null);
        RandomAccessFile randomAccessFile = new RandomAccessFile(randomFile, "rw");
        randomAccessFile.setLength(1024 * 1024 * 5);
        FileChannel channel = randomAccessFile.getChannel();
        channel.truncate(1024);

        System.out.println(channel.position());
        // 强制写入，相当于立即同步到文件系统， false表示不立即同步文件源信息，因为同步一次源信息，还得一次io交互
        channel.write(ByteBuffer.wrap("hello, world".getBytes()));
        channel.force(false);
    }

}
