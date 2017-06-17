package cn.tim.algorithm.mi;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * Created by LuoLiBing on 17/1/21.
 */
public class UrlDemo1 {

    final static char[] digits = {
            '0' , '1' , '2' , '3' , '4' , '5' ,
            '6' , '7' , '8' , '9' , 'a' , 'b' ,
            'c' , 'd' , 'e' , 'f' , 'g' , 'h' ,
            'i' , 'j' , 'k' , 'l' , 'm' , 'n' ,
            'o' , 'p' , 'q' , 'r' , 's' , 't' ,
            'u' , 'v' , 'w' , 'x' , 'y' , 'z'
    };

    final static String[] exts = {
            "com", "cn", "org", "cc", "net", "xyz"
    };

    private String base = "http://";

    private int count = 1_000_000_000;

    private Random rand = new Random(47);

    private void initData() throws IOException {
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("/share/url.txt")));
        AtomicInteger counter = new AtomicInteger();
        (IntStream.range(0, count)).forEach((i) -> {
            out.println(randUrl());
            if(counter.incrementAndGet() % 100000 == 0) {
                if(i % 100000 == 0) {
                    out.flush();
                }
            }
        });
        out.close();

    }


    private String randUrl() {
        return String.format("%s%s.%s.%s", base, randStr(3), randStr(6), exts[rand.nextInt(exts.length)]);
    }

    private String randStr(int size) {
        StringBuilder builder = new StringBuilder(5);
        for(int i = 0; i < size; i++) {
            builder.append(digits[rand.nextInt(digits.length)]);
        }
        return builder.toString();
    }

    public static void main(String[] args) throws IOException {
        new UrlDemo1().initData();
    }
}
