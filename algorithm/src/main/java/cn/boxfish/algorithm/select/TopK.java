package cn.tim.algorithm.select;

import cn.tim.algorithm.ArrayUtils;
import cn.boxfish.algorithm.RandomUtils;

import java.util.Arrays;

/**
 * Created by LuoLiBing on 17/2/28.
 * TopK问题， 取排名前K个
 */
public class TopK {

    /**
     * 解法1： 利用快排思想，先找出一个数的位置， 然后根据这个数的位置与topK的索引值进行比较， 如果比他大则找左边， 比它小则找右边， 直到索引值相等为止
     */
    public static class TopK1 {
        public static int[] topK(int[] T, int left, int right, int k) {
            if(left > right || right < k) {
                return null;
            }
            int indexK= right  + 1 - k;
            int mid = partition(T, left, right);
            // 如果mid比right-k大， 表明right-k在左边
            if(mid > indexK) {
                return topK(T, left, mid - 1, k);
            } else if(mid < indexK) {
                return topK(T, mid + 1, right, k);
            } else {
                // 递归出口
                return Arrays.copyOfRange(T, indexK, right + 1);
            }
        }

        public static int partition(int[] T, int left, int right) {
            int i = left - 1;
            int x = T[right];
            for(int j = left; j < right; j++) {
                if(T[j] <= x) {
                    i++;
                    ArrayUtils.swap(T, i, j);
                }
            }
            ArrayUtils.swap(T, i + 1, right);
            return i+1;
        }

        public static void main(String[] args) {
            int[] array = RandomUtils.randArray(10);
            System.out.println(Arrays.toString(array));
            int[] topK = topK(array, 0, array.length - 1, 3);
            System.out.println(Arrays.toString(topK));
        }
    }

    /**
     * topK问题解法二， 利用最大堆构造一个topK问题， 对于内存有限， 无法保存大表的情况下进行排序
     */
    public static class TopK2 {

    }
}
