package cn.design.patterns.decorator.io;

import java.io.*;

/**
 * Created by LuoLiBing on 16/5/5.
 */
public class InputTest {

    public static void main(String[] args) throws IOException {
        int c;
        String path = "/Users/tim/L3NoYXJlL3N2bi_kurrmlZnkuIPlubTnuqfkuIsgVW5pdDHmi5PlsZUvMDAxLuefreWvueivnS3lj4LliqDogZrkvJrvvJpDYW4geW91IHBsYXkgdGhlIGd1aXRhcj8ueGxzeA.json";
        InputStream input = new LowerCaseInputStream(new BufferedInputStream(new FileInputStream(path)));
        while ((c = input.read()) >= 0) {
            System.out.print((char) c);
        }
        input.close();
    }
}
