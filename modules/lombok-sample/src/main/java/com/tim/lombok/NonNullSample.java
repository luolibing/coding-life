package com.tim.lombok;

import lombok.NonNull;
import org.junit.Test;

public class NonNullSample {

    /**
     * when null throw java.lang.NullPointerException
     * @param name
     */
    public static void check(@NonNull String name) {
        System.out.println(name);
    }

    @Test
    public void test1() {
        check(null);
    }
}
