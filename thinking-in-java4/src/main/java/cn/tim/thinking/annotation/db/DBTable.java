package cn.tim.thinking.annotation.db;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by LuoLiBing on 17/1/13.
 */
// 数据库表
@Target(ElementType.TYPE) // 这个自定义注解只能应用于类型(class, interface, enum)
@Retention(RetentionPolicy.RUNTIME)
public @interface DBTable {
    String name() default "";
}
