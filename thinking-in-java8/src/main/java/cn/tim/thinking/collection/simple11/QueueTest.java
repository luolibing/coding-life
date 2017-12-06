package cn.tim.thinking.collection.simple11;

import org.junit.Test;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by LuoLiBing on 16/9/19.
 */
public class QueueTest {

    @Test
    public void test1() throws InterruptedException {
        // Queue接口窄化了LinkedList类的方法
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>(5);
        Random random = new Random();
        for(int i = 0; i < 100; i++) {
            // 当队列满的时候,再调用add时抛出IllegalStateException异常
             queue.add(i);
            // 当队列满的时候,调用put阻塞住等待空间
            // queue.put(i);
            // 当队列满的时候,调用offer时,会返回false,会丢失消息
            queue.offer(random.nextInt(100));
        }

        // peek() element()为null时会抛出NoSuchElementException异常
        while (queue.peek() != null) {
            // remove()为null时会抛出NoSuchElementException异常 poll()
            System.out.println(queue.remove());
        }
    }

    @Test
    public void test2() {
        // 通过数组实现,优先队列
        Queue<String> queue = new PriorityQueue<>(10, String.CASE_INSENSITIVE_ORDER);
        queue.offer("aaa");
        queue.offer("zzz");
        queue.offer("xxx");
        queue.offer("bbb");
        queue.offer("eee");
        while (queue.peek() != null) {
            System.out.println(queue.remove());
        }
    }

    @Test
    public void test3() {
        List<Integer> ints = Arrays.asList(25, 22, 20, 18, 1, 2, 3 ,9, 14, 18, 21, 23 ,25);
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(ints);
        printQ(priorityQueue);

        // 倒序
        priorityQueue = new PriorityQueue<>(ints.size(), Collections.reverseOrder());
        printQ(priorityQueue);

        String fact = "EDUCATION SHOULD ESCHEW OBFUSCATION";
        List<String> strings = Arrays.asList(fact.split(""));
        PriorityQueue<String> stringPriorityQueue = new PriorityQueue<>(strings);
        printQ(stringPriorityQueue);

        stringPriorityQueue = new PriorityQueue<>(strings.size(), Collections.reverseOrder());
        stringPriorityQueue.addAll(strings);
        printQ(stringPriorityQueue);

        // character
        Set<Character> charSet = new HashSet<>();
        for(char ch : fact.toCharArray()) {
            charSet.add(ch);
        }
        PriorityQueue<Character> characterPriorityQueue = new PriorityQueue<>(charSet);
        printQ(characterPriorityQueue);
    }

    private void printQ(Queue queue) {
        while (queue.peek() != null) {
            System.out.println(queue.remove());
        }
    }

    @Test
    public void test4() {
        Random random = new Random(System.nanoTime());
        Queue<Double> priorityQueue = new PriorityQueue<>();
        for(int i = 0; i < 1000; i++) {
            priorityQueue.offer(random.nextDouble());
        }
        printQ(priorityQueue);
    }

    @Test
    public void test5() {
        Queue<PriorityObject> priorityQueue = new PriorityQueue<>();
        for(int i = 0; i < 1000; i++) {
            priorityQueue.offer(new PriorityObject());
        }
        printQ(priorityQueue);
        int i = 0;
        System.out.println(4 % 8);
        System.out.println( ++i);
    }

    // 必须得实现comparable接口才能对其进行排序与比较
    class PriorityObject implements Comparable {

        @Override
        public int compareTo(Object o) {
            return 0;
        }
    }

}
