package com.tim.lombok;

import lombok.val;
import org.junit.Test;

import java.util.ArrayList;

public class ValSample {

    /**
     * 等价于final val example = new ArrayList<String>();
     */
    @Test
    public void val() {
        val list = new ArrayList<String>();
        list.add("aaa");
        list.add("bbb");
        // 当使用明确的类时，直接推断为实现类，如果当遇到 flag ? new HashSet<>() : new ArrayList<>()推断的是AbstractCollect
        System.out.println(list.getClass());
    }
}
