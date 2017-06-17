package cn.design.patterns.composite.iteratorsimple;

import cn.design.patterns.composite.iterator.MenuItem;

import java.util.Hashtable;
import java.util.Iterator;

/**
 * Created by LuoLiBing on 16/6/18.
 */
public class CafeMenu implements Menu<MenuItem> {

    private Hashtable<String, MenuItem> menuItems = new Hashtable<>();

    public CafeMenu() {

    }

    public void addItem(String name, String description,
                        boolean vegetarian, double price) {
        MenuItem menuItem = new MenuItem(name, description, vegetarian, price);
        menuItems.put(menuItem.getName(), menuItem);
    }

    @Override
    public Iterator<MenuItem> createIterator() {
//        return menuItems.values().iterator();
        return menuItems.values().iterator();
    }
}
