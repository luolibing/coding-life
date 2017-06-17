package cn.tim.thinking.string;

import cn.tim.thinking.io.TextFileDemo1;
import cn.tim.thinking.io.TextFileDemo2;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by LuoLiBing on 16/12/22.
 * Pattern标记
 *
 * Pattern类的compile()方法还有另一个版本, 它接受一个标记参数, 以调整匹配的行为:
 * Pattern Pattern.compile(String regex, int flag)
 * 其中的flag来自以下Pattern类中的常量:
 * CANON_EQ             两个字符当且仅当他们的完全规范分解相匹配时,就认为他们是匹配的
 * CASE_INSENSITIVE(?i) 大小写不敏感的匹配假定只有US-ASCII字符集中的字符才能进行.
 * COMMENTS(?x)         这种模式下,空格符将被忽略掉,并且以#开始直到行末的注释也会被忽略掉.
 * DOTALL(?s)           在dotall模式中,表达式"."匹配所有字符,包括行终结符.
 * MULTILINE(?m)        在多行模式下,表达式^和$分别匹配一行的开始和结束.
 * UNICODE_CASE(?u)     当制定这个标记, 并且开启CASE_INSENSITIVE时, 大小写不敏感的匹配将按照与Unicode标准相一致的方式进行
 * UNIX_LINES(?d)       在这种模式下, 在. ^和$行为中, 值识别行终结符\n
 *
 */
public class PatternDemo {


    /**
     * 使用或(|)操作符组合多个标记的功能
     */
    @Test
    public void reFlags() {
        // 匹配 JAVA java Java等等, 并且是多行模式.
        Pattern p = Pattern.compile("^java", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
        Matcher m = p.matcher("java has regex\nJava has regex\nJava has pretty good regular expressions\nRegular expressions are in Java");
        while (m.find()) {
            System.out.println(m.group());
        }
    }


    /**
     * split()
     * 将输入的字符串断开成字符串对象数组, 断开边界由下列正则表达式确定
     * split(input)
     * split(input, limit) limit限制
     * 这是一个快速而方便的方法, 可以按照通用边界断开输出文本
     *
     */
    @Test
    public void splitDemo() {
        String input = "This!!unusual use!!of exclamation!!points";
        System.out.println(Arrays.toString(Pattern.compile("!!").split(input)));
        System.out.println(Arrays.toString(Pattern.compile("!!").split(input, 3)));
    }


    /*!
     * Here's a block of text to    use as input to
     *  the regular expression matcher. Note that we'll
     * first extract the block of text by looking for
     * the special delimiters, then process the
     * extracted block.
    !*/

    /*!
     * 替换操作
     * replaceFirst, replaceAll, 替换首个, 和替换所有
     !*/
    @Test
    public void replace() {
        String s = TextFileDemo2.read("/Users/tim/Documents/samples-tim/thinking-in-java/src/main/java/cn/tim/thinking/string/PatternDemo.java");
        // 匹配注释, /\\*!  使用转义字符\\将/*!看做简单的字符串
        Matcher m = Pattern.compile("/\\*!(.*)!\\*/", Pattern.DOTALL).matcher(s);
        if(m.find()) {
            s = m.group(1);
        }
        System.out.println(s);

        // 替换两个以上的空格为一个
        s = s.replaceAll(" {2,}", "");
        System.out.println(s);

        // ?m表示多行模式, 去掉每行开始的空格
        s = s.replaceAll("(?m)^ +", "");
        System.out.println(s);

        // 替换掉首个元音字母为VOWEL1
        s = s.replaceFirst("[aeiou]", "(VOWEL1)");
        System.out.println(s);

        StringBuffer buffer = new StringBuffer();
        Pattern p = Pattern.compile("[aeiou]");
        Matcher matcher = p.matcher(s);
        while (matcher.find()) {
            // appendReplacement将字符串分割成组, 然后替换, 替换之后然后append到buffer当中
            matcher.appendReplacement(buffer, matcher.group().toUpperCase());
        }
        System.out.println(buffer);
        // appendTail将分割之后剩下的部分添加到buffer的末尾
        matcher.appendTail(buffer);
        System.out.println(buffer);
    }


    /**
     * 将matcher重置reset()
     */
    @Test
    public void resetting() {
        Matcher m = Pattern.compile("[frb][aiu][gx]").matcher("fix the rug with bags");
        int index = 0;
        while (m.find()) {
            System.out.println((++index) + "find()");
            for(int i = 0; i <= m.groupCount(); i++) {
                System.out.println("group" + i + ": " + m.group(i));
            }
        }
        System.out.println();
        // 重置重新正则匹配新的字符串, 并且回到头位置
        m.reset("fix the rig with rags");
        while (m.find()) {
            System.out.println((++index) + "find()");
            for(int i = 0; i <= m.groupCount(); i++) {
                System.out.println("group" + i + ": " + m.group(i));
            }
        }
    }


    /**
     * 正则表达式与JAVA I/O
     * 将正则表达式引用到IO当中,在一个文件中进行搜索匹配操作.
     */
    static class JGrep {

        public static void grep(String command) {
            String[] commands = command.split(" +");
            grep(commands[0], commands[1]);
        }

        private static void grep(String fileName, String regex) {
            Pattern p = Pattern.compile(regex);
            int index = 0;
            // 先初始化一个空的match
            Matcher matcher = p.matcher("");
            for(String line : new TextFileDemo1(fileName)) {
                // 每行都进行reset重置Matcher
                matcher.reset(line);
                while (matcher.find()) {
                    System.out.println(index++ + ": " + matcher.group() + ": " + matcher.start());
                }
            }
        }


        public static void grepClassByPath(String pathName) throws IOException {
            Files.walkFileTree(Paths.get(pathName), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    grepClass(file.toString());
                    return FileVisitResult.CONTINUE;
                }
            });
        }

