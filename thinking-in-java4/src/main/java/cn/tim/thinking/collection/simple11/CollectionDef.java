package cn.tim.thinking.collection.simple11;

import org.junit.Test;

import java.util.*;

/**
 * Created by LuoLiBing on 16/8/23.
 * Collection,一个独立元素的序列
 *
 */
public class CollectionDef {

    /**
     * Map是允许你将某些对象和其他一些对象关联起来的关联数组
     */
    public void map() {

    }

    /**
     * 泛型,add时可以使用向上转型的方式.
     */
    @SuppressWarnings("uncheck")
    public void genericity() {
        List<? super Apple> list = new ArrayList<>();
        list.add(new Apple());
        list.add(new RedGranny());
    }

    class Apple extends Flut {

    }

    class Granny extends Apple {

    }

    class RedGranny extends Apple {

    }

    class Flut {

    }

    @Test
    public void gerbil() {
        List<Gerbil> gerbils = new ArrayList<>();
        gerbils.add(new Gerbil(1));
        gerbils.add(new Gerbil(2));
        gerbils.add(new Gerbil(3));
        for(Gerbil gerbil : gerbils) {
            gerbil.hop();
        }
    }

    class Gerbil {

        private int bilNumber;

        public Gerbil(int bilNumber) {
            this.bilNumber = bilNumber;
        }

        public int gerBilNumber() {
            return bilNumber;
        }

        public void hop() {
            System.out.println("i am " + bilNumber + ", hop!!!");
        }

        @Override
        public String toString() {
            return "Gerbil{" +
                    "bilNumber=" + bilNumber +
                    '}';
        }
    }

    @Test
    public void collection2() {
        Collection<Integer> c
                = new HashSet<>();
                //= new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            c.add(i);
        }
        for(Integer i : c) {
            System.out.println(i);
        }
    }

    @Test
    public void test2() {
        List<Gerbil> gerbilList = new ArrayList<>();
        display(gerbilList.iterator());
        display(gerbilList);
    }

    public static void display(Iterator<Gerbil> iterator) {
        while (iterator.hasNext()) {
            Gerbil gerbil = iterator.next();
            System.out.println(gerbil.bilNumber + ":" + gerbil);
        }
        System.out.println();
    }

    public static void display(Collection<Gerbil> collection) {
        for(Gerbil gerbil : collection) {
            System.out.println(gerbil.bilNumber + ":" + gerbil);
        }
    }

    @Test
    public void test3() {
        CollectionSequence c = new CollectionSequence();
        display(c);
        display(c.iterator());
    }


    public class CollectionSequence extends AbstractCollection<Gerbil> {

        private Gerbil[] gerbils = new Gerbil[] {new Gerbil(1), new Gerbil(2), new Gerbil(3), new Gerbil(4), new Gerbil(5)};

        @Override
        public Iterator<Gerbil> iterator() {
            return new Iterator<Gerbil>() {

                private int index = 0;

                @Override
                public boolean hasNext() {
                    return index < gerbils.length;
                }

                @Override
                public Gerbil next() {
                    return gerbils[index++];
                }

            };
        }

        @Override
        public int size() {
            return gerbils.length;
        }
    }

    public class GerbilSequence {
        protected Gerbil[] gerbils = new Gerbil[] {new Gerbil(1), new Gerbil(2), new Gerbil(3), new Gerbil(4), new Gerbil(5)};
    }

    public class NonCollectionSequence extends GerbilSequence {
        public Iterator<Gerbil> iterator() {
            return new Iterator<Gerbil>() {

                private int index = 0;

                @Override
                public boolean hasNext() {
                    return index < gerbils.length;
                }

                @Override
                public Gerbil next() {
                    return gerbils[index++];
                }

            };
        }
    }

    @Test
    public void test4() {
        display(new NonCollectionSequence().iterator());
    }

    @Test
    public void test6() {
        Gerbil[] gerbils = new Gerbil[] {new Gerbil(1), new Gerbil(2), new Gerbil(3), new Gerbil(4), new Gerbil(5)};
        for(Gerbil gerbil : gerbils) {
            System.out.println(gerbil);
        }
        System.out.println();

        // Collection能使用foreach语法,是因为collection继承了Iterable接口, Iterable接口有一个iterator方法,生成一个迭代器
        for(Gerbil gerbil : new IterableClass()) {
            System.out.println(gerbil);
        }
        System.out.println();

        for(Map.Entry<String, String> entry : System.getenv().entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }

    // 实现Iterable接口让其具有foreach功能
    public class IterableClass implements Iterable<Gerbil> {

        protected Gerbil[] gerbils = new Gerbil[] {new Gerbil(1), new Gerbil(2), new Gerbil(3), new Gerbil(4), new Gerbil(5)};

        @Override
        public Iterator<Gerbil> iterator() {
            return new Iterator<Gerbil>() {

                private int index = 0;

                @Override
                public boolean hasNext() {
                    return index < gerbils.length;
                }

                @Override
                public Gerbil next() {
                    return gerbils[index++];
                }

            };
        }
    }

    /**
     * 适配器反转遍历list集合
     * @param <T>
     */
    public class ReversibleArrayList<T> extends ArrayList<T> {

        public ReversibleArrayList(Collection<T> c) {super(c);}

        // 返回一个重写的Iterable,而Iterable接口又只有一个iterator()方法,返回一个Iterator迭代器
        public Iterable<T> reversed() {
            return () -> new Iterator<T>() {
                int index = size() - 1;

                @Override
                public boolean hasNext() {
                    return index > -1;
                }

                @Override
                public T next() {
                    return get(index--);
                }
            };
        }
    }

    @Test
    public void test5() {
        ReversibleArrayList<String> names = new ReversibleArrayList<>(Arrays.asList("aaa", "bbb", "ccc", "ddd"));
        for(String name : names) {
            System.out.println(name);
        }

        System.out.println();
        for(String name : names.reversed()) {
            System.out.println(name);
        }
    }

    public class MultiIterableClass extends IterableClass {

        // 反转迭代器
        public Iterable<Gerbil> reversed() {
            return () -> new Iterator<Gerbil>() {
                int index = gerbils.length - 1;

                @Override
                public boolean hasNext() {
                    return index > -1;
                }

                @Override
                public Gerbil next() {
                    return gerbils[index--];
                }
            };
        }

        // 随机洗牌 strings顺序并没有乱,因为使用ArrayList对strings进行了封装
        public Iterable<String> randomized() {
            return new Iterable<String>() {
                @Override
                public Iterator<String> iterator() {
                    List<String> strings = Arrays.asList("aa", "bb", "cc", "dd", "ee", "ff", "hh");
                    List<String> shuffled = new ArrayList<>(strings);
                    Collections.shuffle(shuffled, new Random(50));
                    System.out.println();
                    return shuffled.iterator();
                }
            };
        }
    }

    @Test
    public void test7() {
        MultiIterableClass multiIterableClass = new MultiIterableClass();
        for(Gerbil gerbil : multiIterableClass.reversed()) {
            System.out.println(gerbil);
        }
        System.out.println();

        for(String str : multiIterableClass.randomized()) {
            System.out.println(str);
        }

        System.out.println();
        List<String> list = Arrays.asList("aa", "bb", "cc", "dd", "ee", "ff", "hh");
        Collections.shuffle(list, new Random(50));

    }

    @Test
    public void test8() {}

}
