package cn.tim.nio;

/**
 * Created by luolibing on 2018/3/13.
 */
public class NioBasics {

    /**
     * 缓冲区操作
     * read()操作过程：
     * 1 jvm进程使用read()系统调用，要求把缓冲区填满
     * 2 内核向硬盘控制器硬件发出命令，要求其从磁盘读取数据。（用户空间无法直接向硬件发指令，属于非特权空间）
     * 3 磁盘控制器通过DMA将输入直接写入到内核内存缓冲区
     * 4 内核内存缓冲区写满，内核即把数据从内核空间缓冲区拷贝到进程执行read()指定的缓冲区
     * 5 完成read()
     */
    public void bufferOp() {

    }


    /**
     * 发散与汇聚
     * 在之前缓冲区read的基础上，进程只需要一个系统调用，把一连串缓冲区地址传递给操作系统（不必执行多次系统调用）。
     * DMA先将所有缓冲区要的数据写入到内核缓冲区。然后再讲数据顺序填充到对应的缓冲区中，
     * 如果有多个cpu填充工作可以同时进行，加快速度。 这一过程就是发散
     *
     */
    public void scatter() {

    }


    /**
     * 虚拟内存
     * 1 一个以上虚拟地址可指向同一个物理内存地址
     * 2 虚拟内存空间可大于实际可用的硬件内存
     *
     * 将用户空间缓冲区地址和内核空间地址映射到同一个虚拟地址，这样就可以省去内核与用户空间的往来拷贝
     *
     * 对于第2条，则使用虚拟内存分页技术实现，涉及到内存与磁盘设备交换，缺页错等等操作系统相关的技术
     *
     */
    public void vri() {

    }
}
