package cn.tim.classloader;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * User: luolibing
 * Date: 2017/9/4 20:06
 */
public class MainClass {

    static {
        System.out.println("load class");
    }

    public static void main(String[] args) throws ParseException {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2017-01-01 10:00:00").getTime());
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-01-01 10:00:00").getTime());
    }
}
