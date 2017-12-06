package cn.tim.thinking.string;

import cn.tim.thinking.io.TextFileDemo1;
import org.junit.Test;

import java.io.*;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by LuoLiBing on 16/12/23.
 * 扫描输入, 对输入的字符串进行分词, 然后转换成对应的类型
 */
public class ScanInput {

    @Test
    public void scanInput() throws IOException {
        BufferedReader input = new BufferedReader(new StringReader("Sir Robin of Camelot\n22 1.61803"));
        System.out.println("What is your name?");
        String name = input.readLine();
        System.out.println(name);
        System.out.println("How old are you? what is your favorite double");
        String numbers = input.readLine();
        System.out.println(numbers);
        String[] numArray = numbers.split(" ");
        System.out.println(Integer.valueOf(numArray[0]));
        System.out.println(Double.valueOf(numArray[1]));

        // Scanner对格式化输入进行了优化
        BufferedReader input2 = new BufferedReader(new StringReader("Sir Robin of Camelot\n22 1.61803"));
        Scanner stdin = new Scanner(input2);
        System.out.println(stdin.nextLine());
        System.out.println(stdin.nextInt());
        System.out.println(stdin.nextDouble());
        // stdin.nextBigDecimal();  Scanner还支持nextBigDecimal()这种
        // stdin.nextBigInteger();
    }


    /**
     * Scanner定界符
     * Scanner默认是使用空白字符进行分词. 也可以指定定界符进行分割
     */
    @Test
    public void scanSplit() {
        Scanner scanner = new Scanner("12, 142, 78, 99, 42");
        while (scanner.hasNext()) {
            System.out.println(scanner.next());
        }

        scanner = new Scanner("12, 142, 78, 99, 42");
        // Scanner限定符
        scanner.useDelimiter("\\s*,\\s*");
        while (scanner.hasNextInt()) {
            System.out.println(scanner.nextInt());
        }
    }


    /**
     * 使用正则扫描
     *
     */
    @Test
    public void threatAnalyzer() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("/Users/tim/Downloads/analyzer.log"));
        String patternStr = "(-(\\d+[.]\\d+[.]\\d+[.]\\d+))" + "[\\w\\W]*" + "(\\[(\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2})\\])";
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher("");
        String line;
        while ((line = br.readLine())!= null) {
            matcher = matcher.reset(line);
            while (matcher.find()) {
                System.out.println("ip = " + matcher.group(2) + ", time = " + matcher.group(3));
            }
        }
    }


    /**
     * 使用scanner扫描输入
     */
    @Test
    public void threatAnalyzer2() {
        String content = TextFileDemo1.read("/Users/tim/Downloads/analyzer.log");
        String patternStr = "(\\n-(\\d+[.]\\d+[.]\\d+[.]\\d+))" + "[\\w\\W]*" + "(\\[(\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2})\\])";
        Scanner scanner = new Scanner(content);
        // 使用正则匹配是否还有下一个
        while (scanner.hasNext(patternStr)) {
            // 移动到下一个匹配
            scanner.next(patternStr);
            // match结果
            MatchResult match = scanner.match();
            // 与match类似, 可以使用group进行分组
            String ip = match.group(2);
            String time = match.group(3);
            System.out.format("ip = %s, time = %s", ip, time);
        }
    }

    public static void main(String[] args) {
        String timePattern = "\\[\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}\\]";
        Pattern pattern = Pattern.compile(timePattern);
        Matcher m = pattern.matcher("[2016-11-11 12:00:00]");
        if(m.matches()) {
            System.out.println(m.group());
        }

        String ipPattern = "-\\d+[.]\\d+[.]\\d+[.]\\d+";
        pattern = Pattern.compile(ipPattern);
        m = pattern.matcher("-100.97.10.1");
        if(m.matches()) {
            System.out.println(m.group());
        }
    }
}
