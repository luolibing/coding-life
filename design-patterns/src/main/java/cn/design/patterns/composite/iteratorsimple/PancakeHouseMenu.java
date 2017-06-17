package cn.design.patterns.composite.iteratorsimple;

import cn.design.patterns.composite.iterator.MenuItem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by LuoLiBing on 16/6/18.
 */
public class PancakeHouseMenu implements Menu<MenuItem> {

    List<MenuItem> menuItems;

    public PancakeHouseMenu() {
        this.menuItems = new ArrayList<>();
        addItem("面包", "好吃", true, 111);
        addItem("培根", "好吃的肉", false, 222);
        addItem("肉", "猪肉", false, 333);
    }

    public void addItem(String name, String description,
                        boolean vegetarian, double price) {
        MenuItem menuItem = new MenuItem(name, description, vegetarian, price);
        menuItems.add(menuItem);
    }

    public Iterator<MenuItem> createIterator() {
        return new PancakeHouseMenuIterator(menuItems);
    }
}
