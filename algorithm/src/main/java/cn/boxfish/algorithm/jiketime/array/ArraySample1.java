package cn.boxfish.algorithm.jiketime.array;


import java.util.ArrayList;
import java.util.List;

public class ArraySample1 {

    /**
     * set remove不关注顺序的，时间复杂度为O(1)的方式
     * @param <T>
     */
    static class EfficientArray<T> {
        private int currentCount = 10;

        private int size = 0;

        Object[] arrays;

        public EfficientArray() {
            arrays = new Object[currentCount];
        }

        public boolean set(int index, T t) {
            checkRange(index);
            incrementSize();
            Object temp = arrays[index];
            arrays[index] = t;
            arrays[size ++] = temp;
            return true;
        }

        public T remove(int index) {
            checkRange(index);
            Object rtn = arrays[index];
            arrays[index] = arrays[size--];
            return (T) rtn;
        }

        public void add(T t) {
            incrementSize();
            arrays[size ++] = t;
        }

        private void checkRange(int index) {
            if(index < 0 || index >= size) {
                throw new IllegalArgumentException("out of bound");
            }
        }

        private void incrementSize() {
            if(size >= currentCount) {
                currentCount = size * 3 / 2;
                Object[] newArray = new Object[currentCount];
                System.arraycopy(arrays, 0, newArray, 0, size);
                this.arrays = newArray;
            }
        }

        public static void main(String[] args) {
            EfficientArray efficientArray = new EfficientArray();
            efficientArray.add(100);
            for(int i = 0; i < 100; i++) {
                efficientArray.set(i , i);
            }

            System.out.println(efficientArray);
        }
    }


    /**
     * 带标记清除的array
     * @param <T>
     */
    static class EfficientArray1<T> {

        class Node<T> {
            private T data;

            /**
             * 状态 0 未使用， 1 已使用， 2 已删除
             */
            private int status;

            public void setData(T data) {
                this.data = data;
                this.status = 1;
            }

            public void logicRemove() {
                this.status = 2;
            }

            public void realRemove() {
                this.data = null;
                this.status = 0;
            }
        }

        private List<Integer> freeIndex;

        private Node[] objects;

        private int size = 0;

        private int cap = DEFAULT_SIZE;

        private final static int DEFAULT_SIZE = 10;

        public EfficientArray1() {
            this.objects = new Node[cap];
            this.freeIndex = new ArrayList<>(cap);
        }

        public EfficientArray1(int size) {
            this.cap = size;
            this.objects = new Node[cap];
            this.freeIndex = new ArrayList<>(cap);
        }

        public void add(T t) {
            Node<T> node = getSetNode();
            node.setData(t);
        }

        private Node<T> getSetNode() {
            if(size > 0 && size + 1 > cap && freeIndex.size() == 0) {
                cap = cap * 3 / 2;
                Node[] newArray = new Node[cap];
                System.arraycopy(this.objects, 0, newArray, 0, this.objects.length);
                this.objects = newArray;
                Node<T> newNode = new Node<>();
                this.objects[size ++] = newNode;
                return newNode;
            } else {
                if(size < cap) {
                    Node<T> newNode = new Node<>();
                    this.objects[size ++] = newNode;
                    return newNode;
                } else {
                    Integer index = freeIndex.remove(0);
                    return this.objects[index];
                }
            }
        }

        public void remove(int index) {
            checkSizeRange(index);
            this.objects[index].realRemove();
            this.freeIndex.add(index);
        }

        private void checkSizeRange(int index) {
            if(index < 0 || index >= size) {
                throw new IllegalArgumentException("错误的索引值" + index);
            }
        }

        public static void main(String[] args) {
            EfficientArray1<String> efficientArray1 = new EfficientArray1<>(10);
            efficientArray1.add("aaaaa");
            efficientArray1.add("bbbbb");
            efficientArray1.add("ccccc");

            efficientArray1.remove(2);
            efficientArray1.add("ddddd");
            efficientArray1.add("eeeee");
            efficientArray1.add("fffff");
            efficientArray1.add("ggggg");
            efficientArray1.add("ggggg");
            efficientArray1.add("ggggg");
            efficientArray1.add("ggggg");
            efficientArray1.add("ggggg");
            efficientArray1.add("ggggg");
            efficientArray1.add("ggggg");
            efficientArray1.add("ggggg");
            efficientArray1.add("ggggg");
            efficientArray1.add("ggggg");
            efficientArray1.add("ggggg");
            efficientArray1.add("ggggg");
            System.out.println(efficientArray1);
        }
    }

}
