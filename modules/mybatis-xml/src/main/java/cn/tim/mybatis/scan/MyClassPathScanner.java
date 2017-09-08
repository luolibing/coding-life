package cn.tim.mybatis.scan;

import org.apache.ibatis.annotations.Mapper;
import org.junit.Test;
import org.reflections.Reflections;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * classPath扫描类
 * User: luolibing
 * Date: 2017/9/8 14:15
 */
public class MyClassPathScanner extends ClassPathBeanDefinitionScanner {

    private Class<? extends Annotation> annotationClass;

    public MyClassPathScanner(BeanDefinitionRegistry registry) {
        super(registry);
    }

    /**
     * 包扫描，扫描所有的Mapper注解的类
     */
    @Test
    public void scan() {
        Reflections reflections = new Reflections("cn.tim.mybatis");
        Set<Class<?>> mappers = reflections.getTypesAnnotatedWith(Mapper.class);
        for(Class<?> mapper : mappers) {
            System.out.println(mapper);
        }
    }

    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);
        for(BeanDefinitionHolder beanDefinition : beanDefinitions) {
            System.out.println("扫描" + beanDefinition.getBeanName());
        }
        return beanDefinitions;
    }

    public void setAnnotationClass(Class<? extends Annotation> annotationClass) {
        this.annotationClass = annotationClass;
    }

    public void registerFilters() {
        if (this.annotationClass != null) {
            addIncludeFilter(new AnnotationTypeFilter(this.annotationClass));
        }
    }
}
