package cn.tim.thinking.string;

import org.junit.Test;

import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by LuoLiBing on 16/12/21.
 * String类
 * 代表字符串, java中所有字面量, 例如"acb", 都是String实例. String是一个常量, 创建之后值就不能再改变, String Buffer(Builder)提供了可变字符串.
 * 因为String是不可改变的, 所以可以共享. String str = "abc"; 等同于 char[] data = {'a', 'b', 'c'}; String str = new String(data);
 *
 * String类提供了许多字符串序列的方法, 例如字符串比较, 搜索, 抽取子串,大小写转换等. jvm对String进行了操作符+重载, 调用toString()将其他对象转换为string对象.
 * 连接字符串通过StringBuilder或者StringBuffer的append()来实现. 在JVM内部是使用UTF-16编码进行编码的. 而默认的编码defaultCharset()则是根据操作系统来的,是表示从文件读取数据时的格式这种.
 *
 * String类:
 * value[]  char数组, final型
 * hash     默认为0
 *
 *
 *
 */
public class StringClass {


    @Test
    public void encode() {
        String str = "abcd";
        System.out.println(Arrays.toString(str.getBytes()));
        System.out.println(Arrays.toString(str.getBytes(Charset.forName("UTF-16"))));
        System.out.println(Arrays.toString(str.getBytes(Charset.forName("UTF-8"))));
        System.out.println(Charset.forName("UTF-8").encode(str));
        System.out.println(Charset.defaultCharset());
    }



    /**
     * 以下方法, 当需要改变字符串的内容时,String类的方法都会返回一个新的string对象.
     * 同时, 如果内容没有发生改变, string的方法只是返回指向原对象的引用而已. 这样可以节约存储空间以及避免额外的开销.
     */
    @Test
    public void method() {
        // 重载构造方法String, StringBuffer, StringBuilder, char[], byte[]等
        String str = new String("abcdefghijklmnopqrstuvwxyz".getBytes());
        System.out.println(str.length());

        // 返回指定索引值下的字符
        System.out.println(str.charAt(2));
        char[] target = new char[10];
        byte[] data = new byte[10];

        // 复制char或者byte到一个指定数组
        str.getChars(0, 10, target, 0);
        str.getBytes(0, 10, data, 0);
        System.out.println(Arrays.toString(target));
        System.out.println(Arrays.toString(data));

        // 比较内容
        System.out.println(str.contentEquals(new StringBuffer("abcdefghijklmnopqrstuvwxyz")));

        // regionMatches局部是否相同, 参数ignoreCase(大小写忽略), offset(偏移量),str(比较的字符串), startOffset(起始位置), len(比较长度)
        System.out.println("regionMatches = " + str.regionMatches(true, 1, "bbcdefg", 1, 3));

        System.out.println("startsWith = " + str.startsWith("abc"));

        System.out.println("endsWith = " + str.endsWith("xyz"));

        // 返回索引值
        System.out.println("indexOf = " + str.indexOf("p"));
        System.out.println("lastIndexOf = " + str.lastIndexOf("z"));

        // str.substring(beginIndex, endIndex), 包含begin,不包含endIndex
        System.out.println(str.substring(0, 2));

        // concat()连接字符串
        System.out.println("words".concat(str));

        // replace替换
        System.out.println(str.replace("a", "A"));

        // 大小写转换
        System.out.println(str.toUpperCase());

        // valueOf()
        System.out.println(String.valueOf(1000));

        // intern() 为每个唯一的字符序列生成一个且仅生成一个String引用
        System.out.println(str.intern());
    }


    /**
     * printf提供了类似C语言的占位符功能.
     */
    @Test
    public void pFormat() {
        System.out.println("Row 1: [" + 100 + " " + 100.02f + "]" );
        System.out.printf("Row 1: [%d %f]\n", 100, 100.02f);
        System.out.format("Row 1: [%d %f]\n", 100, 100.02f);
    }

    @Test
    public void two() {
        String str1 = "abc";
        String str2 = "abc";
        String str3 = new String("abc");
        System.out.println(str1 == str2);
        System.out.println(str1 == str3);
    }


    /**
     * String是不可变的, 仅仅意味着你不能使用publicAPI来改变它, 如果使用反射, 同样可以改变枚举的值, 自动拆装箱的值
     * s1和s2值被改变, 是因为他们都指向了一样的内部字符串, 这是在编译期就已经指定的, 常量池中会有Hello World. s1和s2都指向了这个字面量常量
     * s3为什么会产生一个新的String对象, 这是因为代码中使用了Arrays.copyOfRange() 这个地方进行了拷贝(如果有改变的时候会拷贝,没有改变的时候还是返回之前的对象).
     * java没有办法将数组作为不可变, 所以不要把数组暴露在类的外部,即使有适当的访问修饰符
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    @Test
    public void immutable1() throws NoSuchFieldException, IllegalAccessException {
        String s1 = "Hello World";
        String s2 = "Hello World";
        String s3 = s1.substring(6);
        System.out.println(s1);
        System.out.println(s2);
        System.out.println(s3);

        Field value = String.class.getDeclaredField("value");
        value.setAccessible(true);
        char[] data = (char[]) value.get(s1);
        data[1] = 'l';
        data[2] = 'a';
        data[3] = 'b';

        System.out.println("\nafter update value");
        System.out.println(s1);
        System.out.println(s2);
        System.out.println(s3);
    }

    // TODO 自动装箱 -128 to 127
    @Test
    public void destoryAutoboxed() throws NoSuchFieldException, IllegalAccessException {
//        Integer i = null;
//        System.out.println(i == 10);
        Field value = Integer.class.getDeclaredField("value");
        value.setAccessible(true);
        // value.set(obj, val); 32为Integer对象. 这里自动装箱Integer.valueOf(int), 一个字节以内可以代表的整数, 都会被缓存起来, 这里设置一次之后, 之后因为-127-128都是使用的这个缓存,
        // 所以之后的结果都是这次设置的结果. 而除此之外的值, 则会每次自动装箱时new Integer, 所以这里设置的不管用.
        value.set(32, 100);
        value.set(64, 100);
        value.set(128, 200);
        new Thread(() -> {
            System.out.format("8 * 8 = %d", 8 * 8);
            System.out.println();
            System.out.format("4 * 8 = %d", 4 * 8);
            System.out.println();
            System.out.printf("64 * 2 =%d", 64 * 2);
            System.out.println();
            Integer val = 8 * 8;
            System.out.println(val);
        }).start();
    }

    @Test
    public void sort() {
        String[] arr = new String[] {"10", "1", "2", "20", "5", "40"};
        Arrays.sort(arr, Comparator.comparingInt(Integer::valueOf));
        System.out.println(Arrays.toString(arr));
    }
}
