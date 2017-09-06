package com.tim.springboot.source.config;

import org.springframework.context.annotation.Bean;

/**
 * User: luolibing
 * Date: 2017/9/1 16:05
 */
public class AConfig {

    @Bean
    public A a() {
        return new A();
    }
}
