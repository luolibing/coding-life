package cn.boxfish.algorithm.complex;

import org.junit.Test;

import java.util.Arrays;

/**
 * Created by luolibing on 2018/11/4.
 */
public class ComplexSample2 {

    static class Complex1 {
        /**
         * 时间复杂度为O（n）
         * @param array
         * @param x
         * @return
         */
        static int find1(int[] array, int x) {
            int i = 0;
            int pos = -1;
            for(; i < array.length; i++) {
                if(array[i] == x) {
                    pos = i;
                }
            }
            return pos;
        }

        /**
         * 最好情况时间复杂度为O(1)最坏情况为O(n)
         * 平均情况时间复杂度，计算，所有情况的次数之和/所有情况总数
         * 下面总共有n+1种情况，0到n-1，还包括没有找到
         * (1+2+3+...+n+n)/(n+1)=n*(n+3)/2(n+1)，除去这些系数，最后得到的结果为O(n)
         * 一般计算平均情况，还得加上每种情况出现的概率
         * @param array
         * @param x
         * @return
         */
        static int find2(int[] array, int x) {
            int i = 0;
            int pos = -1;
            for(; i < array.length; i++) {
                if(array[i] == x) {
                    pos = i;
                    break;
                }
            }
            return pos;
        }

        /**
         * 最好情况时间复杂度O(1)最坏O(n),平均O(1)
         */
        static class List {
            int[] array = new int[10];
            int len = 10;
            int i = 0;

            void add(int n) {
                if(i >= len) {
                    int[] newArray = new int[len * 2];
                    for(int j = 0; j < len; j++) {
                        newArray[j] = array[j];
                    }
                    len = len * 2;
                    array = newArray;
                }
                array[i] = n;
                i++;
            }
        }

        public static void main(String[] args) {
            List list = new List();
            for (int i = 0; i< 30; i++) {
                list.add(i);
            }

            System.out.println(Arrays.toString(list.array));
        }
    }
}
