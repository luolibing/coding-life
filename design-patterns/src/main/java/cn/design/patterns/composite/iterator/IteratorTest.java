package cn.design.patterns.composite.iterator;

import java.util.List;

/**
 * Created by LuoLiBing on 16/6/18.
 */
public class IteratorTest {

    public static void main(String[] args) {
        PancakeHouseMenu pancakeHouseMenu = new PancakeHouseMenu();
        List<MenuItem> breakfastItems = pancakeHouseMenu.getMenuItems();

        DinerMenu dinnerMenu = new DinerMenu();
        MenuItem[] lunchItems = dinnerMenu.getMenuItems();

        for(int i = 0; i < breakfastItems.size(); i++) {
            MenuItem menuItem = breakfastItems.get(i);
            System.out.println(menuItem);
        }

        for(int i = 0; i < lunchItems.length; i++) {
            MenuItem menuItem = lunchItems[i];
            System.out.println(menuItem);
        }
    }
}
