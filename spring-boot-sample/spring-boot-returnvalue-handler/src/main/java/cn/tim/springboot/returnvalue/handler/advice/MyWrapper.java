package cn.tim.springboot.returnvalue.handler.advice;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by luolibing on 2017/7/8.
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface MyWrapper {
    String value() default "wrapper";

    String ifEmpty() default "";
}
