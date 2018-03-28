package cn.tim.nio;

import org.junit.Test;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Created by luolibing on 2018/3/26.
 */
public class FileLockSample {

    /**
     * 文件锁
     * 是作用在文件上，而不是通道上，所以同一个jvm进程中的不同线程访问不会有问题，而不同jvm进程访问会有问题
     */
    @Test
    public void lock() throws IOException {
        FileChannel channel = FileChannel.open(Paths.get("/Users/luolibing/Documents/apache-maven-3.3.9/README.txt"), StandardOpenOption.WRITE);
        FileLock lock = channel.lock();
        try {
            System.out.println("aaaa");
        } catch (Exception e) {

        } finally {
            lock.release();
        }
    }


    @Test
    public void tryLock() throws IOException {
        FileChannel channel = FileChannel.open(Paths.get("/Users/luolibing/Documents/apache-maven-3.3.9/README.txt"), StandardOpenOption.WRITE);
        FileLock lock = channel.tryLock();
        try {
            System.out.println(lock.isValid());
            System.out.println("aaaa");
        } catch (Exception e) {

        } finally {
            lock.release();
        }
    }
}
