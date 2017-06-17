package cn.tim.thinking.collection.complex17;

import org.junit.Test;

import java.util.*;

/**
 * Created by LuoLiBing on 16/12/28.
 */
public class SetDemo1 {

    /**
     * Set
     * Set(interface)   存入Set的每个元素都必须是唯一的, 因为Set不保存重复元素, 加入Set的元素必须定义equals()方法以确保对象的唯一性, Set接口不保证元素的次序
     * HashSet          为快速查找而设计的Set. 存入的HashSet的元素必须定义hashCode()
     * TreeSet          能够排序的Set, 底层为树结构. 元素必须实现Comparable接口
     * LinkedHashSet    具有HashSet的查询速度, 且内部使用链表维护元素的插入顺序.
     *
     * hashSet和LinkedHashSet必需要有hashCode()方法. 同时对于良好的编程风格而言, 你应该在覆盖equals()方法的同时, 总是覆盖hashCode()方法.
     *
     */
    // set存储元素
    static class SetType {
        int i;
        public SetType(int n) {
            this.i = n;
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof SetType && ((SetType) obj).i == this.i;
        }

        @Override
        public String toString() {
            return Integer.toString(i);
        }
    }

    // hashSet存储元素, 对于hashSet来说, 存储元素必须有自身的hashCode方法.
    static class HashType extends SetType {

        public HashType(int n) {
            super(n);
        }

        @Override
        public int hashCode() {
            return i;
        }
    }

    /**
     * treeSet存储对象,需要实现Comparable接口, compareTo()不能简单的使用return i1-i2; 因为这样对于有符号的整数, 有可能出现算数溢出.
     * 通常会希望compareTo()方法可以产生和equals()方法一致的自然排序. 如果equals()=true, 那么compareTo()应该等于0, equals()=false, compareTo()应该为非0值.
     * 如果没有重新定义hashCode()方法, 如果将他们放置到任何散列实现中都有可能会产生重复值, 这样就违法了Set的基本契约.
     */
    static class TreeType extends SetType implements Comparable<TreeType> {

        public TreeType(int n) {
            super(n);
        }

        @Override
        public int compareTo(TreeType o) {
            return i > o.i ? 1 : (i == o.i ? 0 : -1);
        }
    }

