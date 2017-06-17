package cn.tim.spring4.custom;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by LuoLiBing on 16/6/7.
 * 一般的不推荐使用InitializingBean接口来实现初始化
 * 而是使用@PostConstruct或者使用initMethod或者destroyMethod方法
 */
@Configuration
public class LifecycleConfig {

    @Bean(initMethod = "init", destroyMethod = "destroy")
    public ExampleBean exampleBean() {
        return new ExampleBean();
    }
}
