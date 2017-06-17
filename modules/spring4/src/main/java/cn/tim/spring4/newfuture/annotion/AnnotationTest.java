package cn.tim.spring4.newfuture.annotion;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.annotation.AliasFor;

/**
 * Created by LuoLiBing on 16/5/17.
 */
public @interface AnnotationTest {

    @AliasFor("locations")
    String[] value() default {};

    @AliasFor("value")
    String[] locations() default {};

    String name() default "";

    @AliasFor(annotation = Cacheable.class, attribute = "value")
    Cacheable xmlFiles();
}
