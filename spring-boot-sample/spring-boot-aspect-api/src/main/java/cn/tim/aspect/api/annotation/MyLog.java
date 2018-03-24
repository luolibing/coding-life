package cn.tim.aspect.api.annotation;

import java.lang.annotation.*;

/**
 * User: luolibing
 * Date: 2018/3/24 18:14
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyLog {

}
