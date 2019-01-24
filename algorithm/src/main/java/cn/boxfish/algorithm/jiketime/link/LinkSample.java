package cn.boxfish.algorithm.jiketime.link;

/**
 * 1) 单链表反转
 * 2) 链表中环的检测
 * 3) 两个有序的链表合并
 * 4) 删除链表倒数第n个结点
 * 5) 求链表的中间结点
 */
public class LinkSample {

    /**
     * 1 单链表反转
     * @param listNode
     * @return
     */
    public static Node reverse(Node listNode) {
        Node headNode = null;
        Node preNode = null;
        Node currentNode = listNode;
        while (currentNode != null) {
            Node nextNode = currentNode.nextNode;
            // 最后一个节点，为headNode
            if(nextNode == null) {
                headNode = currentNode;
            }

            // 当前节点的下一节点为前一个节点
            currentNode.nextNode = preNode;
            preNode = currentNode;
            currentNode = nextNode;
        }
        return headNode;
    }

    /**
     * 2 链表中环的检测，需要注意，不一定是大环，要是有子环，判断方法不一样
     * @param node
     * @param <N>
     * @return
     */
    public static <N> boolean cycleCheck(Node<N> node) {
        if(node == null) {
            return false;
        }

        // 分2个步子， 一个一次走1步，一个1次走2步，如果有环，在循环到若干次之后一定会重合。 如果没有环，走得快的会先到末尾
        Node<N> small = node;
        Node<N> big = node.nextNode;
        while (big != null && big.nextNode != null) {
            small = small.nextNode;
            big = big.nextNode.nextNode;

            if(small == big) {
                return true;
            }
        }
        return false;
    }

    private static class Node<N> {
        private N data;

        private Node<N> nextNode;

        public Node(N data) {
            this.data = data;
        }

        public N getData() {
            return data;
        }

        public Node<N> getNextNode() {
            return nextNode;
        }
    }

    public static void main(String[] args) {
        // 1 链表翻转
        Node<Integer> head = new Node<>(10);
        Node<Integer> node = head;
        for(int i = 0; i < 10; i++) {
            node.nextNode = new Node<>(i);
            node = node.nextNode;
        }

        Node newHead = reverse(head);
        System.out.println(newHead);

        // 2 链表环检测
        Node<Integer> cycle = new Node<>(10);
        Node<Integer> cycleNode = null;
        node = cycle;
        for(int i = 0; i < 10; i++) {
            Node<Integer> newNode = new Node<>(i);
            if(i == 5) {
                cycleNode = newNode;
            }
            node.nextNode = newNode;
            node = node.nextNode;
        }

        node.nextNode = cycleNode;
        boolean f = cycleCheck(cycle);
        System.out.println(f);
    }
}
