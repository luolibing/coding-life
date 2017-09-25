package cn.tim.java9.coin;

import org.junit.Test;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.util.Arrays;

/**
 * User: luolibing
 * Date: 2017/9/25 15:54
 */
public class VarHandleTest {

    // 等价于Unsafe类，提供一些细粒度的控制
    @Test
    public void isUnsafe() {
        String[] args = new String[] {"a", "b", "c", "d"};
        VarHandle varHandle = MethodHandles.arrayElementVarHandle(String[].class);
        // Unsafe中的cas原语
        boolean b = varHandle.compareAndSet(args, 1, "b", "NB");
        if(b) {
            System.out.println(Arrays.toString(args));
        }

        // Unsafe的getAndSet原语
        Object args3 = varHandle.getAndSet(args, 2, "CI");
        System.out.println(args3);
        System.out.println(Arrays.toString(args));
    }
}
