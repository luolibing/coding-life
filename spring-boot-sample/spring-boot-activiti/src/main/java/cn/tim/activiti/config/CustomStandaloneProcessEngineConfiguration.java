package cn.tim.activiti.config;

import cn.tim.activiti.listener.MyActivitiEventListener;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * User: luolibing
 * Date: 2017/6/14 10:07
 */
@Configuration
public class CustomStandaloneProcessEngineConfiguration {

    @Bean
    public ActivitiEventListener myActivitiEventListener() {
        return new MyActivitiEventListener();
    }
}
