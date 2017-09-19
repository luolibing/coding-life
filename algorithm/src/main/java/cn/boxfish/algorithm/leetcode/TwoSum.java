package cn.boxfish.algorithm.leetcode;

import java.util.Arrays;

/**
 * Given an array of integers, return indices of the two numbers such that they add up to a specific target.
   You may assume that each input would have exactly one solution, and you may not use the same element twice.
 *
 *
 *   Given nums = [2, 7, 11, 15], target = 9,
     Because nums[0] + nums[1] = 2 + 7 = 9,
     return [0, 1].

 TODO 
 * User: luolibing
 * Date: 2017/9/19 19:43
 */
public class TwoSum {

    static class A {

        static int[] twoSum(int[] array, int target) {
            for(int i = 0; i < array.length; i++) {
                for(int j = i + 1; j < array.length; j++) {
                    if(array[j] == target - array[i]) {
                        return new int[] {i, j};
                    }
                }
            }
            throw new RuntimeException("no two sum");
        }

        public static void main(String[] args) {
            int[] array = twoSum(new int[]{1, 3, 4, 8}, 9);
            System.out.println(Arrays.toString(array));
        }
    }
}
