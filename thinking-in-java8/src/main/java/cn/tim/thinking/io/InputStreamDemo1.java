package cn.tim.thinking.io;

/**
 * Created by LuoLiBing on 16/12/1.
 * InputStream和Reader派生出来的类都含有read()方法,用于读取单个字节或者字节数组.
 * OutputStream和Writer派生出来的类都含有write()方法,用于写单个字节或者字节数组.
 *
 * InputStream的作用是用来表示那些从不同数据源产生输入的类.这些数据源包括:
 * 1 字节数组                      ByteArrayInputStream
 * 2 String对象                   StringBufferInputStream
 * 3 文件                         FileInputStream
 * 4 管道                         PipedInputStream
 * 5 一个由其他种类的流组成的序列,   SequenceInputStream
 *  以便将他们收集合并到一个流内
 * 6 其他数据源
 *
 * FilterInputStream也属于一种InputStream,为装饰器类提供基类
 * 通过继承FilterInputStream或者FilterOutputStream来提供多层装饰的能力
 *
 * FilterInputStream
 *
 * DataInputStream              从流中读取基本数据类型
 * BufferedInputStream          使用缓冲区进行写操作
 * LineNumberInputStream        跟踪输入流中的行号
 * PushbackInputStream          具有"能弹出一个字节的缓冲区".因此可以将读到的最后一个字符回退
 *
 */
public class InputStreamDemo1 {
}
