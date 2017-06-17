package cn.tim.thinking.string;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//\xhh     十六进制值为oxhh的字符
/**
 * Created by LuoLiBing on 16/12/22.
 * String只是自带了少许正则表达式功能, java.util.regex包中功能更全.
 *
 * 字符
 * \\B      指定字符B, \\B非词边界
 * \\b      指定字符, \\b词边界
 * \t       制表符tab
 * \n       换行符
 * \r       回车
 * \f       换页
 * \e       转义
 *
 *
 * 字符类的典型方式
 * .        任意字符
 * [abc]    包含a,b和c的任意字符(和a|b|c作用相同)
 * [^abc]   除了a,b和c之外的任意字符
 * [a-zA-Z] 从a到z或A到Z的任意字符(范围)
 * [abc[hij]] 任意a,b,c,h,i和j字符(与a|b|c|h|i|j相同)(合并)
 * [a-z&&[hij]] 任意h,i,j(交)
 * \s       空白符
 * \S       非空白符
 * \d       数字[0-9]
 * \D       非数字[^0-9]
 * \w       词字符[a-zA_Z0-9]
 * \W       非词字符[^\w]
 *
 *
 * 逻辑操作符
 * XY       Y跟着X后面
 * X|Y      X或Y
 * (X)      捕获组
 *
 *
 * 边界匹配符
 * ^        一行的起始
 * $        一行的结束
 * \b       词的边界
 * \B       非词的边界
 * \G       前一个匹配的结束
 */
public class Regex {

    @Test
    public void rudolph() {
        for(String pattern : new String[] {"Rudolph", "[rR]udolph", "[rR][aeiou][a-z]ol.*", "R.*"}) {
            System.out.println(pattern  + " = " + "Rudolph".matches(pattern));
        }
    }


    /**
     * 量词: 描述了一个模式吸收输入文本的方式
     * 贪婪型: 量词总是贪婪的, 除非有其他的选项被设置. 贪婪表达式会为所有可能的模式发现尽可能多的匹配.
     * 勉强性: 用问好来制定, 这个量词匹配满足模式所需的最少字符数. 因此也称作懒惰的, 最少匹配的, 非贪婪的
     * 占有型: 当正则表达式被应用于字符串时, 它会产生相当多的状态,以便在匹配失败时可以回溯. 而占有的量词并不保存这些中间状态,因此他们可以防止回溯.
     *
     * 贪婪型, 勉强性, 占有型
     * X?, X??, X?+             一个或零个X
     * X*, X*?, X*+             零个或多个X
     * X+, X+?, X++             一个或者多个X
     * X{n}, X{n}?, X{n}+       恰好n次X
     * X{n,}, X{n,}?, X{n,}+    至少n次X
     * X{n,m}, X{n,m}?, X{n,m}+ X至少n次,且不超过m次
     *
     * X通常使用()括号括起来
     */
    @Test
    public void test() {
        String str = "abcabcabc";
        System.out.println(str.matches("(abc)+"));
    }


    /**
     * CharSequence从CharBuffer, String, StringBuffer, StringBuilder类之中抽象出了字符序列的一般化定义
     */
    @Test
    public void charSequence() {
        CharSequence str = "abcd";
    }


    public static void match(String regex, String src) {
        System.out.println();
        System.out.println("Regular expression: \"" + regex + "\"");
        // 先对正则表达式进行编译
        Pattern pattern = Pattern.compile(regex);
        // 然后对目标字符串进行matcher获取到一个Matcher对象
        Matcher m = pattern.matcher(src);
        while (m.find()) {
            System.out.println("Match \"" + m.group()
                    +"\" at position " + m.start() + "-" + (m.end()-1));
        }
    }

    /**
     * Pattern和Matcher
     */
    @Test
    public void testRegularExpression() {
        String str = "abcabcabcabcdabcdfabc";
        // 查找指定字符串abc
        match("abc", str);
        // 一个或者多个字符串abc
        match("(abc)+", str);
        // 至少2次字符串abc
        match("(abcd){2,}", str);
        // 正好匹配2次
        match("(abcd){2}", str);

        match("(abcd){1}", str);
    }


    @Test
    public void pattern1() {
        String str = "abcabcabcabcdabcdfabc";
        Pattern p = Pattern.compile("(abcde)+");

        // split分割
        System.out.println("split = " + Arrays.toString(p.split("abc")));
    }

