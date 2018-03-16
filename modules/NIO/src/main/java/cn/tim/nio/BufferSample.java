package cn.tim.nio;

import org.junit.Test;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.stream.IntStream;

/**
 * Created by luolibing on 2018/3/14.
 */
public class BufferSample {


    /**
     * 属性
     */
    @Test
    public void bufferAttr() {
        Buffer buffer = ByteBuffer.allocate(1024);
        // 容量
        System.out.println(buffer.capacity());
        // 上界
        System.out.println(buffer.limit());
        // 下一个要被读写的位置
        System.out.println(buffer.position());
        // 备忘的位置
        System.out.println(buffer.mark());
    }

    @Test
    public void putGet() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        // if i > capacity throws BufferOverflowException
        for(int i = 0; i < 1000; i++) {
            byteBuffer.put((byte) i);
        }

        byteBuffer.flip();
        // if i > limit throws BufferUnderflowException
        for(int i = 0; i < 1000; i++) {
            // 相对存储
            System.out.println(byteBuffer.get());
        }

        System.out.println(byteBuffer.limit());
        // 绝对存储，超出上界抛出IndexOutOfBoundsException
        byteBuffer.put(1024, (byte) 100);
    }

    @Test
    public void byteBuffer() {
        // unicode码只占用两个字节，当这里强转为Byte时，会截掉前8位，只取后八位
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        System.out.println(String.format("capacity=%s, limit=%s, position=%s",
                byteBuffer.capacity(), byteBuffer.limit(), byteBuffer.position()));

        byteBuffer.put("Hello".getBytes());
        System.out.println(String.format("capacity=%s, limit=%s, position=%s",
                byteBuffer.capacity(), byteBuffer.limit(), byteBuffer.position()));

        byteBuffer.put(0, (byte) 'M');
        byteBuffer.put((byte) 'w');
        System.out.println(String.format("capacity=%s, limit=%s, position=%s",
                byteBuffer.capacity(), byteBuffer.limit(), byteBuffer.position()));

        byteBuffer.flip();
        System.out.println(String.format("capacity=%s, limit=%s, position=%s",
                byteBuffer.capacity(), byteBuffer.limit(), byteBuffer.position()));
    }

    @Test
    public void flip() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(100);
        byteBuffer.put("hello".getBytes());
        byteBuffer.position(0);
        // 这里会一致读取，直到上界
        IntStream.range(0, 100).forEach(i -> System.out.println(byteBuffer.get()));

        ByteBuffer byteBuffer1 = ByteBuffer.allocate(100);
        byteBuffer1.put("hello".getBytes());
        // 然后将上界定到当前位置，再将当前位置定位0
        byteBuffer1.limit(byteBuffer1.position()).position(0);

        // 等同于byteBuffer.limit(byteBuffer.position()).position(0);
        // Rewind()函数只是将当前位置置为0，不设置上界
        byteBuffer1.flip(); // 两次flip，最终position=0, limit = 0
        System.out.println(String.format("capacity=%s, limit=%s, position=%s",
                byteBuffer1.capacity(), byteBuffer1.limit(), byteBuffer1.position()));
    }

    @Test
    public void remaining() {
        ByteBuffer byteBuffer = ByteBuffer.wrap("Hello".getBytes());
        System.out.println(String.format("capacity=%s, limit=%s, position=%s",
                byteBuffer.capacity(), byteBuffer.limit(), byteBuffer.position()));

        // 是否到达上界
        while (byteBuffer.hasRemaining()) {
            System.out.println(byteBuffer.get());
        }

        // 当前位置到上界还剩余多少元素
        System.out.println(byteBuffer.remaining());

        // clear并不清除数据，只是将position置为0，将limit置为capacity
        byteBuffer.clear();
        System.out.println("after clear");
        while (byteBuffer.hasRemaining()) {
            System.out.println(byteBuffer.get());
        }

        // 不能再次扩大超过capacity，抛出IllegalArgumentException异常
        byteBuffer.limit(1024);
        System.out.println(String.format("capacity=%s, limit=%s, position=%s",
                byteBuffer.capacity(), byteBuffer.limit(), byteBuffer.position()));
    }

    @Test
    public void bufferFillDrain() {
        CharBuffer buffer = CharBuffer.allocate(100);
        // 填充
        while (fillBuffer(buffer)) {
            // 读取之前翻转，将当前位置置为0，Limit为最后一个元素之后
            buffer.flip();
            // 读取
            drainBuffer(buffer);
            // clear 将当前位置置0，limit置容量位，方便下次写入
            buffer.clear();
        }
    }

    private void drainBuffer(CharBuffer charBuffer) {
        while (charBuffer.hasRemaining()) {
            System.out.print(charBuffer.get());
        }
        System.out.println();
    }

    private boolean fillBuffer(CharBuffer charBuffer) {
        if(index >= strings.length) {
            return false;
        }

        String str = strings[index++];
        IntStream.range(0, str.length()).forEach(i -> charBuffer.put(str.charAt(i)));
        return true;
    }

    private static int index = 0;

    private static String [] strings = {
            "A random string value",
            "The product of an infinite number of monkeys",
            "Hey hey we're the Monkees",
            "Opening act for the Monkees: Jimi Hendrix",
            "'Scuse me while I kiss this fly", // Sorry Jimi ;-)
    };
}