    static class TypesForSets {
        static <T> Set<T> fill(Set<T> set, Class<T> clazz) {
            try {
                for (int i = 0; i < 10; i++) {
                    // 通过反射获取到传递给类型, 然后获取到对应的构造函数, 通过构造函数进行实例初始化
                    set.add(clazz.getConstructor(int.class).newInstance(i));
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return set;
        }

        static <T> void test(Set<T> set, Class<T> type) {
            fill(set, type);
            fill(set, type);
            fill(set, type);
            System.out.println(set);
        }
    }

    @Test
    public void typesForSets() {
        // 正常使用
        TypesForSets.test(new HashSet<>(), HashType.class);
        TypesForSets.test(new LinkedHashSet<>(), HashType.class);
        TypesForSets.test(new TreeSet<>(), TreeType.class);

        // TreeSet需要存储的元素实现Comparable接口, HashSet需要类有hashCode方法
        TypesForSets.test(new HashSet<>(), SetType.class);
        TypesForSets.test(new HashSet<>(), TreeType.class);
        TypesForSets.test(new LinkedHashSet<>(), SetType.class);
        TypesForSets.test(new LinkedHashSet<TreeSet>(), TreeSet.class);

        // 因为SetType没有实现Comparable接口, 所以进行强转的时候会抛出强转异常
        TypesForSets.test(new TreeSet<>(), SetType.class);

        TypesForSets.test(new TreeSet<>(), HashType.class);
    }


    /**
     * SortedSet
     */
    @Test
    public void sortedSet() {
        TreeSet<String> treeSet = new TreeSet<>(Arrays.asList("a", "b", "d", "c", "z", "o", "j"));
        // SortedSet.comparator()返回当前set使用的comparator, 如果返回null, 则表示以自然方式排序
        Comparator<? super String> comparator = treeSet.comparator();
        System.out.println(comparator);
        System.out.println(treeSet);

        // 返回首个/最后一个
        treeSet.first();
        treeSet.last();

        // subSet()返回从a元素到c元素之间的子set, from包含, to不包含
        System.out.println(treeSet.subSet("a", "c"));

        // headSet()返回小于j的子set, 不包括j
        System.out.println(treeSet.headSet("j"));

        // tailSet()返回大于或等于d的子set
        System.out.println(treeSet.tailSet("d"));
    }


    class CustomerSortedSet<T> implements SortedSet<T> {

        private final List<T> list;

        public CustomerSortedSet() {
            this.list = new LinkedList<>();
        }

        private CustomerSortedSet(List<T> list) {
            this.list = list;
        }

        @Override
        public Comparator<? super T> comparator() {
            return null;
        }

        @Override
        public SortedSet<T> subSet(T fromElement, T toElement) {
            checkForNull(fromElement);
            checkForNull(toElement);
            int from = Collections.binarySearch((List<? extends Comparable<? super T>>) list, fromElement);
            int to = Collections.binarySearch((List<? extends Comparable<? super T>>) list, toElement);
            checkForValidIndex(from);
            checkForValidIndex(to);
            try {
                return new CustomerSortedSet<>(list.subList(from, to));
            } catch (IndexOutOfBoundsException e) {
                throw new IllegalArgumentException(e);
            }
        }

        @Override
        public SortedSet<T> headSet(T toElement) {
            checkForNull(toElement);
            int to = Collections.binarySearch((List<? extends Comparable<? super T>>) list, toElement);
            checkForValidIndex(to);
            try {
                return new CustomerSortedSet<>(list.subList(0, to));
            } catch (IndexOutOfBoundsException e) {
                throw new IllegalArgumentException(e);
            }
        }

        @Override
        public SortedSet<T> tailSet(T fromElement) {
            checkForNull(fromElement);
            int from = Collections.binarySearch((List<? extends Comparable<? super T>>) list, fromElement);
            checkForValidIndex(from);
            try {
                return new CustomerSortedSet<>(list.subList(from, list.size()));
            } catch (IndexOutOfBoundsException e) {
                throw new IllegalArgumentException(e);
            }
        }

        @Override
        public T first() {
            try {
                return list.get(0);
            } catch (IndexOutOfBoundsException e) {
                throw new NoSuchElementException();
            }
        }

        @Override
        public T last() {
            try {
                return list.get(list.size() - 1);
            } catch (IndexOutOfBoundsException e) {
                throw new NoSuchElementException();
            }
        }

        @Override
        public int size() {
            return list.size();
        }

        @Override
        public boolean isEmpty() {
            return list.isEmpty();
        }

        /**
         * 因为是排好序的查找, 所以使用二分查找比使用线性查找快很多
         * @param o
         * @return
         */
        @Override
        public boolean contains(Object o) {
            checkForNull(o);
            return Collections.binarySearch((List<? extends Comparable<? super Object>>) list, o) >= 0;
        }

        @Override
        public Iterator<T> iterator() {
            return list.iterator();
        }

        @Override
        public Object[] toArray() {
            return list.toArray();
        }

        @Override
        public <T1> T1[] toArray(T1[] a) {
            return list.toArray(a);
        }

        /**
         * 返回值false表明插入之前已经存在, true表明插入之前不存在
         * @param t
         * @return
         */
        @Override
        public boolean add(T t) {
            checkForNull(t);
            // 使用二分查找, 因为sortedSet本身就是有序的
            int index = Collections.binarySearch((List<? extends Comparable<? super T>>) list, t);
            if(index < 0) {
                // 没有查找到, 则表明第一次插入, 然后根据返回值的计算公式 = -插入点-1, 这样可以计算出插入点=-(返回值+1)
                index = - (index + 1);
                // 如果插入点是末尾, 则直接使用add
                if(index == list.size()) {
                    list.add(t);
                } else {
                    list.add(index, t);
                }
                return true;
            }
            return false;
        }

        @Override
        public boolean remove(Object o) {
            checkForNull(o);
            return list.remove(o);
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            checkForNull(c);
            return list.containsAll(c);
        }

        @Override
        public boolean addAll(Collection<? extends T> c) {
            checkForNull(c);
            checkForNullElements(c);
            boolean res = false;
            for(T t : c) {
                res |= add(t);
            }
            return res;
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            checkForNull(c);
            return list.retainAll(c);
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            checkForNull(c);
            return list.removeAll(c);
        }

        @Override
        public void clear() {
            list.clear();
        }

        private void checkForNull(Object o) {
            if(o == null)
                throw new NullPointerException();
        }
        private void checkForValidIndex(int idx) {
            if(idx < 0)
                throw new IllegalArgumentException();
        }

        private void checkForNullElements(Collection<?> c) {
            for(Iterator<?> it = c.iterator(); it.hasNext();)
                if(it.next() == null)
                    throw new NullPointerException();
        }

        @Override
        public String toString() {
            return "CustomerSortedSet{" +
                    "list=" + list +
                    '}';
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof CustomerSortedSet && list.equals(((CustomerSortedSet)obj).list);
        }

        @Override
        public int hashCode() {
            return list.hashCode();
        }
    }

    @Test
    public void customerSortedSet() {
        SortedSet<String> set = new CustomerSortedSet<>();
        set.add("a");
        set.add("b");
        set.add("z");
        set.add("x");
        set.add("d");
        set.add("c");

        System.out.println(set);
        System.out.println(set.contains("c"));
        System.out.println(set.subSet("a", "d"));
        System.out.println(set.headSet("y"));
        System.out.println(set.remove("b"));
    }
}
