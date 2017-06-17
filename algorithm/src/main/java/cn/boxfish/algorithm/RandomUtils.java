package cn.tim.algorithm;

import java.util.Random;

/**
 * Created by TIM on 2017/2/28.
 */
public class RandomUtils {

    private static Random rand = new Random(47);

    public static int[] randArray(int count) {
        int[] result = new int[count];
        for(int i = 0; i < count; i++) {
            result[i] = rand.nextInt(100);
        }
        return result;
    }
}
