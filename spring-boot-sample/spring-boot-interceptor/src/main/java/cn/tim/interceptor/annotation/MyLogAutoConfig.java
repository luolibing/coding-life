package cn.tim.interceptor.annotation;

import cn.tim.interceptor.interceptor.MethodLogInterceptor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * User: luolibing
 * Date: 2018/3/24 17:09
 */
@Configuration
public class MyLogAutoConfig {

    /**
     * TODO 不知道为啥没起作用
     * @return
     */
    @Bean
    public DefaultPointcutAdvisor myLogAnnotation() {
        DefaultPointcutAdvisor myLogAdvisor = new DefaultPointcutAdvisor();
        myLogAdvisor.setOrder(500);
        AnnotationMatchingPointcut myLogAnnotationPointCut =
                new AnnotationMatchingPointcut(MyLog.class, true);
        MethodLogInterceptor logInterceptor = new MethodLogInterceptor();
        myLogAdvisor.setPointcut(myLogAnnotationPointCut);
        myLogAdvisor.setAdvice(logInterceptor);
        return myLogAdvisor;
    }
}
