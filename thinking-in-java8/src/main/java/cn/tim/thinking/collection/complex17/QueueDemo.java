package cn.tim.thinking.collection.complex17;

import cn.tim.thinking.array.TestArrayData;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by LuoLiBing on 16/12/28.
 * 队列
 * 除了并发应用, Queue在java5中仅有两个实现LinkedList和PriorityQueue, 他们的差异在于排序行为而不是性能
 */
public class QueueDemo {

    static class QueueBehavior {
        private static int count = 10;

        static <T> void test(Queue<T> queue, TestArrayData.Generator<T> gen) {
            // 插入到队尾
            for(int i = 0; i < count; i++) {
                queue.offer(gen.next());
            }

            // 从队头取出
            while (queue.peek() != null) {
                System.out.print(queue.remove() + " ");
            }
            System.out.println();
            
        }

        static class Gen implements TestArrayData.Generator<String> {

            private final static String[] s = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten"};

            private int index = 0;

            @Override
            public String next() {
                return s[index++];
            }
        }

        public static void main(String[] args) {
            test(new LinkedList<>(), new Gen());
            // 优先队列
            test(new PriorityQueue<>(), new Gen());
            // 数组阻塞队列
            test(new ArrayBlockingQueue<>(count), new Gen());
            test(new ConcurrentLinkedQueue<>(), new Gen());
            test(new LinkedBlockingQueue<>(), new Gen());
            test(new PriorityBlockingQueue<>(), new Gen());
        }
    }

    /**
     * 优先级队列中的元素, 必须实现Comparable接口, 这样才能比较优先级
     */
    static class ToDoList extends PriorityQueue<ToDoList.ToDoItem> {

        static class ToDoItem implements Comparable<ToDoItem> {
            // 主排序字段
            private char primary;

            // 次排序字段
            private int secondary;

            private String item;

            public ToDoItem(char pri, int s, String i) {
                this.primary = pri;
                this.secondary = s;
                this.item = i;
            }

            @Override
            public int compareTo(ToDoItem o) {
                // 先比较主排序字段, 如果主排序字段相同则再比较次排序字段
                if(primary == o.primary) {
                    return secondary > o.secondary ? 1 : ((secondary == o.secondary) ? 0 : -1);
                }
                return primary > o.primary ? 1 : -1;
            }

            @Override
            public String toString() {
                return "ToDoItem{" +
                        "primary=" + primary +
                        ", secondary=" + secondary +
                        ", item='" + item + '\'' +
                        '}';
            }
        }

        public boolean add(String item, char pri, int sec) {
            return super.add(new ToDoItem(pri, sec, item));
        }

        public static void main(String[] args) {
            ToDoList queue = new ToDoList();
            queue.add("Empty trash", 'a', 4);
            queue.add("Jack", 'b', 10);
            queue.add("rose", 'a', 3);
            queue.add("Bluck", 'b', 1);
            while (!queue.isEmpty()) {
                System.out.println(queue.remove());
            }
        }
    }


    static class B implements Comparable<B> {
        private static Random rand = new Random(47);

        private Integer num = rand.nextInt(100);

        @Override
        public int compareTo(B o) {
            return num.compareTo(o.num);
        }

        @Override
        public String toString() {
            return "B{" +
                    "num=" + num +
                    '}';
        }
    }

    @Test
    public void b() {
        Queue<B> queue = new PriorityQueue<>();
        for(int i = 0; i < 100; i++) {
            queue.offer(new B());
        }
        while (!queue.isEmpty()) {
            System.out.print(queue.poll() + " ");
        }
    }


    /**
     * 双向(双端)队列, 可以在任意一段添加或移除元素. 在LinkedList中包含支持双向队列得方法, LinkedList实现了Deque接口
     */
    static class DequeTest {
        static void fillTest(Deque<Integer> deque) {
            for(int i = 20; i < 27; i++) {
                deque.addFirst(i);
            }

            for(int i = 50; i < 55; i++) {
                deque.addLast(i);
            }
        }

        // 双端队列可以从队头队尾插入或者移除
        public static void main(String[] args) {
            Deque<Integer> di = new LinkedList<>();
            fillTest(di);
            System.out.println(di);
            while (di.size() != 0) {
                System.out.print(di.removeFirst() + " ");
            }
            System.out.println();
            fillTest(di);
            while (di.size() != 0) {
                System.out.print(di.removeLast() + " ");
            }
        }
    }
}