    @Test
    public void matcher1() {
        String str = "abcabcabcabcdabcdfabc";
        Pattern p = Pattern.compile("(abcde)+");
        Matcher m = p.matcher(str);

        // 方法1 判断整个字符串是否匹配正则表达式模式
        System.out.println("matches = " + m.matches());

        // 方法2 判断开始部分是否能够匹配
        System.out.println("lockingAt = " + m.lookingAt());

        // 是否还有下一个
        System.out.println("find = " + m.find());

        // 从索引值10开始查找是否能够获取到
        System.out.println("find = " + m.find(10));

        Integer i = null;

        // Integer会自动拆箱,调用intValue()方法, 所以当i等于null时, 会抛出异常  Integer.intValue. bug隐藏很深
        System.out.println(1 == i);
    }

    @Test
    public void regular2() {
        String words = "Java now has regular expressions";
        for(String p : new String[] {
                "^Java",                // 以Java开始
                "\\Breg.*",             // 非词边界
                "n.w\\s+h(a|i)s",       // n.w=now(一个或多个空格)h(a或i)s
                "s?",                   // s?一个或者零个s
                "s*",                   // s*零个或多个s
                "s+",                   // s+一个以上的s
                "s{4}",                 // 4个s
                "s{1}",                 // 1个s
                "s{0,3}"}) {            // 最少0个s, 最多不大于3个s
            System.out.println("regular expressions = " + p);
            Pattern pattern = Pattern.compile(p);
            Matcher m = pattern.matcher(words);
            System.out.println(m.find());
        }
    }

    @Test
    public void regular3() {
        String words = "Arline ate eight apples and one orange while Anita hadn't any";
        String regex = "(?i)((^[aeiou])|(\\s+[aeiou]))\\w+?[aeiou]\b";
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(words);
        System.out.println(m.find());
    }


    /**
     * find()可用来在CharSequence中查找多个匹配.
     */
    @Test
    public void finding() {
        // \\w+ 将字符串划分为单词. find()像迭代器那样前向遍历输出字符串.
        Matcher m = Pattern.compile("\\w+").matcher("Evening is full of the linnet's wings");
        while (m.find()) {
            System.out.println(m.group() + " ");
        }
        System.out.println();
        int i = 0;
        while (m.find(i)) {
            System.out.println(m.group() + " ");
            i++;
        }
    }


    /**
     * Groups组
     * 组是用括号划分的正则表达式,可以根据组的编号来引用某个组, 组号为0表示整个表达式, 组号1表示被第一队括号括起的组.
     * A(B(C))D, 中有三个组: 组0是ABCD, 组1是BC, 组2是C
     *
     * Matcher类
     * groupCount()     返回分组数目, 第0组不包括在内.
     * group()          返回前一次匹配操作的第0组(整个匹配)
     * group(i)         返回前一次匹配操作期间制定的组号
     * start(i)         返回前一次匹配操作中寻找到的组的起始索引
     * end(i)           返回在前一次匹配操作中寻找到的组的最后一个字符索引加一的值
     *
     */
    @Test
    public void groups() {
        String POEM = "Twas brillig，and the slithy toves Did gyre and gimble in the wabe； All mimsy were the borogoves， And the mome raths outgrabe. 'Beware the Jabberwock，my son！ The jaws that bite，the claws that catch！ Beware the Jubjub bird，and shun The frumious Bandersnatch！' He took his vorpal sword in hand： Long time the manxome foe he sought So rested he by the Tumtum tree， And stood awhile in thought. And as in uffish thought he stood. The Jabberwock，with eyes of flame， Came whiffling through the tulgey wood， And burbled as it came！ One，two！One，two！And through and through The vorpal blade went snicker-snack！ He left it dead，and with its head He went galumphing back. 'And thou hast slain the Jabberwock？ Come to my arms，my beamish boy！ O frabjous day！Callooh！Callay！' He chortled in his joy. 'Twas brillig，and the slithy toves Did gyre and gimble in the wabe； All mimsy were the borogoves， And the mome raths outgrabe.";
        Matcher m = Pattern.compile("(?m)(\\S+)\\s+((\\S+)\\s+(\\S+))$").matcher(POEM);
        // 查找多个匹配
        while (m.find()) {
            // 获取分组集合
            for(int j = 0; j <= m.groupCount(); j++) {
                System.out.println("[" + m.group(j) + "]");
            }
            System.out.println();
        }
    }

