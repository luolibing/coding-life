package cn.tim.thinking.generic;

import org.junit.Test;

/**
 * Created by LuoLiBing on 17/1/18.
 * 元组
 */
public class TupleDemo {

    public class TwoTuple<A, B> {
        public final A first;
        public final B second;

        public TwoTuple(A first, B second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public String toString() {
            return "(" + first + ", " + second + ")";
        }
    }

    // 元组, 一个返回值包含多个元素
    @Test
    public void tupleTest() {
        TwoTuple<Integer, String> tuple = new TwoTuple<>(1, "aaa");
        System.out.println(tuple);
        System.out.println(tuple.first);
    }


    static class LinkedStack<T> {
        private static class Node<U> {
            U item;
            Node<U> next;
            Node() {
                item = null;
            }
        }
    }

    @Test
    public void testFloat() {
        System.out.println((int) (1.04f * 100));
    }
}
