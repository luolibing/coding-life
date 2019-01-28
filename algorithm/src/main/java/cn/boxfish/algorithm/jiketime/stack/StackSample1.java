package cn.boxfish.algorithm.jiketime.stack;

import org.junit.Test;

import java.util.Stack;

public class StackSample1 {


    /**
     * 计算器
     */
    public static class Calculator {

        public static int calc(String calculateStr) {
            Stack<String> operationStack = new Stack<>();
            Stack<Integer> numberStack = new Stack<>();
            // 如何拆出来数字和操作符
            String[] calculateArray = calculateStr.split("");
            for(String str : calculateArray) {
                if(isNumeric(str)) {
                    numberStack.push(Integer.valueOf(str));
                }
                // 运算符
                else if(isOp(str)) {
                    if(operationStack.isEmpty()) {
                        operationStack.push(str);
                    } else {
                        String op = operationStack.peek();
                        // 运算符比栈定元素还高，则压栈，继续
                        if(priority(str) > priority(op)) {
                            operationStack.push(str);
                        } else {
                            // 优先级等于或者低于当前操作符，所以需要先执行当前栈顶结果
                            int calc = calc(numberStack.pop(), numberStack.pop(), operationStack.pop());
                            numberStack.push(calc);
                            operationStack.push(str);
                        }
                    }
                }
            }

            while (!operationStack.isEmpty()) {
                int result = calc(numberStack.pop(), numberStack.pop(), operationStack.pop());
                numberStack.push(result);
            }

            return numberStack.pop();
        }

        private static boolean isOp(String op) {
            return "+-*/".contains(op);
        }

        private static boolean isNumeric(String str) {
            try
            {
                double d = Double.parseDouble(str);
            }
            catch(NumberFormatException nfe)
            {
                return false;
            }
            return true;
        }

        private static int priority(String op) {
            if("*/".contains(op)) {
                return 1;
            } else if("+-".contains(op)) {
                return 0;
            } else {
                throw new RuntimeException("error operate " + op);
            }
        }

        private static int calc(Integer num1, Integer num2, String op) {
            switch (op) {
                case "+": return num2 + num1;
                case "-": return num2 - num1;
                case "*": return num2 * num1;
                case "/": return num2 / num1;
                default:throw new RuntimeException("not support op " + op);
            }
        }
    }


    @Test
    public void calculator() {
        int result = Calculator.calc("1-5+2*3*5-1");
        System.out.println(result);
    }


    public static class ArrayStack<T> {
        private Object[] data;

        private int count;

        private int size;

        public ArrayStack(int size) {
            this.size = size;
            this.count = 0;
            this.data = new Object[size];
        }

        public void push(T t) {
            if(count == size) {
                throw new RuntimeException("stack is full");
            }

            data[++count] = t;
        }

        public T pop() {
            if(isEmpty()) {
                throw new RuntimeException("stack is empty");
            }

            T result = (T) data[count];
            data[count--] = null;
            return result;
        }

        public boolean isEmpty() {
            return count == 0;
        }
    }

    public static class LinkStack<D> {
        private Node<D> top;

        public LinkStack() {
            top = new Node<>(null);
        }

        public void push(D d) {
            Node<D> newNode = new Node<>(d);
            newNode.nextNode = top;
            top = newNode;
        }

        public D pop() {
            Node<D> popNode = top;
            D result = popNode.data;
            if(popNode.nextNode == null) {
                throw new RuntimeException("stack is Empty");
            }

            top = popNode.nextNode;
            popNode.nextNode = null;
            return result;
        }

        public boolean isEmpty() {
            return top.nextNode == null;
        }
    }

    public static class Node<N> {
        private N data;

        private Node<N> nextNode;

        public Node(N data) {
            this.data = data;
        }

        public void setNextNode(Node<N> nextNode) {
            this.nextNode = nextNode;
        }

        public Node<N> getNextNode() {
            return nextNode;
        }
    }

    @Test
    public void arrayStack() {
        ArrayStack<Integer> stack = new ArrayStack<>(10);
        System.out.println(stack.isEmpty());
        for(int i = 0; i < 5; i++) {
            stack.push(i);
        }

        System.out.println(stack.isEmpty());
        for(int i = 0; i < 5; i++) {
            Integer val = stack.pop();
            System.out.println(val);
        }
        System.out.println(stack.isEmpty());
    }

    @Test
    public void linkStack() {
        LinkStack<Integer> stack = new LinkStack<>();
        System.out.println(stack.isEmpty());
        for(int i = 0; i < 5; i++) {
            stack.push(i);
        }

        System.out.println(stack.isEmpty());
        for(int i = 0; i < 5; i++) {
            Integer val = stack.pop();
            System.out.println(val);
        }
        System.out.println(stack.isEmpty());
    }

    /**
     * 浏览器的前进后退功能
     */
    public static class Brower {
        private LinkStack<String> aHead;

        private LinkStack<String> back;

        public Brower() {
            this.aHead = new LinkStack<>();
            this.back = new LinkStack<>();
        }

        public String ahead() {
            if(back.isEmpty()) {
                return null;
            }

            String site = back.pop();
            aHead.push(site);
            return site;
        }

        public String back() {
            if(aHead.isEmpty()) {
                return null;
            }

            String site = aHead.pop();
            back.push(site);
            return site;
        }

        public void access(String site) {
            aHead.push(site);
            while (!back.isEmpty()) {
                back.pop();
            }
        }
    }

    @Test
    public void brower() {
        Brower brower = new Brower();
        brower.access("a");
        brower.access("b");
        brower.access("c");

        System.out.println(brower.ahead());
        System.out.println(brower.back());
        brower.access("d");
        System.out.println(brower.back());
    }
}
