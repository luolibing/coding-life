package cn.design.patterns.composite.iteratorsimple;

import java.util.Iterator;

/**
 * Created by LuoLiBing on 16/6/18.
 */
public interface Menu<T> {
    Iterator<T> createIterator();
}
