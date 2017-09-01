package com.tim.springboot.source.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * User: luolibing
 * Date: 2017/9/1 16:09
 */
@Configuration
// 多个config作为一个整体上下文，同时对于导入的class，将其纳入spring管理，即使不在spring的扫描范围内
// C类，即使没有添加注解或者扫描到对应的包，在这个地方Import进来，同样会被纳入spring的管理
@Import({AConfig.class, C.class})
public class BConfig {

    @Bean
    public B b(A a) {
        return new B(a);
    }
}
