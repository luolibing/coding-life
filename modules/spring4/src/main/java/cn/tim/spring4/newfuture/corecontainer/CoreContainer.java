package cn.tim.spring4.newfuture.corecontainer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Created by LuoLiBing on 16/5/17.
 */
@Configuration
public class CoreContainer {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
