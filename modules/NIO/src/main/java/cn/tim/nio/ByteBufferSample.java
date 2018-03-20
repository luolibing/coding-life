package cn.tim.nio;

import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/**
 * Created by luolibing on 2018/3/20.
 */
public class ByteBufferSample {

    @Test
    public void byteOrder() {
        // 字节顺序，我这个mac2017上使用的是小端顺序
        // 大端顺序指的是高位在前，低位在后，与人类读取顺序一致；而小端则正好相反
        // 各自的优势，大端顺序很容易判断正负值，因为正负号在高位；小端顺序很好进行数据转换，因为只需要将前几位传给对方即可
        ByteOrder byteOrder = ByteOrder.nativeOrder();
        System.out.println(byteOrder);

        ByteBuffer byteBuffer = ByteBuffer.wrap("hello".getBytes());
        System.out.println(byteBuffer.order());
        System.out.println(Arrays.toString(byteBuffer.array()));

        // 创建之后，再进行改变也不会影响到当前的buffer
        // TODO 如何修改buffer的字节缓冲区
        ByteBuffer order = byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        System.out.println(Arrays.toString(order.array()));

        ByteBuffer allocate = ByteBuffer.allocate(100);
        allocate.order(ByteOrder.LITTLE_ENDIAN);
        allocate.put("hello".getBytes());
        System.out.println(Arrays.toString(allocate.array()));
    }
}
