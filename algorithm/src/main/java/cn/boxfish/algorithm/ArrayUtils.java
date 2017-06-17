package cn.tim.algorithm;

/**
 * Created by TIM on 2017/2/28.
 */
public class ArrayUtils {

    public static void swap(int[] T, int i, int j) {
        int temp = T[i];
        T[i] = T[j];
        T[j] = temp;
    }}
