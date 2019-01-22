package cn.boxfish.algorithm.jiketime.link;

import java.util.Objects;

/**
 * Created by luolibing on 2018/11/25.
 */
public class LinkSample1 {

    public static class SingleLink<N> {
        static class Node<T> {
            private T data;

            private Node<T> nextNode;

            public Node(T data, Node<T> next) {
                this.data = data;
                this.nextNode = next;
            }
        }

        private Node<N> head;

        private Node<N> tail;

        private int size;

        public SingleLink() {
            head = new Node<>(null, null);
            tail = head;
            size = 0;
        }

        public void addNode(N value) {
            Node<N> newNode = new Node<>(value, null);
            tail.nextNode = newNode;
            tail = newNode;
            size ++;
        }

        public boolean isEmpty() {
            return head == tail;
        }

        public int find(N value) {
            if(isEmpty()) {
                return -1;
            }

            int index = 0;
            Node<N> nextNode = head.nextNode;
            while (nextNode != null) {
                if(Objects.equals(nextNode.data, value)) {
                    return index;
                }

                nextNode = nextNode.nextNode;
                index ++;
            }
            return -1;
        }

        public int size() {
            return size;
        }

        public N get(int index) {
            Node<N> node = getNode(index);
            if(node == null) {
                return null;
            }
            return node.data;
        }

        private Node<N> getNode(int index) {
            checkIndexRange(index);

            Node<N> nextNode = head.nextNode;
            for(int i = 0; i < index; i++) {
                nextNode = nextNode.nextNode;
            }
            return nextNode;
        }

        public void add(int index, N value) {
            checkIndexRange(index);
            index = index - 1;
            Node<N> insertNode;
            if(index < 0) {
                insertNode = head;
            } else {
                insertNode = getNode(index - 1);
            }

            if(insertNode == null) {
                insertNode = head;
            }

            insertNode.nextNode = new Node<>(value, insertNode.nextNode);
            size ++;
        }

        private void checkIndexRange(int index) {
            if(isEmpty() && index == 0) {
                return;
            }

            if(index < 0 || index >= size) {
                throw new IllegalArgumentException("error index " + index);
            }
        }

        public N remove(int index) {
            checkIndexRange(index);
            Node<N> preNode;
            if(index - 1 < 0) {
                preNode = head;
            } else {
                preNode = getNode(index - 1);
            }

            Node<N> removeNode = preNode.nextNode;
            preNode.nextNode = removeNode.nextNode;
            return removeNode.data;
        }

    }


    public static void main(String[] args) {
        SingleLink<Integer> link = new SingleLink<>();
        link.add(0, 100);
        for(int i = 0; i < 10; i++) {
            link.addNode(i);
        }

        int index = link.find(9);
        System.out.println(index);
        System.out.println(link.size);

        Integer value = link.get(9);
        System.out.println(value);
        link.add(0, 5);
        System.out.println(link);
        link.add(4, 5);

        Integer val = link.remove(5);
        System.out.println(val);
        System.out.println(link);
    }
}
