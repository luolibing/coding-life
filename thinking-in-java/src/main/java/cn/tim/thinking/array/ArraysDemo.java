package cn.tim.thinking.array;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.Random;

/**
 * Created by LuoLiBing on 16/12/26.
 * Arrays数组工具:
 * equals(),
 * deepEquals()多维数组,
 * fill()填充数组,
 * sort()数组排序,
 * binarySearch()用于在已经排序的数组中查找元素.
 * toString(),
 * hashCode()数组的散列码
 * asList()将其转换为List容器
 *
 * 优先选择容器而不是数组, 随着自动包装机制和泛型的出现, 数组相对于容器的有点越来越少, 功能也变得不那么灵活. 而且基本类型数组不够面向对象.
 *
 */
public class ArraysDemo {


    /**
     * System.arraycopy()复制数组比用for循环复制要快很多
     */
    @Test
    public void copyingArrays() {
        int[] i = new int[7];
        int[] j = new int[10];
        Arrays.fill(i, 47);
        Arrays.fill(j, 99);
        System.out.println("i = " + Arrays.toString(i));
        System.out.println("j = " + Arrays.toString(j));
        // 系统标准数组拷贝方法System.arraycopy(srcArr, srcIndex, targetArr, targetIndex, len)
        System.arraycopy(i, 0, j, 0, i.length);
        System.out.println("j = " + Arrays.toString(j));

        int[] k = new int[5];
        Arrays.fill(k, 103);
        // 拷贝, k.length-targetIndex, 大于了目标数组的大小, 多余的部分会被舍弃掉
        System.arraycopy(i, 0, k, 0, k.length);
        System.out.println("k = " + Arrays.toString(k));

        Arrays.fill(k, 103);
        System.arraycopy(k, 0, i, 0, k.length);
        System.out.println("i = " + Arrays.toString(i));

        // 对象类型
        Integer[] u = new Integer[10];
        Integer[] v = new Integer[5];
        Arrays.fill(u, 47);
        Arrays.fill(v, 99);
        System.out.println("u = " + Arrays.toString(u));
        System.out.println("v = " + Arrays.toString(v));
        System.arraycopy(v, 0, u, u.length/2, v.length);
        System.out.println("u = " + Arrays.toString(u));

        // 复制对象数组, 只是复制了对象的引用-而不是对象本身的拷贝. 这被称作(浅复制), System.arraycopy()不会执行自动拆装箱
        B[] w = new B[10];
        B[] x = new B[5];
        B bean = new B();
        Arrays.fill(w, bean);
        System.arraycopy(w, 0, x, 0, x.length);
        System.out.println("x = " + Arrays.toString(x));
        bean.id = 2;
        System.out.println("x = " + Arrays.toString(x));

    }

    class B {
        public int id = 0;

        @Override
        public String toString() {
            return "B{" +
                    "id=" + id +
                    '}';
        }
    }


    /**
     * 数组的比较, 使用Arrays.equals()比较两个数组, 内容是否一样.
     */
    @Test
    public void equals() {
        int[] a1 = new int[10];
        int[] a2 = new int[10];
        Arrays.fill(a1, 47);
        Arrays.fill(a2, 47);
        System.out.println(Arrays.equals(a1, a2));
        a2[3] = 11;
        System.out.println(Arrays.equals(a1, a2));

        // 对于对象类型数组, 会调用每个对象的equals方法, 进行逐个对比
        String[] s1 = new String[4];
        Arrays.fill(s1, "Hi");
        String[] s2 = {new String("Hi"), new String("Hi"), new String("Hi"), new String("Hi")};
        System.out.println(Arrays.equals(s1, s2));
    }


    class C {
        private int id;

        public C(int id) {
            this.id = id;
        }

        @Override
        public boolean equals(Object obj) {
            return obj!= null && (obj instanceof C) && ((C) obj).id == id;
        }
    }

    @Test
    public void test19() {
        C[] c1 = new C[10];
        C[] c2 = new C[10];
        C b1 = new C(1);
        C b2 = new C(1);
        Arrays.fill(c1, b1);
        Arrays.fill(c2, b2);
        // 如果C使用的是默认的Object.equals, 这个地方为false
        System.out.println(Arrays.equals(c1, c2));

        // 二维数组比较
        int[][] arr = {{1, 2}, {3, 4}};
        int[][] arr1 = {{1, 2}, {3, 4}};
        System.out.println(Arrays.deepEquals(arr, arr1));
        int[] a = {1, 2, 3, };
        System.out.println(a.length);
    }


    /**
     * 数组排序, 要实现排序必须得实现对象的比较功能. 实现比较功能有两种方式:
     * 1 实现Comparable接口, 使得类天生具有比较能力. 这个接口只有一个compareTo()方法, 如果当前对象小于参数则返回负值, 相等返回0, 否则返回正值.
     * 2 当有人给你一个并没有实现Comparable的类, 或者给你的类实现了Comparable, 但是不是你想要的方式. 要解决这个问题, 可以创建一个实现了Comparator接口的单独类
     *
     */
    static class CompType implements Comparable<CompType> {
        int i, j;
        private static int count = 1;
        public CompType(int n1, int n2) {
            this.i = n1;
            this.j = n2;
        }

