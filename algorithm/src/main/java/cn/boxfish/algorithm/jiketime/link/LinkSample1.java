package cn.boxfish.algorithm.jiketime.link;

/**
 * Created by luolibing on 2018/11/25.
 */
public class LinkSample1 {

    static class SingleLink<N> {
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

        public SingleLink() {
            head = new Node<N>(null, null);
            tail = head;
        }

        public void addNode() {

        }

        public boolean isEmpty() {
            return true;
        }
    }
}
