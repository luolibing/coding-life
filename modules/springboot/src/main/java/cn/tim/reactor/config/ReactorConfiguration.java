package cn.tim.reactor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import reactor.Environment;
import reactor.bus.EventBus;
import reactor.spring.context.config.EnableReactor;

/**
 * Created by LuoLiBing on 15/8/10.
 */
@Configuration
@ComponentScan
@EnableReactor
public class ReactorConfiguration {

    @Bean
    public EventBus eventBus() {
        return EventBus.config()
                .env(Environment.get())
                .dispatcher(Environment.SHARED)
                .get();
    }

}
