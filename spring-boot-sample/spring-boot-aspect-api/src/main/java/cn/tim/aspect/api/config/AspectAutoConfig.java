package cn.tim.aspect.api.config;

import cn.tim.aspect.api.annotation.MyLog;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * User: luolibing
 * Date: 2018/3/24 18:15
 */
@Configuration
public class AspectAutoConfig {

    @Bean
    public DefaultPointcutAdvisor myLogAnnotation() {
        DefaultPointcutAdvisor myLogAdvisor = new DefaultPointcutAdvisor();
        myLogAdvisor.setOrder(Ordered.HIGHEST_PRECEDENCE + 500);
        AnnotationMatchingPointcut myLogAnnotationPointCut =
                new AnnotationMatchingPointcut(MyLog.class, true);
        MethodLogInterceptor logInterceptor = new MethodLogInterceptor();
        myLogAdvisor.setPointcut(myLogAnnotationPointCut);
        myLogAdvisor.setAdvice(logInterceptor);
        return myLogAdvisor;
    }
}
