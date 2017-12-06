package cn.tim.thinking.annotation.db;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by LuoLiBing on 17/1/13.
 */
// 约束
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Constraints {
    // 主键
    boolean primaryKey() default false;
    // 是否允许null
    boolean allowNull() default true;
    // 唯一
    boolean unique() default false;
}
