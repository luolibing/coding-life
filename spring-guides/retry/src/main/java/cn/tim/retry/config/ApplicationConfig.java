package cn.tim.retry.config;

import cn.tim.retry.service.RetryService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

/**
 * Created by LuoLiBing on 16/4/25.
 */
@Configuration
@EnableRetry
public class ApplicationConfig {

    /**
     * 需要重试的service都需要@Bean抛出
     * @return
     */
    @Bean
    public RetryService retryService() {
        return new RetryService();
    }
}
