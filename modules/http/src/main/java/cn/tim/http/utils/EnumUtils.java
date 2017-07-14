package cn.tim.http.utils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 枚举帮助类
 * User: luolibing
 * Date: 2017/7/12 18:00
 */
public class EnumUtils {


    /**
     * 根据名称查找对应的枚举
     * @param type
     * @param name
     * @param <E>
     * @return
     */
    public static <E extends Enum<E>> Optional<E> valueFor(Class<E> type, String name) {
        return Arrays.stream(type.getEnumConstants())
                .filter(x ->  x.name().equals(name))
                .findFirst();
    }


    /**
     * 根据其他属性查找对应的枚举
     * @param type
     * @param v
     * @param valueSupplier
     * @param <E>
     * @param <V>
     * @return
     */
    public static <E extends Enum<E>, V> List<E> valueFor(
            Class<E> type, V v, Function<E, V> valueSupplier) {

        if(Objects.isNull(v)) {
            return Collections.emptyList();
        }

        return Arrays.stream(type.getEnumConstants()).filter(x ->  {
            V compare = valueSupplier.apply(x);
            return Objects.equals(compare, v);
        }).collect(Collectors.toList());
    }


    /**
     * 根据其他属性查找对应的枚举
     * @param type
     * @param v
     * @param valueSupplier
     * @param <E>
     * @param <V>
     * @return
     */
    public static <E extends Enum<E>, V> Optional<E> valueForSingle(
            Class<E> type, V v, Function<E, V> valueSupplier) {

        List<E> enumList = valueFor(type, v, valueSupplier);
        return enumList.isEmpty() ? Optional.empty() : Optional.of(enumList.get(0));
    }
}
