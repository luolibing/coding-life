package cn.tim.thinking.collection.complex17;

import org.junit.Test;

import java.util.*;

/**
 * Created by LuoLiBing on 16/12/27.
 * Collection的功能方法
 *
 */
public class CollectionDemo2 {

    @Test
    public void collectionMethods() {
        // add  addAll 添加元素
        Collection<String> c = new ArrayList<>();
        c.addAll(AbstractCollection1.Countries.names(6));
        c.add("ten");
        c.add("eleven");
        System.out.println(c);

        // toArray, 转换为数组, 当有泛型数组参数时, 可以返回对应的类型
        Object[] objs = c.toArray();
        String[] str = c.toArray(new String[0]);

        // Collections.max|min获取到集合中最大或者最小的值
        System.out.println("Collections.max(c) = " + Collections.max(c));
        System.out.println("Collections.min(c) = " + Collections.min(c));

        // 将一个集合加到另外一个集合里面 addAll
        Collection<String> c2 = new ArrayList<>();
        c2.addAll(AbstractCollection1.Countries.names(6));
        c.addAll(c2);
        System.out.println(c);

        // 删除元素 remove, removeAll,
        c.remove(AbstractCollection1.Countries.DATA[0][0]);
        System.out.println(c);
        c.remove(AbstractCollection1.Countries.DATA[1][0]);
        System.out.println(c);
        c.removeAll(c2);
        System.out.println(c);
        c.addAll(c2);

        // 判断一个元素是否在某个集合当中 contains(), containsAll
        String val = AbstractCollection1.Countries.DATA[3][0];
        System.out.println("c.contains(" + val + ") = " + c.contains(val));
        System.out.println("c.containsAll(c2) = " + c.containsAll(c2));

        // 子集合, retainAll 取交集, 只要发生了改变, 就返回true,表明有交集
        Collection c3 = ((List) c).subList(2, 5);
        c2.retainAll(c3);
        System.out.println(c2);

        // 删除c2和c3的交集, 清空
        c2.removeAll(c3);
        System.out.println("c2.isEmpty() = " + c2.isEmpty());
        c = new ArrayList<>();
        c.addAll(AbstractCollection1.Countries.names(6));
        System.out.println(c);
        c.clear();
        System.out.println("after c.clear(): " + c);
    }

    /**
     * 可选操作, 执行各种不同的添加和移除的方法在Collection接口中都是可选操作. 这些方法会抛出异常.
     * 这种可选操作的目的, 是为了防止接口爆炸的情况. 未获支持的操作.
     * 1 UnsupportedOperationException必须是一种罕见事件
     * 2 如果一个操作是未获支持的, 那么在实现接口的时候可能就会导致UnsupportedOperationException异常.
     */
    @Test
    public void unSuppported() {
        // 固定大小的list, 不支持改动list大小的所有操作. 但是可以改变它的值
        List<Integer> ints = Arrays.asList(1, 2, 3, 4);
        try {
            ints.remove(1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            ints.add(1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ints.set(0, 100);

        // 不可以修改的list
        List<Integer> ints2 = Collections.unmodifiableList(ints);
        try {
            ints2.set(0, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
