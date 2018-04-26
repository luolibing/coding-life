package cn.tim.thinking.collection.complex17;

import org.junit.Test;

import java.util.*;

/**
 * Created by LuoLiBing on 16/12/27.
 */
public class AbstractCollection1 {

    /**
     * 享元设计模式
     *
     * 共享同一个元素, 例如String a = "abc"; String b = "abc"; 这种情况就可以使用享元模式, 在内存中只保存一份"abc", 同时
     * 创建只读的Map, 可以继承AbstractMap并实现entrySet()方法
     */
    public static class Countries {
        public final static String[][] DATA = {
                {"CHINA", "BEIJING"}, {"KOREA", "SHOUER"},
                {"USA", "纽约", "CANADA", "多伦多"},
                {"CONGO", "Brazzaville"}, {"CYPRUS", "Nicosia"},
                {"God", "G"}, {"Brind", "B"},
                {"Genth", "G"}, {"Eath", "e"}
        };

        // 继承自AbstractMap, 生成一个只读的Map
        private static class FlyweightMap extends AbstractMap<String, String> {

            // 实现了Entry接口, Entry中并没有保存实际的键值对, 而只是存储了一个索引值
            private static class Entry implements Map.Entry<String, String> {

                // 只存储数据的索引值, 不保存实际的key, value. 这正是享元模式
                int index;

                Entry(int index) {
                    this.index = index;
                }

                // Key和value都是临时获取到的值, Entry并不保存实际的KV
                @Override
                public String getKey() {
                    return DATA[index][0];
                }

                @Override
                public String getValue() {
                    return DATA[index][1];
                }

                @Override
                public String setValue(String value) {
                    throw new UnsupportedOperationException();
                }

                /**
                 * 重写equals, 通过判断key是否匹配即可
                 * @param obj
                 * @return
                 */
                @Override
                public boolean equals(Object obj) {
                    return DATA[index][0].equals(obj);
                }

                @Override
                public int hashCode() {
                    return DATA[index][0].hashCode();
                }
            }

            // EntrySet拥有一个size大小字段, 然后有一个迭代器, 迭代器中只有一个Entry, 通过entry中的index变化, 来实现迭代的过程.
            public static class EntrySet extends AbstractSet<Map.Entry<String, String>> {

                private int size;

                public EntrySet(int size) {
                    if(size < 0) {
                        this.size = 0;
                    } else if(size > DATA.length) {
                        this.size = DATA.length;
                    } else {
                        this.size = size;
                    }
                }

                @Override
                public Iterator<Map.Entry<String, String>> iterator() {
                    return new Iter();
                }

                @Override
                public int size() {
                    return this.size;
                }

                // 迭代器, 通过共享同一个Entry, 切换其中的index值, 来实现迭代. Entry被当做数据的视窗, 它只包含在静态字符串数组中的索引.
                // 遍历的时候, 通过Entry.index和size大小, 来进行遍历控制, 然后通过Entry中的equals方法进行判断, 当equals为true时, 返回对应的节点的值getValue()
                private class Iter implements Iterator<Map.Entry<String, String>> {

                    private Entry entry = new Entry(-1);

                    @Override
                    public boolean hasNext() {
                        return entry.index < size - 1;
                    }

                    @Override
                    public Map.Entry<String, String> next() {
                        entry.index ++;
                        return entry;
                    }
                }
            }

            private final static EntrySet entries = new EntrySet(DATA.length);

            @Override
            public Set<Map.Entry<String, String>> entrySet() {
                return entries;
            }
        }


        //
        static Map<String, String> select(final int size) {
            // 匿名内部类, 重写了entrySet方法
            return new FlyweightMap() {
                @Override
                public Set<Map.Entry<String, String>> entrySet() {
                    return new EntrySet(size);
                }
            };
        }

        static Map<String, String> map = new FlyweightMap();

        public static Map<String, String> capitals() {
            return map;
        }

        public static Map<String, String> capitals(int size) {
            return select(size);
        }

        static List<String> names = new ArrayList<>(map.keySet());

        public static List<String> names() {
            return names;
        }

        public static List<String> names(int size) {
            return new ArrayList<>(select(size).keySet());
        }


        public static void main(String[] args) {
            System.out.println(capitals(4));
            System.out.println(names(4));
            System.out.println(new HashMap<>(capitals(3)));
            System.out.println(new LinkedHashMap<>(capitals(3)));
            System.out.println(new TreeMap<>(capitals(3)));
            System.out.println(new Hashtable<>(capitals(3)));
            System.out.println(new HashSet<>(names(4)));
            System.out.println(new TreeSet<>(names(4)));
            System.out.println(new ArrayList<>(names(4)));
            System.out.println(new LinkedList<>(names(3)));
            System.out.println(capitals().get("CHINA"));
        }
    }


    /**
     * 这里也是一个典型的享元模式, 这个集合并不保存实际的list集合, 每次get()的时候临时生成数据
     */
    class CountingIntegerList extends AbstractList<Integer> {

        private int size;

        public CountingIntegerList(int size) {
            this.size = size < 0 ? 0 : size;
        }

        @Override
        public Integer get(int index) {
            return Integer.valueOf(index);
        }

        @Override
        public int size() {
            return this.size;
        }
    }

    @Test
    public void countingIntegerList() {
        System.out.println(new CountingIntegerList(30));
    }
}
