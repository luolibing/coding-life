package cn.tim.thinking.collection.complex17;

import cn.tim.thinking.io.TextFileDemo1;
import org.junit.Test;

import java.util.*;

/**
 * Created by LuoLiBing on 16/12/27.
 *
 */
public class AbstractCollection2 {

    static class CountingMapData extends AbstractMap<Integer, String> {

        private int size;

        private static String[] chars = "A B C D E F G H I J K L M N O P Q R S T U V W X Y Z".split(" ");

        public CountingMapData(int size) {
            this.size = size < 0 ? 0 : size;
        }


        // Entry节点信息, 只包含索引值
        private static class Entry implements Map.Entry<Integer, String> {

            private int index;

            Entry(int index) {
                this.index = index;
            }

            @Override
            public Integer getKey() {
                return index;
            }

            @Override
            public String getValue() {
                return chars[index % chars.length] + Integer.toString(index / chars.length);
            }

            @Override
            public String setValue(String value) {
                throw new UnsupportedOperationException();
            }

            @Override
            public int hashCode() {
                return Integer.valueOf(index).hashCode();
            }

            @Override
            public boolean equals(Object obj) {
                return Integer.valueOf(index).equals(obj);
            }
        }


        // EntrySet
        class EntrySet extends AbstractSet<Map.Entry<Integer, String>> {

            private int size;

            public EntrySet(int size) {
                if(size < 0) {
                    this.size = 0;
                } else if(size > chars.length) {
                    this.size = chars.length;
                } else {
                    this.size = size;
                }
            }

            @Override
            public Iterator<Map.Entry<Integer, String>> iterator() {
                return new Iter();
            }

            @Override
            public int size() {
                return this.size;
            }

            // 迭代器
            class Iter implements Iterator<Map.Entry<Integer, String>> {

                private Entry entry = new Entry(-1);

                @Override
                public boolean hasNext() {
                    return entry.index < chars.length - 1;
                }

                @Override
                public Map.Entry<Integer, String> next() {
                    entry.index ++;
                    return entry;
                }
            }
        }

        // 因为没有重写EntrySet, 所以这个地方享元模式并没有得到完全实现
        @Override
        public Set<Map.Entry<Integer, String>> entrySet() {
//            Set<Map.Entry<Integer, String>> entries = new LinkedHashSet<>();
//            for(int i = 0; i < size; i++) {
//                entries.add(new Entry(i));
//            }
//            return entries;
            return new EntrySet(this.size);
        }

        public static void main(String[] args) {
            System.out.println(new CountingMapData(60));
        }
    }


    /**
     * 集合排序Collections.sort(list1, Comparator), Collections.shuffle(list)打乱集合中的顺序
     */
    @Test
    public void test1() {
        ArrayList<String> list1 = new ArrayList<>(AbstractCollection1.Countries.names);
        Collections.sort(list1, String.CASE_INSENSITIVE_ORDER);
        System.out.println("list1 = " + list1);

        ArrayList<String> list2 = new ArrayList<>(AbstractCollection1.Countries.names);
        Collections.sort(list2, String.CASE_INSENSITIVE_ORDER);
        System.out.println("list2 = " + list2);

        for(int i = 0; i <10; i++) {
            Collections.shuffle(list1);
            System.out.println("list1 = " + list1);
        }
    }

    @Test
    public void test2() {
        // 放入到已经排序的TreeMap和TreeSet
        TreeMap<String, String> treeMap = new TreeMap<>(AbstractCollection1.Countries.capitals());
        TreeSet<String> treeSet = new TreeSet<>(AbstractCollection1.Countries.names);
        Iterator<String> it = treeSet.iterator();
        String n = null;
        while (it.hasNext()) {
            String name = it.next();
            if(!name.startsWith("C")) {
                n = name;
                break;
            }
        }

        // 获取子Map或者子Set
        Map<String, String> aMap = treeMap.headMap(n);
        Set<String> aSet = treeSet.headSet(n);
        System.out.println("aMap = " + aMap);
        System.out.println("aSet = " + aSet);
    }


    public void pushData(Set<String> set, List<String> names) {
        for(int i = 0; i < 5; i++) {
            set.addAll(names);
        }
        System.out.println("set = " + set);
    }

    @Test
    public void pushData() {
        List<String> names = AbstractCollection1.Countries.names();
        pushData(new HashSet<String>(), names);
        pushData(new LinkedHashSet<String>(), names);
        pushData(new TreeSet<String>(), names);
    }


    @Test
    public void test4() {
        TextFileDemo1 tf = new TextFileDemo1("/Users/tim/Documents/samples-tim/thinking-in-java/src/main/java/cn/tim/thinking/collection/complex17/AbstractCollection2.java", "\\s+");
        for(String str : tf) {
            System.out.println(str);
        }
    }

}