        @Override
        public int compareTo(CompType o) {
            return i < o.i ? -1 : (i == o.i ? 0 : 1);
        }

        @Override
        public String toString() {
            String result = "CompType{" +
                    "i=" + i +
                    ", j=" + j +
                    '}';
            if(count ++ %3 == 0) {
                result += "\n";
            }
            return result;
        }

        private static Random rand = new Random(47);

        public static TestArrayData.Generator<CompType> generator() {
            return () -> new CompType(rand.nextInt(100), rand.nextInt(100));
        }

        public static void main(String[] args) throws InstantiationException, IllegalAccessException {
            CompType[] a = TestArrayData.Generated.array(CompType.class, generator(), 10);
            System.out.println("before sorting: ");
            System.out.println(Arrays.toString(a));
            Arrays.sort(a);
            System.out.println("after sorting: ");
            System.out.println(Arrays.toString(a));
        }
    }


    // 使用Comparator接口, Collections.reverseOrder(); 用于返回一个反序的Comparator
    @Test
    public void reverse() {
        CompType[] a = TestArrayData.Generated.array(new CompType[12], CompType.generator());
        System.out.println("before sorting");
        System.out.println(Arrays.toString(a));
        Arrays.sort(a, Collections.reverseOrder());
        // Arrays.sort(a, Comparator.reverseOrder()); java8中, Comparator接口中添加了许多默认的实现, 提供一些Comparator
        System.out.println("after sorting");
        System.out.println(Arrays.toString(a));
    }


    /**
     * Java标准库中的排序算法针对正排序的特殊类型进行了优化--针对基本类型设计的"快速排序", 以及针对对象设计的"未定归并排序".
     */
    @Test
    public void stringSorting() {
        String[] sa = {"a"," bd", "ce", "fe", "Ab", "ab", "xy", "Fe", "1"};
        System.out.println("before sort ");
        System.out.println(Arrays.toString(sa));
        // 默认的字典序, 大小会在小写的前面
        Arrays.sort(sa);
        System.out.println("after sort ");
        System.out.println(Arrays.toString(sa));
        // 忽略大小写
        Arrays.sort(sa, String.CASE_INSENSITIVE_ORDER);
        System.out.println(Arrays.toString(sa));
    }


    /**
     * 在已排序的数组中查找, 如果数组已经排好序, 就可以使用Arrays.binarySearch()执行快速二分查找.
     * 如果找到目标, Arrays.binarySearch()产生的返回值等于或大于0, 否则, 它产生负返回值, 表示若要保持数组的排序状态此目标元素所应该插入的位置.
     * 这个负值的计算方式是: -(插入点)-1. 插入点是指, 第一个大于查找对象的元素在数组中的位置, 如果数组中所有的元素都小于要查找的对象, 插入点就等于a.size()
     */
    @Test
    public void arraySearching() {
        TestArrayData.Generator<Integer> gen = new TestArrayData.RandomGenerator.Integer();
        int[] a = TestArrayData.ConvertTo.primitive(TestArrayData.Generated.array(new Integer[25], gen));
        Arrays.sort(a);
        System.out.println("Sorted array: " + Arrays.toString(a));
        while (true) {
            int r = gen.next();
            int location = Arrays.binarySearch(a, r);
            if(location > 0) {
                System.out.println("Location of " + r + " is " + location + ", a[" + location + "] = " + a[location]);
                break;
            }
        }
    }


    @Test
    public void alphabeticSearch() {
        Integer[] ch = TestArrayData.Generated.array(new Integer[30], new TestArrayData.RandomGenerator.Integer());
        Arrays.sort(ch);
        System.out.println(Arrays.toString(ch));
        // 可以在搜索的时候重写比较方法
        int index = Arrays.binarySearch(ch, ch[10] * 2, (i1, i2) -> {
            if (i1 * 2 == i2 || i2 * 2 == i1 || Objects.equals(i1, i2))
                return 0;
            return i1 > i2 ? 1 : -1;
        });
        if(index >= 0) {
            System.out.println("Index: " + index + "\n" + ch[index]);
        }
    }


    @Test
    public void test22() {
        Integer[] array = TestArrayData.Generated.array(new Integer[10], new TestArrayData.RandomGenerator.Integer());
        int[] ints = TestArrayData.ConvertTo.primitive(array);
        System.out.println(Arrays.toString(ints));
        int i = Arrays.binarySearch(ints, 200);
        System.out.println(i);
    }

    @Test
    public void test23() {
        Integer[] array = TestArrayData.Generated.array(new Integer[10], new TestArrayData.RandomGenerator.Integer());
        System.out.println(Arrays.toString(array));
        Arrays.sort(array, Collections.reverseOrder());
        System.out.println(Arrays.toString(array));
    }
}
