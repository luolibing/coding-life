package cn.tim.algorithm.mi;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Created by LuoLiBing on 17/2/20.
 * 单链表反转
 */
public class Sample2 {

    class Node {
        Node next;
        int data;

        public Node(Node next, int data) {
            this.next = next;
            this.data = data;
        }
    }

    Node head = new Node(null, -1);

    private Random rand = new Random(47);

    public void initNode(int n) {
        Node node = head;
        for(int i = 0; i < n; i++) {
            node.next = new Node(null, rand.nextInt(100));
            node = node.next;
        }
    }

    // 逆转
    public void revese() {
        if(head.next == null) {
            return;
        }
        Node cur = head.next.next;
        head.next.next = null;
        while (cur != null) {
            Node temp = head.next;
            head.next = cur;
            cur = cur.next;
            head.next.next = temp;
        }
    }

    public void print() {
        Node node = head;
        while (node.next != null) {
            System.out.print(node.data);
            System.out.print(", ");
            node = node.next;
        }
        System.out.println(node.data);
    }


    public static Date addMinutes(Date date, int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minutes);
        return calendar.getTime();
    }

    public static Date getAfter7Days(Date date, int i ){
        return  (i-1)==0?date : addMinutes(date,60*24*(i-1)*7);
    }


    public static void main(String[] args) {
        Date after7Days = getAfter7Days(new Date(), 3);
        System.out.println(after7Days);
    }
}
