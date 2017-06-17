package cn.tim.phonenumber.generate;

import cn.tim.phonenumber.simple.PhoneNumberResolver;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.BitSet;

/**
 * Created by LuoLiBing on 16/8/24.
 */
public class BitSetTest {

    private static Path bookFile = Paths.get("/share/number_book.txt");

    @Test
    public void containChars() throws IOException {
        BitSet used = new BitSet();
        FileInputStream fin = new FileInputStream("/share/rms_resource.log");
        InputStreamReader inR = new InputStreamReader(fin);
        BufferedReader bfR  = new BufferedReader(inR);
        char[] chars = new char[512];
        while (bfR.read(chars) > 0) {
            for(char ch : chars) {
                used.set(ch);
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        int size = used.size();
        System.out.println("size=" + size);
        for(int i = 0; i< size; i++) {
            if(used.get(i)) {
                sb.append((char) i);
            }
        }
        sb.append("]");
        System.out.println(sb.toString());
    }



    @Test
    public void sort1() {
        // 大数据去重排序
        BitSet bitSet = new BitSet(1_000_000_000);
        NumberBookReader.readData(bookFile, bitSet::set);
//        System.out.println(bitSet.size());
         bitSet.stream().forEach(System.out::println);
    }

    @Test
    public void sort2() {
        BitSet bitSet = new BitSet();
        bitSet.set(0);
        bitSet.set(1);
        bitSet.set(5);
        bitSet.set(10);
        bitSet.set(63);

        System.out.println("0 exists:" + bitSet.get(0));
        System.out.println("5 exists:" + bitSet.get(5));
    }

    @Test
    public void resolve() {
        PhoneNumberResolver.resolve();
        PhoneNumberResolver.release();
    }

    @Test
    public void getTop50() {
        System.out.println(8 >> 2);
    }
}
