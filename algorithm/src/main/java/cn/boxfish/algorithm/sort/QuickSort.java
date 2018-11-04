package cn.tim.algorithm.sort;

import cn.tim.algorithm.ArrayUtils;
import cn.boxfish.algorithm.RandomUtils;

import java.util.Arrays;

/**
 * Created by TIM on 2017/2/28.
 * 快速排序
 */
public class QuickSort {

    /**
     * 递归方式快排
     */
    static class QuickSort1 {
        public static void quickSort(int[] T, int left, int right) {
            // 递归出口， 也可以作为条件检查
            if(left >= right) {
                return;
            }
            // 先取中间节点， 将数组分为左右两边， 各自排序
            int mid = partition(T, left, right);
            // mid位置已经找到， 可以不再参与排序， 所以mid -1 和mid+1
            quickSort(T, left, mid - 1);
            quickSort(T, mid + 1, right);
        }

        // 分块
        public static int partition(int[] T, int l, int r){
            int i = l - 1;
            int x = T[r];
            for(int j = l; j < r; j++) {
                if(T[j] <= x) {
                    // i标记小于或者等于x的位置， 所以后移之后，将比x小的数换到这个位置
                    i++;
                    ArrayUtils.swap(T, i, j);
                }
            }
            // i为小于等于x的值， 所以i+1为大于x的值， 将i+1和x互换， 最终0-i小于等于x, i+1-r大于x
            ArrayUtils.swap(T, i + 1, r);
            return i + 1;
        }

        public static void main(String[] args) {
            int[] array = RandomUtils.randArray(10);
            quickSort(array, 0, array.length-1);
            System.out.println(Arrays.toString(array));
        }
    }

    /**
     * 非递归实现貌似有点困难
     */
    static class QuickSort2 {
        public static void quickSort(int[] T, int left, int right) {
            int x = T[left];
            while (left < right) {
                while (left < right && T[right] >= x) {
                    right--;
                }
                T[right] = T[left];

                while (left < right && T[left] <= x) {
                    left ++;
                }
                T[right] = T[left];
            }

            T[left] = x;
        }

        public static void main(String[] args) {
            int[] array = RandomUtils.randArray(10);
            quickSort(array, 0, array.length - 1);
            System.out.println(Arrays.toString(array));
        }
    }
}
