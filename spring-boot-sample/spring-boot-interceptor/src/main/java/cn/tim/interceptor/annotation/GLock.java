package cn.tim.interceptor.annotation;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.METHOD;

/**
 * User: luolibing
 * Date: 2017/8/8 9:48
 */
@Target({METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface GLock {

    /**
     * key
     * @return
     */
    String key();

    /**
     * 默认超时时间为5分钟
     * @return
     */
    long timeout() default 60 * 5L;

    /**
     * 是否可重入
     * @return
     */
    boolean reentrant() default true;
}
