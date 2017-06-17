package cn.design.patterns.composite.composite;


import java.util.Iterator;

/**
 * Created by LuoLiBing on 16/6/20.
 */
public class NullIterator implements Iterator {

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public Object next() {
        return null;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
