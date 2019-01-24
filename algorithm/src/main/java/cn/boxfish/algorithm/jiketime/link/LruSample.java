package cn.boxfish.algorithm.jiketime.link;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
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

        public LRU(int cap) {
            this.cap = cap;
            head = new Node<>(null, null);
            this.size = 0;
        }

        private Node<K, V> findNode(K k) {
            Node<K, V> node = head;
            while (node.nextNode != null) {
                if(Objects.equals(node.nextNode.k, k)) {
                    return node;
                }
                node = node.nextNode;
            }

            return null;
        }

        public V find(K k) {
            Node<K, V> node = findNode(k);
            if(node == null || node.nextNode == null) {
                return null;
            }
            return node.nextNode.v;
        }

        public V put(K k, V v) {
            Node<K, V> preNode = findNode(k);
            V oldV = null;
            // 节点已经存在
            if(preNode != null) {
                oldV = preNode.nextNode.v;
                deleteNode(preNode);
                insertToHead(k, v);
            }
            // 不存在
            else {
                // 超出最高容量得先删除尾节点
                if(size >= cap) {
                    deleteTailNode();
                }
                insertToHead(k, v);
            }
            return oldV;
        }

        /**
         * 删除当前节点的下一个节点
         * @param preNode
         */
        private void deleteNode(Node<K, V> preNode) {
            Node<K, V> tempNode = preNode.nextNode;
            preNode.nextNode = tempNode.nextNode;
            size --;
        }

        /**
         * 添加节点到head
         * @param k
         * @param v
         */
        private void insertToHead(K k, V v) {
            Node<K, V> nextNode = head.nextNode;
            Node<K, V> newNode = new Node<>(k, v);
            newNode.setNextNode(nextNode);
            head.setNextNode(newNode);
            size ++;
        }

        /**
         * 删除尾节点
         */
        private void deleteTailNode() {
            Node<K, V> node = head;
            if(node.nextNode == null) {
                return;
            }

            // 获取到倒数第二个节点
            while (node.nextNode.nextNode != null) {
                node = node.nextNode;
            }

            node.setNextNode(null);
            size --;
        }

        public Map<K, V> getAll() {
            Map<K, V> map = new LinkedHashMap<>(size);
            Node<K, V> node = head;
            while (node.nextNode != null) {
                map.put(node.nextNode.k, node.nextNode.v);
                node = node.nextNode;
            }
            return map;
        }
    }

    public static void main(String[] args) {
        LRU<String, Integer> lru = new LRU<>(3);
        lru.put("a", 1);
        lru.put("b", 2);
        lru.put("c", 3);
        lru.put("a", 1);
        System.out.println(lru.getAll());
    }
}