        // 类命名正则
        private final static String Identifier = "[$A-Za-z_][$A-Za-z_0-9]*";

        private final static String ClassOrInterfaceType = Identifier + "(?:\\." + Identifier + ")*";

        private final static String CU_REP_REGEX =
                // 类定义, 继承基类, new实例化类
                "class\\s+" + Identifier +
                "|extends\\s+" + ClassOrInterfaceType +
                "|new \\s+" + ClassOrInterfaceType;

        public static void grepClass(String fileName) {
            String content = TextFileDemo1.read(fileName);
            Matcher matcher = Pattern.compile(CU_REP_REGEX).matcher(content);
            while (matcher.find()) {
                System.out.println(matcher.group());
            }
        }

        public static void grepByPath(String path, String regex) {
            File[] files = new File(path).listFiles();
            for(File file : files) {
                grep(file.getPath(), regex);
            }
        }

        public static void main(String[] args) {
            grep("/Users/tim/Documents/samples-tim/thinking-in-java/src/main/java/cn/tim/thinking/string/PatternDemo.java", "\\{(.*?)\\}");
            //
            grep("/Users/tim/Documents/samples-tim/thinking-in-java/src/main/java/cn/tim/thinking/string/PatternDemo.java", "\\b[Ssct]\\w+");
            grepByPath("/Users/tim/Documents/samples-tim/thinking-in-java/src/main/java/cn/tim/thinking/string", "\\{(.*?)\\}");
            grepByPath("/Users/tim/Documents/samples-tim/thinking-in-java/src/main/java/cn/tim/thinking/string", "\\{(.*?)\\}");
        }
    }

    @Test
    public void comment() {
        Pattern pattern = Pattern.compile("//.*|(\"(?:\\\\[^\"]|\\\\\"|.)*?\")|(?s)/\\*.*?\\*/");
        Matcher m = pattern.matcher(TextFileDemo2.read("/Users/tim/Documents/samples-tim/thinking-in-java/src/main/java/cn/tim/thinking/string/PatternDemo.java"));
        while (m.find()) {
            System.out.println(m.group());
        }
    }

    @Test
    public void className() throws IOException {
        JGrep.grepClassByPath("/Users/tim/Documents/samples-tim/thinking-in-java/src/main/java/");
    }

}
