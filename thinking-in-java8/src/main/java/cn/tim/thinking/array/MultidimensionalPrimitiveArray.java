package cn.tim.thinking.array;

import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by LuoLiBing on 16/12/24.
 * 多维数组
 */
public class MultidimensionalPrimitiveArray {

    @Test
    public void test1() {
        int[][][] a = {
                {{1, 2, 3}, {4, 5, 6}},  {{7, 8, 9}, {10, 11, 12}}
        };
        System.out.println(a[0][1][1]);

        // 多维数组不能简单的使用Arrays.toString(), 而是应该使用deepToString()
        System.out.println(Arrays.toString(a));
        System.out.println(Arrays.deepToString(a));

        // 多维数组同样会被自动初始化
        int[][][] b = new int[2][4][5];
        int[][][] c = {};
        System.out.println(Arrays.deepToString(b));
    }

    /**
     * 数组中构成矩阵的每个向量都可以具有任意的长度(又可以称为粗糙数组)
     */
    @Test
    public void raggedArray() {
        Random rand = new Random(47);
        // 三维大小可变数组
        int[][][] a = new int[rand.nextInt(7)][][];
        for(int i = 0; i < a.length; i++) {
            a[i] = new int[rand.nextInt(5)][];
            for(int j = 0; j < a[i].length; j++) {
                a[i][j] = new int[rand.nextInt(5)];
            }
        }
        System.out.println(Arrays.deepToString(a));
    }

    /**
     * 多维数组的初始化, 逐层逐层的进行初始化.
     */
    @Test
    public void assemblingMultidimensionalArrays() {
        Integer[][] a;
        a = new Integer[3][];
        for(int i = 0; i < a.length; i++) {
            a[i] = new Integer[3];
            for(int j = 0; j < a[i].length; j++) {
                a[i][j] = i * j;
            }
        }
        System.out.println(Arrays.deepToString(a));
    }


    static double[][] createDoubleArray(int size, double from, double to) {
        double[][] result = new double[size][size];
        double increment = (to - from) / (size * size);
        double val = from;
        for(int i = 0; i < result.length; i++) {
            for(int j = 0; j < result[i].length; j++) {
                result[i][j] = val;
                val += increment;
            }
        }
        return result;
    }

    @Test
    public void test2() {
        double[][] result = createDoubleArray(10, 10.5d, 20.8d);
        for(int i = 0; i < result.length; i++) {
            for(int j = 0; j < result[i].length; j++) {
                System.out.printf("%.2f", result[i][j]);
            }
        }
    }
}
