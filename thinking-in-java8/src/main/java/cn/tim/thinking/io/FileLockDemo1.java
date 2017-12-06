package cn.tim.thinking.io;

import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.concurrent.TimeUnit;

/**
 * Created by LuoLiBing on 16/12/8.
 * 文件加锁
 * JDK1.4引入了文件加锁机制,允许我们同步访问某个作为共享资源的文件.
 * 文件锁对其他的操作系统是可见的,因为java的文件加锁直接映射到本地操作系统的加锁工具
 *
 * 与对象一样lock是阻塞式的,tryLock尝试获取锁
 * 当当前虚拟机已经锁住了一个文件,再次尝试锁住该文件会抛出OverlappingFileLockException异常.
 * 所以同一个JVM中使用tryLock或者lock时都有可能会抛出OverlappingFileLockException异常.
 * 不同的jvm上面的lock和tryLock才会起作用.
 *
 * 共享锁: 共享读操作,但只能一个写
 * 独占锁: 只有一个读或一个写.
 *
 */
public class FileLockDemo1 {

    @Test
    public void fileLocking() throws IOException, InterruptedException {
        new Thread(new LockFileTask()).start();
//        new Thread(new LockFileTask()).start();
//        new Thread(new LockFileTask()).start();
        TimeUnit.SECONDS.sleep(40);
    }

    static class LockFileTask implements Runnable {

        @Override
        public void run() {
            try {
                FileLock lock = new FileOutputStream("file1.txt").getChannel().tryLock();
                System.out.println("lock = " + lock);
                for(int i = 0; i < 30; i++) {
                    System.out.println("wait " + i + "s");
                    TimeUnit.SECONDS.sleep(1);
                }
                lock.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 文件部分锁定: 指定文件锁的指定部分
     *
     *
     */
    static class PartLockFileTask implements Runnable {

        private long position;

        private long size;

        private boolean shared = false;

        public PartLockFileTask(long position, long size) {
            this.position = position;
            this.size = size;
        }

        @Override
        public void run() {
            try {
                FileChannel fc = new FileOutputStream("file1.txt").getChannel();
                // 对于只写文件不允许使用共享锁 NonReadableChannelException
                FileLock lock = fc.lock(position, size, shared);
                System.out.println("lock = " + lock);
                for(int i = 0; i < 30; i++) {
                    System.out.println("wait " + i + "s");
                    TimeUnit.SECONDS.sleep(1);
                }
                lock.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void partLock() throws InterruptedException {
        new Thread(new PartLockFileTask(0, 2)).start();
        TimeUnit.SECONDS.sleep(50);
    }


    /**
     * 对映射文件的部分加锁
     * 对于巨大文件进行部分加锁,以便其他进程可以修改文件中未加锁的部分.例如数据库就是这样,因此多个用户可以同时访问到它
     */
    static class LockingMappedFiles {
        final static int LENGTH = 0x8FFFFFF; // 128MB
        static FileChannel fc;

        private static class LockAndModify extends Thread {
            private ByteBuffer buff;
            private int start, end;

            LockAndModify(ByteBuffer mbb, int start, int end) {
                this.start = start;
                this.end = end;
                mbb.limit(end);
                mbb.position(start);
                // 复制一个ByteBuffer,包含一个ByteBuffer指定的position到limit部分
                this.buff = mbb.slice();
                start();
            }

            @Override
            public void run() {
                try {
                    // lock(position, size, shared); 当前位置,锁定区域大小
                    FileLock fl = fc.lock(start, end, false);
                    System.out.println("Locked: " + start + " to " + end);
                    //
                    while (buff.position() < buff.limit() - 1) {
                        buff.put((byte) (buff.get() + 1));
                    }
                    fl.release();
                    System.out.println("Released: " + start + " to " + end);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        public static void main(String[] args) throws IOException {
            fc = new RandomAccessFile("test.dat", "rw").getChannel();
            // 映射文件
            MappedByteBuffer byteBuffer = fc.map(FileChannel.MapMode.READ_WRITE, 0, LENGTH);
            for(int i = 0; i < LENGTH; i++) {
                byteBuffer.put((byte) 'x');
            }

            new LockAndModify(byteBuffer, 0, LENGTH / 3);
            new LockAndModify(byteBuffer, LENGTH / 2, LENGTH / 2 + LENGTH / 4);
        }
    }


    @Test
    public void readMysqlDataFile() throws IOException {
        int LENGTH = 1024 * 100;
        FileChannel fc = new RandomAccessFile("/usr/local/var/mysql/course_online_1/work_excel.ibd", "rw").getChannel();
        MappedByteBuffer byteBuffer = fc.map(FileChannel.MapMode.READ_ONLY, 0, LENGTH);
        byte[] buffer = new byte[224];
        for(int i = 0; i < 1000; i++) {
            byteBuffer.get(buffer, 0, 224);
            System.out.println("i=" + i+ ",  " +  new String(buffer));
        }
    }

}
