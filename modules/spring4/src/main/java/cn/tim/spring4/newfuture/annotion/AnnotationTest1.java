package cn.tim.spring4.newfuture.annotion;

import org.springframework.cache.annotation.Cacheable;

/**
 * Created by LuoLiBing on 16/5/17.
 * AliaFor 注解属性的别名,也可以在注解中添加注解
 */
@AnnotationTest(value = "", name = "", locations = {}, xmlFiles = @Cacheable(value = "aaa"))
public class AnnotationTest1 {
}
