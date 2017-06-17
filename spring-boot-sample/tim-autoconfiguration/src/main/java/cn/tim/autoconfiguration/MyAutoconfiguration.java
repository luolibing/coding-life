package cn.tim.autoconfiguration;

import cn.tim.autoconfiguration.beans.AtomicCounting;
import cn.tim.autoconfiguration.beans.Counting;
import cn.tim.autoconfiguration.config.CountingProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by luolibing on 2017/4/1.
 */
@Configuration
@ConditionalOnBean(Counting.class)
@EnableConfigurationProperties(CountingProperties.class)
public class MyAutoconfiguration {

    @Bean
    @ConditionalOnBean(CountingProperties.class)
    public Counting atomicCounting(CountingProperties countingProperties) {
        return new AtomicCounting(countingProperties);
    }
}
