package cn.tim.thinking.io;

import org.junit.Test;

import java.io.*;
import java.util.Arrays;
import java.util.TreeMap;

/**
 * Created by LuoLiBing on 16/12/2.
 * 读取二进制文件
 */
public class BinaryFileDemo1 {

    // 实用BufferedInputStream对二进制文件进行读取
    public static byte[] read(File bFile) throws IOException {
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(bFile));
        try {
            byte[] result = new byte[in.available()];
            in.read(result);
            return result;
        } finally {
            in.close();
        }
    }

    public static byte[] read(String fName) throws IOException {
        return read(new File(fName));
    }

    public static void main(String[] args) throws IOException {
        byte[] bytes = BinaryFileDemo1.read("/Users/tim/Documents/业务需求疑问.md");
        System.out.println(Arrays.toString(bytes));
    }

    class BinaryFileDemo2 extends TreeMap<Byte, Integer> {

        public BinaryFileDemo2(String fileName) {
            try {
                byte[] data = read(fileName);
                for(byte b : data) {
                    compute(b, (k, v) -> v == null ? 1 : (v + 1));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void binary() {
        BinaryFileDemo2 binary = new BinaryFileDemo2("/Users/tim/Documents/业务需求疑问.md");
        binary.forEach((k, v) -> System.out.println(k + ": " + v));
    }
}
