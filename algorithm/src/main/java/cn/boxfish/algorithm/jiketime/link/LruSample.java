package cn.boxfish.algorithm.jiketime.link;

import java.util.Objects;

public class LruSample {

    private static class Node<K, V> {
        private K k;

        private V v;

        private Node<K, V> nextNode;

        public Node(K k, V v) {
            this.k = k;
            this.v = v;
        }

        public K getK() {
            return k;
        }

        public V getV() {
            return v;
        }

        public void setNextNode(Node<K, V> nextNode) {
            this.nextNode = nextNode;
        }
    }

    public static class LRU<K, V> {
        private Node<K, V> head;

        private int size;

        private int cap;

        public LRU(int size) {
            this.size = size;
            head = new Node<>(null, null);
            cap = 0;
        }

        private Node<K, V> findNode(K k) {
            Node<K, V> node = head;
            while (node.nextNode != null) {
                if(Objects.equals(node.nextNode.k, k)) {
                    return node;
                }
                node = node.nextNode;
            }

            return head;
        }

        public V find(K k) {
            Node<K, V> node = findNode(k);
            if(node == null || node.nextNode == null) {
                return null;
            }
            return node.nextNode.v;
        }

        public V put(K k, V v) {
            return null;
        }
    }

    public static void main(String[] args) {
        LRU<String, Integer> lru = new LRU<>(3);
        lru.put("a", 1);
        lru.put("b", 2);
        lru.put("c", 3);
        lru.put("d", 4);
        lru.put("c", 3);
        System.out.println(lru);
    }
}
