package cn.design.patterns.composite.iterator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LuoLiBing on 16/6/18.
 */
public class PancakeHouseMenu {

    List<MenuItem> menuItemList;

    public PancakeHouseMenu() {
        menuItemList = new ArrayList<>();
        addItem("面包", "好吃", true, 111);
        addItem("培根", "好吃的肉", false, 222);
        addItem("肉", "猪肉", false, 333);
    }

    public void addItem(String name, String description,
                        boolean vegetarian, double price) {
        MenuItem menuItem = new MenuItem(name, description, vegetarian, price);
        menuItemList.add(menuItem);
    }

    public List<MenuItem> getMenuItems() {
        return menuItemList;
    }
}
