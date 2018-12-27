package cn.tim.web;

import cn.tim.web.interceptor.MethodLogInterceptor;
import cn.tim.web.interceptor.MyLog;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
public class MyConfiguration {

    @Bean
    public DefaultPointcutAdvisor easyValidAnnotationClassPointCut() {
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
        advisor.setOrder(Ordered.HIGHEST_PRECEDENCE + 500 * 3);
        AnnotationMatchingPointcut pointcut = new AnnotationMatchingPointcut(MyLog.class, true);
        MethodLogInterceptor interceptor = new MethodLogInterceptor();
        advisor.setPointcut(pointcut);
        advisor.setAdvice(interceptor);
        return advisor;
    }
}
