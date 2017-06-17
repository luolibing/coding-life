package cn.tim.java8.other;

/**
 * Created by LuoLiBing on 16/7/27.
 */
public class Math {

    @TestCase(params = 4, expected = 24)
    @TestCase(params = 0, expected = 1)
    public static int factorial(int n) {
        int fact = 1;
        for (int i = 1; i <= n; i++) {
            fact *= i;
        }
        return fact;
    }
}
