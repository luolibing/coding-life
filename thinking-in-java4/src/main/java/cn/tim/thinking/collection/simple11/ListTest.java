package cn.tim.thinking.collection.simple11;

import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Created by LuoLiBing on 16/8/26.
 * List接口
 * 基本的ArrayList: 它长于随机访问元素, 但是在List的中间插入和移除元素时较慢
 * LinkedList,双向链表实现,插入删除操作性能好,不善于随机访问
 */
public class ListTest {

    /**
     *
     *
     * ArrayList如果不指定容量,会默认生成容量为10的Object[]数组
     * 然后当对list进行添加操作时,会将对象放置到对应的位置,当list的size等于数组的大小时,会自动扩容为当前长度的1.5倍
     * 还可以使用trimToSize()方法对list进行瘦身,将空余的位置全部删掉
     * ArrayList底层是数组, Object[] elementData;
     * 擅长的是随机访问元素,但是在中间插入和移除元素时,需要整体移位所以效率会低一些,使用的是Arrays.copyOf,最终使用底层的System.arraycopy方法
     * 当使用list.add(int,int)方法制定插入时,会对索引值进行检查,rangeCheckForAdd,如果索引值大于size或者小于0都会抛出IndexOutOfBoundsException异常
     * 迭代器统一了对容器的访问方式
     *
     * ArrayList实现了AccessRandom接口,这个接口是一个标记接口,用来表明其支持快速随机访问.此接口的主要目的是允许一般的算法更改其行为,从而在将其应用在随机或者连续列表访问时提供更好的性能
     * 对于实现了AccessRandom接口的List,使用for(int i=0; i<list.size(); i++)的运行速度要快于使用迭代器for(Int i : list)
     *
     */
    @Test
    public void arrayList() {
        ArrayList<Integer> list = new ArrayList<>(1);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(1);
        list.add(1);
        // list.add(5, 1);  IndexOutOfBoundsException
        list.remove(1);
        list.removeIf((t) -> t >= 2);
        list.add(null);
        list.trimToSize(); // 将数组大小缩小
        Iterator<Integer> it = list.iterator();
        while (it.hasNext()) {
            // 调用remove之前必须先调用it.next()
            System.out.println(it.next());
//            it.remove();
            // 通过一个modCount变量(类似版本号)来防止使用迭代器遍历的时候,list被修改,ConcurrentModificationException
            // list.add(4);
        }
        // 提升数组大小的方式
        list.ensureCapacity(20);
        System.out.println(list);

        /**
         * ListIterator专门用于list的迭代,可以双向遍历,还可以从指定位置开始遍历
         */
        ListIterator listIterator = list.listIterator();
        // 向前
        while (listIterator.hasNext()) {
            // 可以获取下一个索引值和上一个索引值
            System.out.println(listIterator.next() + "," + listIterator.nextIndex() + "," + listIterator.previousIndex() + ";");
        }

        System.out.println();
        // 向后
        while (listIterator.hasPrevious()) {
            System.out.println(listIterator.previous() + ";");
        }

        System.out.println();
        // 指定位置处开始迭代
        ListIterator<Integer> iterator = list.listIterator(2);
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
            // 还可以修改对应元素的值,这在其他的迭代器中是不允许的
            iterator.set(30);
        }

        // 由后向前遍历的方式
        System.out.println();
        ListIterator<Integer> reverse = list.listIterator(list.size() - 1);
        while (reverse.hasPrevious()) {
            System.out.println(reverse.previous());
        }
    }

    /**
     * LinkedList底层是用双向链表实现的
     * 所以随机访问性能较慢,但是插入和删除性能较高,直接操作链表添加与删除.
     * 提供了优化的顺序访问,LinkedList可以实现Queue,stack等等数据结构,性能较好
     * LinkedList要随机访问,得从链表的first或者last遍历起,一个一个的访问,直到对应元素
     *
     *
     */
    @Test
    public void linkedList() {
        LinkedList<Integer> list = new LinkedList<>();
        // list.getFirst(); // 获取头结点,当头结点为null时,会抛出NoSuchElementException异常
        //
        list.addFirst(3);
        list.addFirst(4);
        // before
        list.add(1, 5);
        list.get(3);
        // list.node(index)查找索引值,会判断索引大小与size/2的比较,如果处于后端,则从队尾开始查找
        list.remove(2);
        System.out.println(list.set(0, 1)); // 替换对应坐标上的值,并且返回老的值
        Integer peek = list.peek(); // list.peek() 获取first,但是不移除
        list.element(); // 获取first,当为null时,抛出NoSuchElementException异常
        list.poll(); // 移除first,当first为null时,返回null
        list.remove(); // 移除first,为null时,抛出异常

        // 队列方式
        list.offer(3); // 添加到队尾
        list.offerFirst(1); // 添加到队头
        list.offerLast(3); // 队尾

        // stack 栈
        list.push(3);
        list.pop();

        // 移除第一个对应的值
        list.removeFirstOccurrence(3);
        list.removeLastOccurrence(4);

        System.out.println("peek=" + peek);
        System.out.println(list.size());
        list.add(1);
        list.add(2);

        list.clear(); // clear将所以的结点前后都置为null,有利于GC快速回收所有的结点
    }

    @Test
    public void stack() {
        LinkedList<Character> stack = new LinkedList<>();
        String str = "+U+n+c---+e+r+t---+a-+i-+n+t+y---+a+r+u--+1+e+s--";
        char[] chars = str.toCharArray();
        boolean push = false;
        for(char ch : chars) {
            if(ch == '+') {
                push = true;
            } else if(ch == '-') {
                push = false;
            } else {
                if(push) {
                    stack.push(ch);
                } else {
                    stack.pop();
                }
            }
        }
        Iterator<Character> it = stack.iterator();
        while(it.hasNext()) {
            System.out.println(it.next());
        }
        final Path path = Paths.get("http://www.baidu.com/", "/index/index.html");
        System.out.println(path.toString());
    }

}