    @Test
    public void groups2() {
        String POEM = "Twas brillig，and the slithy toves Did gyre and gimble in the wabe； All mimsy were the borogoves， And the mome raths outgrabe. 'Beware the Jabberwock，my son！ The jaws that bite，the claws that catch！ Beware the Jubjub bird，and shun The frumious Bandersnatch！' He took his vorpal sword in hand： Long time the manxome foe he sought So rested he by the Tumtum tree， And stood awhile in thought. And as in uffish thought he stood. The Jabberwock，with eyes of flame， Came whiffling through the tulgey wood， And burbled as it came！ One，two！One，two！And through and through The vorpal blade went snicker-snack！ He left it dead，and with its head He went galumphing back. 'And thou hast slain the Jabberwock？ Come to my arms，my beamish boy！ O frabjous day！Callooh！Callay！' He chortled in his joy. 'Twas brillig，and the slithy toves Did gyre and gimble in the wabe； All mimsy were the borogoves， And the mome raths outgrabe.";
        Set<String> words = new HashSet<>();
        // 找出所有不以大写字母开头的词 1: \\b词边界  2: ?![A-Z]非大写字母开头的首字母  3: 一个或者多个词字符  4: \\b词边界
        Matcher m = Pattern.compile("\\b((?![A-Z])\\w+)\\b").matcher(POEM); // 组0是全部匹配   组1是
        while (m.find()) {
//            for(int i = 0; i <= m.groupCount(); i++) {
//                System.out.println("[" + m.group(i) + "]");
//            }
            System.out.println("[" + m.group(1) + "], start = " + m.start() + ", end = " + m.end());
            words.add(m.group(1));
            System.out.println();
        }
        System.out.println("words = " + words);
    }


    /**
     * start()和end()
     * start()返回先前匹配的起始位置的索引
     * end()返回所匹配的最后字符的索引加一的值
     * 匹配适配之后,如果还调用start()或end()将会产生IllegalStateException
     */
    @Test
    public void startEnd() {

    }

    static class StartEnd {
        static String input = "Twas brillig，and the slithy toves Did gyre and gimble in the wabe； All mimsy were the borogoves， And the mome raths outgrabe. 'Beware the Jabberwock，my son！ The jaws that bite，the claws that catch！ Beware the Jubjub bird，and shun The frumious Bandersnatch！' He took his vorpal sword in hand： Long time the manxome foe he sought So rested he by the Tumtum tree， And stood awhile in thought. And as in uffish thought he stood. The Jabberwock，with eyes of flame， Came whiffling through the tulgey wood， And burbled as it came！ One，two！One，two！And through and through The vorpal blade went snicker-snack！ He left it dead，and with its head He went galumphing back. 'And thou hast slain the Jabberwock？ Come to my arms，my beamish boy！ O frabjous day！Callooh！Callay！' He chortled in his joy. 'Twas brillig，and the slithy toves Did gyre and gimble in the wabe； All mimsy were the borogoves， And the mome raths outgrabe.";

        private static class Display {
            private boolean regexPrinted = false;
            private String regex;
            Display(String regex) {
                this.regex = regex;
            }

            void display(String message) {
                if(!regexPrinted) {
                    System.out.println(regex);
                    regexPrinted = true;
                }
                System.out.println(message);
            }
        }

        static void examine(String s, String regex) {
            Display d = new Display(regex);
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(s);
            System.out.println("regex = " + regex);
            // 判断是否还有下一个匹配的
            while (m.find()) {
                d.display("find() '" + m.group() + ", start = " + m.start() + " end = " + m.end());
            }

            // 判断字符串开始部分是否匹配
            if(m.lookingAt()) {
                d.display("lockingAt() start = " + m.start() + " end = " + m.end());
            }

            // 判断是否全部匹配
            if(m.matches()) {
                d.display("matches() start = " + m.start() + " end = " + m.end());
            }
        }

        public static void main(String[] args) {
            System.out.println("input: " + input);
            for(String regex : new String[] {"\\w*ere\\w*", "\\w*ever", "T\\w+", "Never.*?!"}) {
                examine(input, regex);
            }
        }
    }

    @Test
    public void varTest() {

        String patternStr = "^[a-zA-Z_$][a-zA-Z_$0-9]*$";
        Matcher m = Pattern.compile(patternStr).matcher("_b");
        System.out.println(m.find());
    }

    @Test
    public void specTest() {
        // 中文\\W, 字母\\w
        String patternStr = "^[a-zA-Z0-9\\u4e00-\\u9fa5]+$";
        Matcher m = Pattern.compile(patternStr).matcher("中国a1a");
        System.out.println(m.find());
    }
}
