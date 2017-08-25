package cn.boxfish.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * User: luolibing
 * Date: 2017/8/25 12:29
 */
public class DefectDemo1 {

    public static void defect() {
        List<Integer> list = randomArray();
        int num = list.get(0);
        for(int i = 1; i< list.size(); i++) {
            num = num ^ list.get(i);
        }
        System.out.println(num);
    }

    private static List<Integer> randomArray() {
        List<Integer> result = new ArrayList<>();
        for(int i = 0; i < 100; i++) {
            result.add(i);
            result.add(i);
        }
        result.remove(0);
        Collections.shuffle(result);
        return result;
    }

    public static void main(String[] args) {
        DefectDemo1.defect();
        System.out.println("^ = " + (1 ^ 2 ^ 3));
    }
}
