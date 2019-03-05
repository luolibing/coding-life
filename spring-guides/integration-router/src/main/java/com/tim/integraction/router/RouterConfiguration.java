package com.tim.integraction.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;

/**
 * Created by luolibing on 2019/3/6.
 */
@Configuration
public class RouterConfiguration {

    @Bean(name = "routeChannel1")
    public MessageChannel messageChannel() {
        return new DirectChannel();
    }

    @Bean(name = "routeOutput")
    public MessageChannel routeOutput() {
        return new DirectChannel();
    }
}
