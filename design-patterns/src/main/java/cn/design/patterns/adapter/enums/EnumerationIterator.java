package cn.design.patterns.adapter.enums;

/**
 * Created by LuoLiBing on 16/6/10.
 */
public class EnumerationIterator<T> implements Iterator<T> {

    private Enumeration<T> enumeration;

    public EnumerationIterator(Enumeration<T> enumeration) {
        this.enumeration = enumeration;
    }

    @Override
    public boolean hasNext() {
        return enumeration.hasMoreElements();
    }

    @Override
    public T next() {
        return enumeration.nextElement();
    }

    @Override
    public T remove() {
        throw new UnsupportedOperationException("Not support");
    }
}
