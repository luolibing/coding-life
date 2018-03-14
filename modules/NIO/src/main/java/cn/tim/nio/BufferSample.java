package cn.tim.nio;

import org.junit.Test;

import java.nio.Buffer;
import java.nio.ByteBuffer;

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
        // 上界，现有元素的计数
        System.out.println(buffer.limit());
        // 写一个要背读写的位置
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
}
