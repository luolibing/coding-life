package cn.tim.thinking.collection.complex17;

import org.junit.Test;

import java.util.*;

/**
 * Created by LuoLiBing on 16/12/31.
 * 明白了如何散列之后, 编写自己的hashCode()就更有意义了.
 */
public class HashCodeMap {

    /**
     * 首先你无法控制bucket数组的下标值的产生, 这个值依赖于具体的HashMap对象的容量, 而容量的改变与容器的充满程度和负载因子有关.
     * hashCode()生成的结果, 经过处理后成为桶位的下标.
     * 设计hashCode()时最重要的因素是: 无论何时, 对同一个对象调用hashCode()都应该生成同样的值.
     * 如果在put()是添加进hashMap时产生一个HashCode(),get()的时候又产生另外一个hashCode(), 这样就无法重新取得该对象了.
     * 所以, 如果你的hashCode()方法依赖于对象中易变的数据, 用户就要小心了, 因为此数据发生变化时,hashCode()会产生一个不同的散列码.
     * 也不应该使hashCode()依赖于具有唯一性的对象信息, 尤其是使用this的值, 这只能产生很糟糕的hashCode(). 这样做无法产生一个新的键. 默认的HashCode()使用的是对象的地址.
     *
     */
    @Test
    public void stringHashCode() {
        // [0]和[1]字符串内容相同, 那么这些对象都映射到同一块内存区域. String.hashCode()是基于内容
        // hashCode()必须基于对象的内容生成散列码, 通过hashCode()和equals(), 必须能够完全确定对象的身份.
        String[] hellos = "Hello Hello".split(" ");
        System.out.println(hellos[0].hashCode());
        System.out.println(hellos[1].hashCode());
    }


    /**
     * 好的hashCode()应该产生分布均匀的散列码. 如果散列码都集中在一块, 那么HashMap或者HashSet在某些区域的负载会很重, 这样不如均匀分配的散列函数快.
     */

    static class BadHash {
        private static int counter = 0;

        private int id = counter++;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            BadHash badHash = (BadHash) o;

            return id == badHash.id;

        }

        @Override
        public int hashCode() {
            // 糟糕的hashCode, 所有元素都集中在3个bucket桶当中, 即使是切换成红黑树, 也会相当慢
            // return id % 3;
            return id;
        }
    }


    /**
     * 好的hashCode()
     * 1 给int变量result赋予某个非零值常量, 例如17
     * 2 为对象内每个有意义的域f(即每个可以做equals()操作的域)计算出一个int散列码c
     *      boolean                 c=f?0:1
     *      byte,char,short,int     c=(int)f
     *      long                    c=(int)(f^(>>>32))
     *      float                   c=Float.floatToIntBits(f)
     *      double                  long l = Double.doubleToLongBits(f); c=(int)(l^(l>>>32))
     *      object                  c=f.hashCode()
     *      数组                     对每个元素应用上述规则
     * 3 合并计算得到的散列码: result = 37 * result + c
     * 4 返回result
     * 5 检查hashCode()最后生成的结果,确保相同的对象有相同的散列码
     *
     */
    @Test
    public void badHash() {
        Map<BadHash, Integer> badHashMap = new HashMap<>(100000);
        BadHash selected = new BadHash();
        for(int i = 0; i < 100000; i++) {
            if(i == 5000) {
                badHashMap.put(selected, i);
            } else {
                badHashMap.put(new BadHash(), i);
            }
        }
        long startTime = System.currentTimeMillis();
        System.out.println(badHashMap.get(selected));
        System.out.println("time = " + (System.currentTimeMillis() - startTime));
    }


    /**
     * 好的hashCode()例子1
     */
    static class CountedString {
        private static List<String> created = new ArrayList<>();

        private String s;

        private char c;

        private int id = 0;

        public CountedString(String str, char ci) {
            this.s = str;
            this.c = ci;
            created.add(s);
            for(String s2 : created) {
                if(s2.equals(s)) {
                    id++;
                }
            }
        }

        @Override
        public String toString() {
            return "String: " + s + " id: " + id + " hashCode(): " + hashCode();
        }

        @Override
        public int hashCode() {
            // hashCode计算过程, 初始值17. 成员变量s和id都参与hashCode计算
            int result = 17;
            result = 37 * result + s.hashCode();
            result = 37 * result + id;
            result = 37 * result + c;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            // equals得判断对象成员变量s和id
            return obj instanceof CountedString &&
                    s.equals(((CountedString)obj).s) &&
                    id == ((CountedString) obj).id &&
                    c == ((CountedString) obj).c;
        }

        public static void main(String[] args) {
            Map<CountedString, Integer> map = new HashMap<>();
            CountedString[] cs = new CountedString[5];
            for(int i = 0; i < cs.length; i++) {
                cs[i] = new CountedString("hi", (char) (i + 59));
                map.put(cs[i], i);
            }
            System.out.println(map);

            for(CountedString cstring : cs) {
                System.out.println("Locking up " + cstring);
                System.out.println(map.get(cstring));
            }
        }
    }


    private static long counter = 0;

    /**
     * 好的hashCode()例子2
     *
     */
    class Individual implements Comparable<Individual> {


        private final long id = counter++;

        private String name;

        public Individual(String name) {
            this.name = name;
        }

        public Individual() {}

        public long id() {
            return id;
        }

        @Override
        public String toString() {
            return getClass().getSimpleName() +
                    (name == null ? "" : " " + name);
        }

        @Override
        public int compareTo(Individual o) {
            // 排序规则
            String first = getClass().getSimpleName();
            String argFirst = o.getClass().getSimpleName();
            int firstCompare = first.compareTo(argFirst);
            if(firstCompare != 0) {
                return firstCompare;
            }

            if(name != null && o.name != null) {
                int seconmdCompare = name.compareTo(o.name);
                if(seconmdCompare != 0) {
                    return seconmdCompare;
                }
            }
            return (o.id < id ? -1 : (o.id == id ? 0 : 1));
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof Individual
                    && id == ((Individual) obj).id;
        }

        @Override
        public int hashCode() {
            // 计算
            int result = 17;
            if(name != null) {
                result = 37 * result + name.hashCode();
            }
            result = 37 * result + (int) id;
            return result;
        }
    }


    @Test
    public void individualTest() {
        Set<Individual> pets = new TreeSet<>();
        for(int i = 0; i < 10; i++) {
            //"name" + (char)(i + 59)
            pets.add(new Individual("name" + i));
        }

        pets.forEach(System.out::println);
    }
}
