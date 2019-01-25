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
        Node<N> big = node.nextNode;
        Node<N> pattern = runCycle(node, big);
        return pattern != null;
    }

    /**
     * 查找链表中环的入口，在进行环探测的时候，有一个相距点，然后相距点计算有一个公式，根据这个公式能够得出
     * @param pHead
     * @param <N>
     * @return
     */
    public static <N> Node<N> getEnter(Node<N> pHead){
        Node<N> fast = pHead;
        Node<N> slow = pHead;
        while(fast != null && fast.nextNode != null){
            fast = fast.nextNode.nextNode;
            slow = slow.nextNode;
            //当快指针 与 慢指针相遇时
            if(fast == slow){
                fast = pHead;
                //再次相遇
                while(fast != slow){
                    fast = fast.nextNode;
                    slow = slow.nextNode;
                }
                return fast;
            }
        }
        return null;
    }

    private static <N> Node<N> runCycle(Node<N> small, Node<N> big) {
        if(big == null || big.nextNode == null || big.nextNode.nextNode == null) {
            return null;
        }

        while (big != null && big.nextNode != null) {
            small = small.nextNode;
            big = big.nextNode.nextNode;

            if(small == big) {
                return small;
            }
        }
        return null;
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

    /**
     * 3 合并已经排序完的两个链表，递归调用
     * @param leftNode
     * @param rightNode
     * @param <N>
     * @return
     */
    public static <N extends Comparable<N>> Node<N> mergeSortLink(Node<N> leftNode, Node<N> rightNode) {
        // 是否到末尾，leftNode为空，表明是右边的节点
        if(leftNode == null) {
            return rightNode;
        }

        if(rightNode == null) {
            return leftNode;
        }

        Node<N> headNode;
        // left <= right，如果左边的小，则将左边的标往后移
        if(leftNode.data.compareTo(rightNode.data) < 0) {
            headNode = leftNode;
            headNode.nextNode = mergeSortLink(leftNode.nextNode, rightNode);
        } else {
            headNode = rightNode;
            headNode.nextNode = mergeSortLink(leftNode, rightNode.nextNode);
        }

        return headNode;
    }

    /**
     * 有3种解法
     * 一 是直接遍历一次，计算出有多少个元素，然后再次遍历总数-倒数次，这个节点就是要删除的节点，这个要遍历size + index 次
     * 二 倒数index，遍历的时候，每走1步-1，直到末尾。遍历完之后，如果结果=0，表明表头为倒数index个，
     *    如果结果>0表明，没有倒数index这个数，小于0的话，则再次遍历，每走1步+1，直到结果等于index，这个节点就是要删除的节点。除最好结果需要遍历size次，其他都需要size + index
     * 三 用2个指针，一个先走index次，如果正好走到末尾，则表头为倒数index.如果index没走完就到末尾，表明没有倒数index，如果没到末尾，第二个指针再开始走，当第一个到末尾时，则第二个节点的位置为要删除的节点
     *    总次数最多只需要size次
     * @param node
     * @param index
     * @param <N>
     */
    public static <N> Node<N> deleteReverseObj(Node<N> node, int index) {
        if(node == null || index < 0) {
            return null;
        }

        Node<N> fast = node;
        Node<N> slow = node;
        int size = 1;
        // 走index步
        while (fast != null && size < index) {
            fast = fast.nextNode;
            size++;
        }

        // index还没走完就到头了，表明倒数index不存在
        if(fast == null) {
            return null;
        }

        Node<N> preNode = null;
        // 再走完
        while (fast.nextNode != null) {
            fast = fast.nextNode;
            preNode = slow;
            slow = slow.nextNode;
        }

        // 头节点为倒数index个节点
        if(preNode == null) {
            return node.nextNode;
        }

        preNode.nextNode = slow.nextNode;
        return node;
    }

    /**
     * 求链表的中间结点
     * @param list
     * @param <N>
     * @return
     */
    public static <N> Node<N> getMidNode(Node<N> list) {
        if(list == null) {
            return null;
        }

        // 2个指针，一个一次走1步，一个一次走2步
        Node<N> fast = list;
        Node<N> slow = list;
        while (fast.nextNode != null && fast.nextNode.nextNode != null) {
            slow = slow.nextNode;
            fast = fast.nextNode.nextNode;
        }
        return slow;
    }


    public static void main(String[] args) {
        // 1 链表翻转
        Node<Integer> head = new Node<>(0);
        Node<Integer> node = head;
        for(int i = 0; i < 10; i++) {
            if(i % 2 == 0) {
                node.nextNode = new Node<>(i);
                node = node.nextNode;
            }
        }

        Node newHead = reverse(head);
        System.out.println(newHead);

        // 2 链表环检测
        Node<Integer> cycle = new Node<>(0);
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

        // 3 链表环
        Node<Integer> enter = getEnter(cycle);
        System.out.println(enter);


        Node<Integer> node1 = new Node<>(0);
        node = node1;
        for(int i = 0; i < 10; i++) {
            if(i % 2 == 0) {
                node.nextNode = new Node<>(i);
                node = node.nextNode;
            }
        }

        Node<Integer> node2 = new Node<>(0);
        node = node2;
        for(int i = 0; i < 10; i++) {
            if(i % 2 == 1) {
                node.nextNode = new Node<>(i);
                node = node.nextNode;
            }
        }
        // 4 合并两个有序链表
        Node<Integer> mergeNode = mergeSortLink(node1, node2);
        System.out.println(mergeNode);

        // 5 删除链表倒数N个元素
        Node<Integer> deleteNode = deleteReverseObj(node2, 20);
        System.out.println(deleteNode);
        deleteNode = deleteReverseObj(node2, 4);
        System.out.println(deleteNode);
        deleteNode = deleteReverseObj(node2, 4);
        System.out.println(deleteNode);

        // 6 获取链表中间的中点
        Node<Integer> node3 = new Node<>(0);
        node = node3;
        for(int i = 0; i < 10; i++) {
            if(i % 2 == 1) {
                node.nextNode = new Node<>(i);
                node = node.nextNode;
            }
        }
        Node<Integer> midNode = getMidNode(node3);
        System.out.println(midNode);
    }
}
