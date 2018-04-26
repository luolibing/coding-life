package cn.tim.thinking.enums;

import org.junit.Test;

import java.util.EnumSet;

/**
 * Created by LuoLiBing on 16/11/4.
 */
public class EnumMapDemo {

    enum Number {

        ONE(1), TWO(2), THREE(3);

        private int code;

        Number(int code) {
            this.code = code;
        }
    }

    @Test
    public void number() {
        EnumSet<Number> numbers = EnumSet.allOf(Number.class);
        for(Number number : numbers) {
            System.out.println(number);
        }
    }
}
