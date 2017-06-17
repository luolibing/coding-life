package cn.tim.springboot.returnvalue.handler.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

/**
 * User: luolibing
 * Date: 2017/5/4 11:28
 * Email: 397911353@qq.com
 */
@Target({METHOD, TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JsonpResponseBody {
}
