package cn.tim.thinking.generic;

import org.junit.Test;

/**
 * Created by LuoLiBing on 17/1/16.
 * 多态也算是一种泛化机制
 *
 * 泛型实现了参数化类型的概念
 */
public class GenericDemo1 {

    class Automobile {}

    // 只能保存一种类型的实现
    class Holder1  {
        private Automobile a;

        public Holder1(Automobile a) {
            this.a = a;
        }

        Automobile get() {
            return a;
        }
    }

    class Holder2 {
        private Object a;
        public Holder2(Object a) {
            this.a = a;
        }

        public void set(Object a) {
            this.a = a;
        }

        public Object get() {
            return a;
        }
    }


    class Holder3<T> {
        private T a;
        public Holder3(T a) {
            this.a = a;
        }

        public void set(T a) {
            this.a = a;
        }

        public T get() {
            return a;
        }
    }

    @Test
    public void holder2() {
        // 同一个Holder存了三种类型的元素
        Holder2 h2 = new Holder2(new Automobile());
        Automobile a = (Automobile) h2.get();
        h2.set("Not an Automibile");
        String s = (String) h2.get();
        h2.set(1);
        Integer x = (Integer) h2.get();

        // 使用泛型, 告诉编译器想使用什么类型, 然后编译器帮你处理了一切细节.
        Holder3<Automobile> h3 = new Holder3<>(new Automobile());
        h3.get();
        // h3.set(3);
    }


}
