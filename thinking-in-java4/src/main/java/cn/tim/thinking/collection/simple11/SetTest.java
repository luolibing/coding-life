package cn.tim.thinking.collection.simple11;

import org.junit.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by LuoLiBing on 16/8/28.
 * Set
 *
 */
public class SetTest {

    /**
     * HashSet
     * 1 不保证顺序,也不能保证随着时间推移顺序不会改变.
     * 2 hasSet底层是用HashMap实现的,所以也会有与hashMap一样可以设置initCapacity和load factor
     * 3 hashSet.iterator()返回的是hashMap.keySet().iterator();
     * 4 hashSet里面的value,始终是同一个PRESENT
     * 5 hashSet为什么能去重,这个是与hashMap.put()方法相关,当查询到key节点时,会覆盖掉节点的value
     * 6 hashSet的add(),当要添加的元素不存在时,返回true,当要添加的元素已经存在则返回false   map.put(e, PRESENT)(返回上一个映射的value) == null
     * 7 HashSet允许添加null元素
     * 8 迭代时间与hashMap的capacity有关,因为需要遍历数组,然后再遍历数组中的元素
     *
     * hashSet的优势:
     * 1 hashSet的底层实现是HashMap,所以它的查找速度会很快
     *
     *
     */
    @Test
    public void hashSet() {
        HashSet hashSet = new HashSet();
        for(int i = 0; i< 100; i++) {
            System.out.println(hashSet.add(i));
        }
        System.out.println(hashSet.add(1));
        hashSet.add(null);
        hashSet.forEach(System.out::println);
        hashSet.forEach(System.out::println);
        hashSet.forEach(System.out::println);

        hashSet.contains(10);
        // 使用同步的set
        final Set set = Collections.synchronizedSet(hashSet);


    }

    /**
     * LinkedHashSet
     * 1 保证插入的顺序,当元素re-insert时,并不会影响他的顺序
     * 2 插入删除性能略微低于HashSet,因为需要维持一个链表
     * 3 迭代一个LinkedHashSet所需时间只与size有关,因为有链表来维持关系.所以性能会比HashSet强
     * 4 LinkedHashSet有两个重要的性能参数: initial capacity和load factor负载因子
     *
     * LinkedHashSet底层实现
     * 1 是HashSet的子类, 由hashSet来提供功能,提供了一个专门给LinkedHashSet使用的构造函数
     * 2 底层通过LinkedHashMap来实现
     *
     */
    @Test
    public void linkedHashSet() {
        LinkedHashSet<Integer> linkedHashSet = new LinkedHashSet();
        System.out.println(linkedHashSet.add(1));
        System.out.println(linkedHashSet.add(2));
        System.out.println(linkedHashSet.add(1));
        System.out.println(linkedHashSet.add(null));

        for(Integer i : linkedHashSet) {
            System.out.println(i);
        }
    }

    public static void main(String[] args) {
        // sipush  200  Integer.valueOf(int) 自动拆装箱
        Integer i = 200, j = 200;// -128 127
        String str = "aaaa";
        System.out.println(str);
        Integer x = 1, y = 1;
        System.out.println("i=j:" + (i==j));
        System.out.println("x=y:" + (x==y));
        HashSet hashSet = new HashSet();

        System.out.println(hashSet.add("aaa"));
        System.out.println(hashSet.add("aaa"));
    }

    @Test
    public void testAddAll() {
        // for-each调用的时候会抛空指针
        HashSet<Object> set = new HashSet<>(null);
        System.out.println(set);
    }

    @Test
    public void forEach() {
        Set<String> set = null;
        // 空指针，因为会调用set.iterator
       for(String str : set) {
            System.out.println(str);
        }
    }
}
