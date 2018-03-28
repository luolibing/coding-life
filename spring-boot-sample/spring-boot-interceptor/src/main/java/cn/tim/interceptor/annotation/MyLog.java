package cn.tim.interceptor.annotation;

import java.lang.annotation.*;

/**
 * User: luolibing
 * Date: 2018/3/24 17:07
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyLog {

}
