package cn.design.patterns.composite.iteratorsimple;

import cn.design.patterns.composite.iterator.MenuItem;

import java.util.Iterator;

/**
 * Created by LuoLiBing on 16/6/18.
 */
public class DinnerMenuIterator implements Iterator<MenuItem> {

    MenuItem[] items;

    int position = 0;

    public DinnerMenuIterator(MenuItem[] items) {
        this.items = items;
    }

    @Override
    public boolean hasNext() {
        return !(position >= items.length || items[position] == null);
    }

    @Override
    public MenuItem next() {
        return items[position++];
    }
}
