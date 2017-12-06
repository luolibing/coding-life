package cn.tim.thinking.collection.complex17;

import org.junit.Test;

import java.util.WeakHashMap;

/**
 * Created by LuoLiBing on 17/1/7.
 * WeakHashMap用来保存WeakReference.
 */
public class WeakHashMapDemo {

    class Element {
        private String ident;

        public Element(String id) {
            this.ident = id;
        }

        @Override
        public String toString() {
            return ident;
        }

        @Override
        public int hashCode() {
            return ident.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof Element && ident.equals(((Element)obj).ident);
        }

        @Override
        protected void finalize() throws Throwable {
            System.out.println("Finalizeing " + getClass().getSimpleName() + " " + ident);
        }
    }

    class Key extends Element {

        public Key(String id) {
            super(id);
        }
    }

    class Value extends Element {

        public Value(String id) {
            super(id);
        }
    }

    @Test
    public void canonicalMapping() throws InterruptedException {
        int size = 1000;
        Key[] keys = new Key[size];
        WeakHashMap<Key, Value> map = new WeakHashMap<>();
        for(int i = 0; i < size; i++) {
            Key k = new Key(Integer.toString(i));
            Value v = new Value(Integer.toString(i));
            if(i % 3 == 0) {
                keys[i] = k; // 强引用
            }
            map.put(k, v);
        }
        System.gc();
        Thread.sleep(5000);
        // 垃圾回收, 会有大约三分之一的对象, 因为有keys数组强引用, 所以不会被回收. 而其他的键因为没有被引用, 会被直接回收掉
        System.out.println(map.size());

        for(int i = 0; i < size; i++) {
            keys[i] = null; // 解除关联
        }
        System.gc();

        Thread.sleep(5000);
        // keys数组, 解除了关联, 会导致剩下的这些弱引用对象因为垃圾回收被自动清除掉, 这个时候map为empty了.
        System.out.println(map.size());
    }
}
