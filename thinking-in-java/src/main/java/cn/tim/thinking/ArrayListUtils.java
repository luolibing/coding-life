package cn.tim.thinking;

import java.util.ArrayList;
import java.util.ArrayListProvider;

/**
 * Created by LuoLiBing on 16/12/13.
 */
public class ArrayListUtils<T> {

    public static void sayHello() {
        System.out.println("Hello");
    }

    static <T> T get(int index) {
        return null;
    }

    public static void main(String[] args) {
        ArrayListProvider.executeAdd(new ArrayList<Integer>(), 1);
    }

}
