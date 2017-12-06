package cn.tim.rabbitmq.samples;

/**
 * Created by LuoLiBing on 16/4/26.
 */
public class Fibonacci {

    public static int fib(int n) throws Exception {
        if (n == 0) return 0;
        if (n == 1) return 1;
        return fib(n-1) + fib(n-2);
    }
}
