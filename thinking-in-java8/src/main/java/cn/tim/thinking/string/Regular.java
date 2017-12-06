package cn.tim.thinking.string;

import org.junit.Test;

import java.util.Arrays;

/**
 * Created by LuoLiBing on 16/12/22.
 * 正则表达式
 *
 * String类自带的正则表达式, 有matches(), split(), replace()
 */
public class Regular {

    /**
     * 在Java中\\表示插入一个正则表达式的反斜线,所以后面的字符串具有特殊的意义. 而在其他语言中, \\表示我想要在正则表达式中插入一个普通的反斜线.
     * 例如一个数字使用\\d, 要表示反斜线, 使用\\\\表示, 不过换行和制表符之类的东西只需使用单反斜线: \n \t.
     *
     * 要表达一个或者多个之间的表达式, 使用+.
     * 要找一个数字, 它前面可能有一个符号, 可以使用 -?
     * 数字的开始有可能是-或者+, 要表示或使用 |   (-|\\+)?. 因为+在正则表达式中有特殊意义, 所以需要使用\\+
     */
    @Test
    public void test1() {
        String str = "1245678";
        // 一个或者多个正数字
        System.out.println(str.matches("\\d+"));

        // 一个或者多个负数
        System.out.println("-113435".matches("-\\d+"));

        // 判断是数字
        System.out.println("-12435".matches("-?\\d+"));

        // 匹配反斜线\
        System.out.println("\\".matches("\\\\"));

        // 匹配正数(可能带+号)或者负数
        System.out.println("+124556".matches("(-|\\+)?\\d+"));
    }

    static String knights = "Then, when you have  found the  shrubbery,  you must cut down the mightiest tree in the forest... with... a herring!";

    public static void split(String regex) {
        System.out.println(Arrays.toString(knights.split(regex, 10)));
        System.out.println(Arrays.toString(knights.split(regex)));
    }

    /**
     * split()还可以限制字符串分割的次数
     */
    @Test
    public void split() {
        split(" ");
        split("\\W+"); // 非单词字符,  \\w+ 表示单词字符
        split("n\\W+"); // n后跟着非单词字符,
    }


    /**
     * 正则表达式工具替换.
     */
    @Test
    public void replace() {
        // 替换首个
        System.out.println(knights.replaceFirst("f\\w+", "located"));
        // 替换所有以下单词为banana
        System.out.println(knights.replaceAll("shrubbery|tree|herring", "banana"));

        System.out.println("abce".matches("\\w+"));

        // 以大写开头
        System.out.println("Abcd".matches("[A-Z]\\w+"));

        System.out.println("abcd".matches("[a-z]\\w+"));

        System.out.println(Arrays.toString(knights.split("the|you")));

        System.out.println(knights.replaceAll("a|i|o|u|e", "_"));

        System.out.println(6 * 7);
    }
}
