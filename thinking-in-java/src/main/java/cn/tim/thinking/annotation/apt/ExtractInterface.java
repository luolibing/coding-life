package cn.tim.thinking.annotation.apt;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by LuoLiBing on 17/1/13.
 * 使用这个注解, 将源码中的所有public方法抽取出来, 抽取成接口
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface ExtractInterface {
    String value();
}
