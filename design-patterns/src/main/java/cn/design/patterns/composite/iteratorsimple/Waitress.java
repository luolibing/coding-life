package cn.design.patterns.composite.iteratorsimple;

import cn.design.patterns.composite.iterator.MenuItem;

import java.util.Iterator;
import java.util.List;

/**
 * Created by LuoLiBing on 16/6/18.
 */
public class Waitress {

    List<Menu<MenuItem>> menus;

    public Waitress(List<Menu<MenuItem>> menus) {
        this.menus = menus;
    }

    public void printMenu() {
        java.util.Iterator<Menu<MenuItem>> menuIterator = menus.iterator();
        while(menuIterator.hasNext()) {
            Menu<MenuItem> menu = menuIterator.next();
            printMenu(menu.createIterator());
        }

    }

    private void printMenu(Iterator<MenuItem> iterator) {
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
