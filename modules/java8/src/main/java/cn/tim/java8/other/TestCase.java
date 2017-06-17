package cn.tim.java8.other;

import java.lang.annotation.*;

/**
 * Created by LuoLiBing on 16/7/27.
 */
@Repeatable(TestCases.class)
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface TestCase {
    int params();
    int expected();
}

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface TestCases {
    TestCase[] value();
}
