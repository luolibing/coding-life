package cn.tim.http.utils;

/**
 * User: luolibing
 * Date: 2017/7/13 14:45
 */
public interface Functions<E> {

    static  <E extends Enum<E>> Object applyDefault(Enum<E> type) {
        return type.name();
    }

    default <E extends Enum<E>> Object apply(Enum<E> type) {
        return type.name();
    }
}
