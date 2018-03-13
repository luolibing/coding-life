package cn.tim.java8.concurrent;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

/**
 * User: luolibing
 * Date: 2018/3/7 9:50
 */
public class Parent<E> implements Collection<Object> {

    private Vector<Object> objectCollection;

    Parent() {
        this.objectCollection = new Vector<Object>();
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Child iterator() {
        return(new Child());
    }

    @NotNull
    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @NotNull
    @Override
    public <T> T[] toArray(@NotNull T[] a) {
        return null;
    }

    @Override
    public boolean add(Object object) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(@NotNull Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(@NotNull Collection<?> c) {
        return false;
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    public class Child implements Iterator<Object> {

        private int index;

        Child() {
            this.index = 0;
        }

        @Override
        public boolean hasNext() {
            int size = objectCollection.size();
            if (size <= 0) return(false);
            if (this.index >= size) return(false);
            return(true);
        }

        @Override
        public Object next() {
            Object temp = objectCollection.elementAt(this.index);
            this.index++;
            return(temp);
        }

        public Vector<Object> getObjectCollection() {
            return objectCollection;
        }
    }
}
