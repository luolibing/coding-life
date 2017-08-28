package java.util;

/**
 * User: luolibing
 * Date: 2017/8/28 16:10
 */
public class ArrayListProvider {

    public static <E> void executeAdd(ArrayList<E> data, E e) {
        data.add(e);
    }
}
