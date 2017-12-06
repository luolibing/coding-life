package cn.tim.thinking.io;

import java.io.File;
import java.io.IOException;

/**
 * Created by LuoLiBing on 16/12/2.
 */
public class BinaryFileDemo2 {

    final static byte[] signature = { (byte) -54, (byte) -2, (byte) -70, (byte) -66};

    public static void main(String[] args) throws IOException {
        for(File file : FileDemo1.Directory.walk("/Users/tim/Documents/samples-tim/thinking-in-java", ".*\\.class")) {
            byte[] data = BinaryFileDemo1.read(file);
            for(int i = 0; i < signature.length; i++) {
                if(data[i] != signature[i]) {
                    System.err.println(file + " is corrupt!");
                    break;
                }
                System.out.println(file + " is good");
            }
        }
    }

    public static void sayHello() {
        System.out.println("Hello!!!");
    }
}
