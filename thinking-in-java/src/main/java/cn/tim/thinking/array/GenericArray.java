package cn.tim.thinking.array;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LuoLiBing on 16/12/26.
 * 通常数组和泛型不能很好地结合. 你不能实例化具有参数化类型的数组
 */
public class GenericArray {

    @Test
    public void genericArray() {
        // Peel<Banana>[] peels = new Peel<Banana>[10];  new Peel[10]; 类型会被擦除, 你不能实例化具有参数化类型的数组
    }

    class Banana {
        private final int id;
        Banana(int id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return getClass().getSimpleName() + " " + id;
        }
    }

    class Peel<T> {
        T fruit;
        Peel(T fruit) {
            this.fruit = fruit;
        }

        void peel() {
            System.out.println("Peeling " + fruit);
        }
    }

    /**
     * 参数化数组本身的类型.
     * 参数化类
     * @param <T>
     */
    class ClassParameter<T> {
        public T[] f(T[] arg) {
            return arg;
        }
    }

    /**
     * 参数化方法
     */
    static class MethodParameter {
        public static <T> T[] f(T[] arg) {
            return arg;
        }
    }

    /**
     * 使用参数化方法而不使用参数化类的方便之处在于: 你不必为需要应用的每种不同的类型都使用一个参数去实例化这个类, 并且你可以将其定义为静态的.
     */
    @Test
    public void parameterizedArrayType() {
        Integer[] ints = {1, 2, 3, 4, 5, };
        System.out.println(ints.length);
        Double[] doubles = {1.1, 2.2, 3.3, 4.4, 5.5};
        Integer[] ints2 = new ClassParameter<Integer>().f(ints);
        Double[] doubles2 = MethodParameter.f(doubles);
    }


    @Test
    public void arrayOfGenerics() {
        List<String>[] ls;
        List[] la = new List[10];
        ls = la; // 可以直接进行转换
        ls[0] = new ArrayList<>(); // ArrayList<>(); 语法糖
        // ls[1] = new ArrayList<Integer>();

        Object[] objects = ls; // List<String>是Object的子类, 协变
        objects[1] = new ArrayList<Integer>(); // List<Integer>自然也是Object的子类

        List<Banana>[] bananas = new List[10];
        for(int i = 0; i < bananas.length; i++) {
            bananas[i] = new ArrayList<>();
        }
    }


    class ArrayOfGenericType<T> {
        T[] array;

        public ArrayOfGenericType(int size) {
            // array = new T[size]; 并不能创建泛型数组, 类型被擦除, 因而是类型未知的数组
            array = (T[]) new Object[size];
        }

//        public <U> U[] makeArray() {
//            return new U[10];
//        }
    }

    @Test
    public void objectArray() {
        Object[] objects = new Object[10];
        objects[0] = 10;
        objects[1] = "aab";
        objects[2] = new ArrayList<>();
        for(Object obj: objects) {
            System.out.println("obj = " + obj);
        }
    }

    @Test
    public void peelBanana() {
        // Peel<Banana>[] a = new Peel<Banana>[10];
        List<Peel<Banana>> list = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            list.add(new Peel<>(new Banana(i)));
        }
        for(Peel<Banana> p: list) {
            p.peel();
        }
    }
}
