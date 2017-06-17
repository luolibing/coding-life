package cn.tim.phonenumber.generate;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by LuoLiBing on 16/8/26.
 */
public class SortMapUtils {

    /**
     * 将map按照
     * @param map
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V extends Comparable<? super V>> Map<K,V> sortByValue(Map<K, V> map, Comparator<V> comparator, int limit) {
        Stream<Map.Entry<K, V>> stream = map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(comparator));
        if(limit > 0) {
            stream = stream.limit(limit);
        }
        return stream.collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (v1, v2) -> v1,
                        LinkedHashMap::new));
    }
}
