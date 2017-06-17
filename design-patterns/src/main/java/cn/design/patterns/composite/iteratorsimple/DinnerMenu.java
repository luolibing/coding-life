package cn.design.patterns.composite.iteratorsimple;

import cn.design.patterns.composite.iterator.MenuItem;

import java.util.Iterator;

/**
 * Created by LuoLiBing on 16/6/18.
 */
public class DinnerMenu implements Menu<MenuItem> {

    final static int MAX_ITEMS = 6;

    int numberOfItems = 0;

    MenuItem[] menuItems;

    public DinnerMenu() {
        this.menuItems = new MenuItem[MAX_ITEMS];
        addItem("面包", "好吃", true, 111);
        addItem("培根", "好吃的肉", false, 222);
        addItem("肉", "猪肉", false, 333);
    }

    public Iterator<MenuItem> createIterator() {
        return new DinnerMenuIterator(menuItems);
    }

    public void addItem(String name, String description,
                        boolean vegetarian, double price) {
        MenuItem menuItem = new MenuItem(name, description, vegetarian, price);
        if(numberOfItems < MAX_ITEMS) {
            menuItems[numberOfItems++] = menuItem;
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }
}
