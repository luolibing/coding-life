package cn.tim.springboot.aspect;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * User: luolibing
 * Date: 2017/6/1 14:25
 */
@Retention(RUNTIME)
@Target({METHOD, TYPE})
public @interface ThrowableReturn {

    Class<?> returnClazz() default Void.class;
}
