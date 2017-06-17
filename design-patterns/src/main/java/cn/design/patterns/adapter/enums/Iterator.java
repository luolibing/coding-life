package cn.design.patterns.adapter.enums;

/**
 * Created by LuoLiBing on 16/6/10.
 */
public interface Iterator<E> {
    boolean hasNext();
    E next();
    E remove();
}
