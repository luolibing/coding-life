package cn.design.patterns.composite.iteratorsimple;

import cn.design.patterns.composite.iterator.MenuItem;

import java.util.Iterator;
import java.util.List;

/**
 * Created by LuoLiBing on 16/6/18.
 */
public class PancakeHouseMenuIterator implements Iterator<MenuItem> {

    List<MenuItem> menuItemList;

    int position = 0;

    public PancakeHouseMenuIterator(List<MenuItem> menuItems) {
        this.menuItemList = menuItems;
    }

    @Override
    public boolean hasNext() {
        return !(position >= menuItemList.size() || menuItemList.get(position) == null);
    }

    @Override
    public MenuItem next() {
        // 取出元素,position往后推一位
        return menuItemList.get(position++);
    }
}
