package cn.boxfish.algorithm.jiketime.array;


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

        static class Node<T> {
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

            public void realRemvoe() {
                this.data = null;
                this.status = 0;
            }
        }

        private int[] freeIndex;

        private int[] removeIndex;

    }
}
