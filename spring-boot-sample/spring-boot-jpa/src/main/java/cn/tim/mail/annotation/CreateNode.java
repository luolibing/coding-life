package cn.tim.mail.annotation;

import java.lang.annotation.*;

/**
 * User: luolibing
 * Date: 2018/3/20 14:24
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface CreateNode {
    String[] values() default "";
    String tid();
}