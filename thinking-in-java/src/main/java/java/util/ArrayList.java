package java.util;

/**
 * User: luolibing
 * Date: 2017/8/28 16:03
 */
public class ArrayList<E> {

    private List<E> data = new LinkedList<E>();

    public boolean add(E e) {
        return data.add(e);
    }

    public boolean myAdd(E e) {
        return add(e);
    }
}
