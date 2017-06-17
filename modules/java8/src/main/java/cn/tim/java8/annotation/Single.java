package cn.tim.java8.annotation;

import java.lang.annotation.*;

/**
 * User: luolibing
 * Date: 2017/5/15 9:45
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Repeatable(value = Singles.class)
public @interface Single {
    String value();
}
