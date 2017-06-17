package cn.tim.thinking.string;

import org.junit.Test;

/**
 * Created by LuoLiBing on 16/12/21.
 *
 * StringBuilder
 * 一个可变的字符序列, 这个类提供了一个兼容StringBuffer的API, 但是不保证同步性.
 * 作为一个在单线程下替换StringBuffer, 在单线程环境下, 提供比StringBuilder更好的性能.
 * 主要的操作是StringBuilder.append(); insert()等, 重载了大量的方法,以支持append()各种数据类型的append();
 * append()总是将字符添加到Builder的末尾, 而insert()可以将其插入到指定(包含)的位置.
 *
 * StringBuilder拥有一个容量capacity字段, 只要字符序列的长度没有超过容量的大小则不会扩容, 反之扩容.
 * 当字符序列溢出时,并不需要手动指定一个新的内部缓冲, 它会自动扩容.
 *
 * StringBuilder是线程不安全的, StringBuffer是线程安全的, 不能往构造函数中传入null值,否则会抛出空指针.
 * append(null)的话,会把null当成null字符串看待.
 *
 * StringBuilder默认容量为16.
 * StringBuilder有一个char[]数组, 和一个字符串长度count.
 *
 * StringBuilder.toString() 和 StringBuffer.toString()之间的区别.
 * StringBuilder会将char[]传入一个new String()构造器, 直接返回
 * StringBuffer因为加入了同步, 所以考虑到性能问题, 添加了一个toStringCache, 当toString()的时候判断是否有cache,
 * 如果没有则创建这个cache, 然后如果之后没有任何改动则, 下次toString()的时候直接返回cache, 如果有改动,则将cache置为null
 *
 */
public class StringBuilderDemo {

    @Test
    public void append() {
        StringBuilder sb = new StringBuilder("luolibing");
        sb.append("liuxiaoling");
        System.out.println(sb);

        StringBuilder s = new StringBuilder("luolibing");
        s.insert(0, "liuxiaoling");
        System.out.println(s);

        StringBuilder s1 = new StringBuilder("luolibing");
        s1.insert(s1.length(), "liuxiaoling");
        System.out.println(s1);
    }

    @Test
    public void capacity() {
        StringBuilder builder = new StringBuilder(100);
        System.out.println("capacity = " + builder.capacity());
    }


    /**
     * 当插入字符串时, 先判断插入之后, 大小比当前容量是否大, 如果大的话, 需要进行扩容.
     * 扩容的时候, 默认扩容为 capacity * 2 + 2;
     * 如果还不够,则判断要求的最小容量和扩容之后的关系来决定. 扩容为最小容量, 还是最大的Integer值.
     * 最后进行字符数组的拷贝.
     */
    @Test
    public void ensureCapacity() {
//        int newCapacity = value.length * 2 + 2;
          // 当前大小是否复合最小的要求, 如果不符合要求, 将newCapacity直接设置为最小要求
//        if (newCapacity - minimumCapacity < 0)
//            newCapacity = minimumCapacity;
          // 判断newCapacity是否已经溢出
//        if (newCapacity < 0) {
              // 再判断minimumCapacity是否溢出, 如果没有溢出, 则使用最大的Integer值
//            if (minimumCapacity < 0) // overflow
//                throw new OutOfMemoryError();
//            newCapacity = Integer.MAX_VALUE;
//        }
//        value = Arrays.copyOf(value, newCapacity);
    }
}
