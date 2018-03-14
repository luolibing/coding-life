package cn.tim.web.service;

import cn.tim.web.entity.ObjectA;
import cn.tim.web.entity.ObjectB;
import cn.tim.web.entity.ObjectC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * User: luolibing
 * Date: 2018/3/14 12:55
 */
@Configuration
public class ViewService {

    @Bean
    @Scope("prototype")
    public ObjectA objectA() {
        System.out.println("create ObjectA");
        return new ObjectA("a" + System.currentTimeMillis());
    }

    @Bean
    @Scope("prototype")
    public ObjectB objectB() {
        System.out.println("create ObjectB");
        return new ObjectB(objectA());
    }

    @Bean
    @Scope("prototype")
    public ObjectC objectC() {
        System.out.println("create ObjectC");
        return new ObjectC(objectB());
    }
}
