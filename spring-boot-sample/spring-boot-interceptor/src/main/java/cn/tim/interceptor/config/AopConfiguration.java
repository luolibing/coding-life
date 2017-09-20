package cn.tim.interceptor.config;

import cn.tim.interceptor.AopService;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * User: luolibing
 * Date: 2017/9/20 17:49
 */
@Configuration
public class AopConfiguration {

    @Bean
    public ProxyFactoryBean aopService(AopService aopService) {
        ProxyFactoryBean factoryBean = new ProxyFactoryBean();
        factoryBean.setTarget(aopService);
        factoryBean.setProxyTargetClass(true);
        factoryBean.setInterceptorNames("aopService");
        return factoryBean;
    }
}
