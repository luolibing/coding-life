package cn.tim.thinking.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by LuoLiBing on 16/11/9.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface HelloWorld {

    long id() default 0;

    String name() default "luolibing";
}
