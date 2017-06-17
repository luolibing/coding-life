package cn.design.patterns.template.sort;

import java.util.Arrays;

/**
 * Created by LuoLiBing on 16/6/12.
 */
public class DuckSortTestDrive {

    public static void main(String[] args) {
        Duck[] ducks = {
                new Duck("daffy", 8),
                new Duck("honey", 10),
                new Duck("red", 4),
                new Duck("god", 15),
                new Duck("dark", 12),
                new Duck("white", 15)
        };
        Arrays.sort(ducks);
        display(ducks);
    }

    public static void display(Duck[] ducks) {
        for(Duck duck : ducks) {
            System.out.println(duck);
        }
    }
}
