package cn.tim.phonenumber.generate;

import java.util.Random;

/**
 * Created by LuoLiBing on 16/8/24.
 */
public class NumberGenerator {

    private static Random random = new Random();

    public static long generate() {
        return random.nextInt(1_000_000_000) + 1000000000;
    }

    public static void main(String[] args) {
        for(int i = 0; i < 894784853; i++) {
            System.out.println(generate());
        }
    }
}
