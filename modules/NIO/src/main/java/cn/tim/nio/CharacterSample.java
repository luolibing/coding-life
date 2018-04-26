package cn.tim.nio;

import org.junit.Test;

import java.nio.charset.Charset;

/**
 * Created by luolibing on 2018/4/17.
 */
public class CharacterSample {

    /**
     * 字符集， 字符的集合。没有内在数字价值。英文字母符号，中文
     * 编码字符集，将数值赋给一个字符的集合。例如USASCII, ISO-8859-1,Unicode
     * 字符编码方案，编码字符集到八位字节的映射。字符集的编码与解码视为序列化与反序列化。字符数据编码用于网络传输和文件传出。
     */
    public void init() {

    }

    @Test
    public void allCharsets() {
        Charset.availableCharsets().keySet().forEach(System.out::println);
    }
}
