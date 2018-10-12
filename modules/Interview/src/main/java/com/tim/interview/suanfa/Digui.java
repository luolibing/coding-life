package com.tim.interview.suanfa;

/**
 * 尾递归
 */
public class Digui {

    /**
     * 递归
     * 函数调用过程
     * 调用开始，将参数，返回地址，局部变量等都压栈。然后执行，最后清理栈得到一个结果返回
     * 递归
     * A函数执行完，继续调用A本身，直到递归出口。这样会一直压栈直到递归出口，再逐个执行fun函数出栈返回结果。
     */
    static class D1 {
        int fun(int i) {
            if(i <= 1) {
                return 1;
            }

            return i + fun(i-1);
        }

        public static void main(String[] args) {
            int result = new D1().fun(5);
            System.out.println(result);
        }
    }

    /**
     * 尾递归优化,并不会减少递归的次数，减少的是保持函数栈状态的次数，也就是不必保存i + fun(i-1)的返回地址。
     * 在return fun(n)前，可以把自己占有的栈给释放了，然后进入的是fun(n-1)的执行，如此这般就行
     */
    static class D2 {
        int fun(int i, int res) {
            if(i <= 1) {
                return res;
            }
            return fun(i - 1, i + res);
        }

        public static void main(String[] args) {
            int result = new D2().fun(5, 1);
            System.out.println(result);
        }
    }
}
