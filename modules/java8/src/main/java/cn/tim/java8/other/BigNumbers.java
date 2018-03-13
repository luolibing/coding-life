package cn.tim.java8.other;

import org.junit.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 * User: luolibing
 * Date: 2018/3/1 13:55
 */
public class BigNumbers {

    public static void main(String[] args) {

    }

    @Test
    public void write1() {
        try(BufferedWriter reader = new BufferedWriter(new FileWriter("E:/numbers/num.txt"))) {
            for (int i = 0; i < 1000_0000; i++) {
                reader.write(Integer.toString(i));
                reader.write(",");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
