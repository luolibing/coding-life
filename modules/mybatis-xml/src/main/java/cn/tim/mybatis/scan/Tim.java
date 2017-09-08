package cn.tim.mybatis.scan;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * User: luolibing
 * Date: 2017/9/8 15:14
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({ MyImportBeanDefinitionRegistrar.class })
public @interface Tim {
}
