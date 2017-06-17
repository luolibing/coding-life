package cn.tim.algorithm.select;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by LuoLiBing on 17/2/28.
 * 二分查找
 * 查找一个数在一个已排序数组中的位置
 */
public class TwoPointSearch1 {

    private static Random rand = new Random(47);

    private static int[] initArray(int count) {
        int[] result = new int[count];
        for(int i = 0; i < count; i++) {
            result[i] = rand.nextInt(1000);
        }
        Arrays.sort(result);
        return result;
    }

    /**
     * 方式一, 使用递归方式
     */
    static class Tws1 {
        public static int search(int[] T, int left, int right, int x) {
            if(left > right || T.length == 0) {
                return -1;
            }
            int mid = (right + left) / 2;
            if(x > T[mid]) {
                return search(T, mid + 1, right, x);
            } else if(x < T[mid]) {
                return search(T, left, mid -1, x);
            } else {
                return mid;
            }
        }

        public static void main(String[] args) {
            for(int i = 0; i < 10; i++) {
                int[] array = initArray(10);
                int searchData = array[rand.nextInt(10)];
                int index = search(array, 0, array.length - 1, searchData);
                System.out.println(Arrays.toString(array) + String.format(", search %s in %s", searchData, index));
            }
        }
    }


    /**
     * 非递归方式实现
     */
    static class Tws2 {
        public static int search(int[] T, int x) {
            if(T.length == 0) {
                return -1;
            }

            int left = 0;
            int right = T.length - 1;
            // 从最大的闭区间到最小的一个元素
            while (left <= right) {
                int mid = (left + right) / 2;
                if(x > T[mid]) {
                    left = mid + 1;
                } else if(x < T[mid]) {
                    right = mid - 1;
                } else {
                    return mid;
                }
            }
            return -1;
        }

        public static void main(String[] args) {
            for(int i = 0; i < 10; i++) {
                int[] array = initArray(10);
                int searchData = array[rand.nextInt(10)];
                int index = search(array, searchData);
                System.out.println(Arrays.toString(array) + String.format(", search %s in %s", searchData, index));
            }
        }
    }
}
