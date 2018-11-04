package cn.tim.algorithm.sort;

import cn.boxfish.algorithm.RandomUtils;

import java.util.Arrays;

/**
 * Created by TIM on 2017/3/1.
 */
public class MergerSort {

     public static class MergerSort1 {
         public static int[] sort(int[] T, int left, int right) {
             if(left == right) {
                 return new int[] {T[left]};
             }
             int mid = (right + left) / 2;
             int[] b1 = sort(T, left, mid);
             int[] b2 = sort(T, mid + 1, right);
             return merge(b1, b2);
         }

         private static int[] merge(int[] left, int[] right) {
             int[] result = new int[left.length + right.length];
             int i = 0;
             int l = 0; int r = 0;
             while (l < left.length && r < right.length) {
                 if(left[l] > right[r]) {
                     result[i] = right[r];
                     r++;
                 } else {
                     result[i] = left[l];
                     l++;
                 }
             }
             return null;
         }

         public static void main(String[] args) {
             int[] array = RandomUtils.randArray(10);
             sort(array, 0, array.length - 1);
             System.out.println(Arrays.toString(array));
         }
     }
}
