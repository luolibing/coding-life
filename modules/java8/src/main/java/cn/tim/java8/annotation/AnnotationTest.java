package cn.tim.java8.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Repeatable, 是提供给注解集合使用的，例如@Scheduled和@Scheduleds()注解
 * User: luolibing
 * Date: 2017/5/15 9:50
 */
public class AnnotationTest {

    public static void main(String[] args) throws NoSuchMethodException {
        SingleStore store = new SingleStore();
        Class<? extends SingleStore> clazz = store.getClass();
        Method resultMethod = clazz.getMethod("result");
        Singles annotation = resultMethod.getAnnotation(Singles.class);
        System.out.println(Arrays.toString(annotation.value()));

        /**
         * 这个地方如果没有@Repeatable注解的支持，获取到的数组就是个空的
         */
        Single[] annotations = resultMethod.getAnnotationsByType(Single.class);
        System.out.println(Arrays.toString(annotations));

        Annotation[] annotations1 = resultMethod.getAnnotations();
        System.out.println(Arrays.toString(annotations1));
    }
}
