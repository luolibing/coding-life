package cn.tim.algorithm.mi;

import org.junit.Test;

/**
 * Created by LuoLiBing on 17/2/8.
 */
public class YueSeFu {

    /**
     * 数学推导式
     * 穷举推导出一个数学公式
     * f(n)=(f(n-1)+m) % n
     */
    @Test
    public void test() {
        int last = 0;
        for(int i = 2; i <= 5; i++) {
            last = (last + 3) % i;
        }
        System.out.println(last);
    }
}
