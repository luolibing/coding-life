package com.tim.dubbo.sample;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by luolibing on 2018/8/30.
 */
public class WelcomeServiceImpl implements WelcomeService {

    private static final long RNADOM = System.currentTimeMillis();

    @Override
    public String welcome(String name) {
        return  RNADOM + " hello, welcome " + name;
    }

    @Override
    public void addPerson(Person person) {

    }

    @Override
    public void updatePerson(Person person) {

    }

    public static void main(String[] args) {
        TreeMap<Integer, String> treeMap = new TreeMap<>();
        treeMap.put(1, "a");
        treeMap.put(3, "c");
        treeMap.put(6, "d");
        treeMap.put(10, "z");
        treeMap.put(20, "x");
        treeMap.put(50, "y");

        Map.Entry<Integer, String> entry = treeMap.tailMap(60, true).firstEntry();
        System.out.println(entry);
    }
}
