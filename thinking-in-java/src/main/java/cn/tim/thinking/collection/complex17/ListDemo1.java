package cn.tim.thinking.collection.complex17;

import org.junit.Test;

import java.util.*;

/**
 * Created by LuoLiBing on 16/12/27.
 */
public class ListDemo1 {


    /**
     * List接口的操作
     */
    @Test
    public void lists() {
        List<String> list = new ArrayList<>(Arrays.asList("a", "bf", "fe", "3ed", "vf"));
        list.add("c");
        // 在某个位置插入
        list.add(2, "bk");
        System.out.println(list);

        boolean b = list.contains("vf");
        b = list.containsAll(Arrays.asList("vf", "3ed"));

        String s = list.get(0);
        int i = list.indexOf("fe");
        b = list.isEmpty();

        Iterator<String> it = list.iterator();
        // listIterator迭代器
        ListIterator<String> lit = list.listIterator(3);

        i = list.lastIndexOf("a");
        list.remove(0); // 根据索引值删除
        list.remove("bf"); // 根据内容删除

        list.set(2, "fk");
        list.retainAll(AbstractCollection1.Countries.names(10));
        list.removeAll(AbstractCollection1.Countries.names(5));
        i = list.size();
        list.clear();
    }

    @Test
    public void iterMotion() {
        List<String> list = AbstractCollection1.Countries.names();
        ListIterator<String> it = list.listIterator();
        // ListIterator增强迭代器, 向前和向后都支持
        boolean b = it.hasNext();
        String s = it.next();
        it.nextIndex();
        b = it.hasPrevious();
        s = it.previous();
        it.previousIndex();
    }

    @Test
    public void list() {
        List<String> names = AbstractCollection1.Countries.names();
        List<String> list1 = new ArrayList<>(names);
        List<String> list2 = new LinkedList<>(names);

        Iterator<String> it1 = list1.iterator();
        while (it1.hasNext()) {
            System.out.println(it1.next());
        }

        it1 = list2.iterator();
        while (it1.hasNext()) {
            System.out.println(it1.next());
        }

        // 当前索引值
        int alindex = 0;
        ListIterator<String> it2 = list2.listIterator();
        while (it2.hasNext()) {
            // 将list2插入到list1当中, 每个一个插入一个
            list1.add(alindex, it2.next());
            // 索引值+2
            alindex += 2;
        }

        // 由后向前
        ListIterator<String> it3 = list1.listIterator(list1.size());
        while (it3.hasPrevious()) {
            System.out.println(it3.previousIndex() + ": " + it3.previous());
        }
    }


    /**
     * 自行实现的ListIterator
     * @param <T>
     */
    interface SListIterator<T> {
        boolean hasNext();
        T next();
        void remove();
        void add(T element);
    }


    static class SList<T> {

        // 初始化的时候list为空, 只有一个Header节点
        private final Link<T> header = new Link<>(null, null);

        @Override
        public String toString() {
            StringBuilder buf = new StringBuilder();
            buf.append("[");
            for(SListIterator<T> it = iterator(); it.hasNext(); ) {
                T element = it.next();
                buf.append(element == this ? "(this SList)" : String.valueOf(element));
                if(it.hasNext()) {
                    buf.append(", ");
                }
            }
            buf.append("]");
            return buf.toString();
        }


        public SListIterator<T> iterator() {
            return null;
        }

        // 链接
        private static class Link<T> {
            // 节点值
            T element;
            Link<T> next;

            Link(T element, Link<T> next) {
                this.element = element;
                this.next = next;
            }
        }

        // SListIterator迭代器
        private class SListIteratorImpl implements SListIterator<T> {

            private Link<T> lastReturned = header;

            private Link<T> next;

            SListIteratorImpl() {
                next = header.next;
            }

            @Override
            public boolean hasNext() {
                return next != header;
            }

            // 移动到下一个节点
            @Override
            public T next() {
                if(next == header) {
                    throw new NoSuchElementException();
                }
                lastReturned = next;
                next = next.next;
                return lastReturned.element;
            }

            @Override
            public void remove() {
                if(lastReturned == header) {
                    throw new IllegalStateException();
                }
                for(Link<T> curr = header; ; curr = curr.next) {
                    if(curr.next == lastReturned) {
                        curr.next = lastReturned.next;
                        break;
                    }
                }
                // 删除之后, 又回到了头部
                lastReturned = header;
            }

            @Override
            public void add(T element) {
                lastReturned = header;
                Link<T> newLink = new Link<>(element, next);
                if(header.next == header) {
                    header.next = newLink;
                } else {
                    for(Link<T> curr = header; ; curr = curr.next) {
                        if(curr.next == next) {
                            curr.next = newLink;
                            break;
                        }
                    }
                }
            }
        }

        public static void main(String[] args) {
            System.out.println("Demonstrating SListIterator...");
            SList<String> sl = new SList<>();
            System.out.println(sl);

        }
    }
}
