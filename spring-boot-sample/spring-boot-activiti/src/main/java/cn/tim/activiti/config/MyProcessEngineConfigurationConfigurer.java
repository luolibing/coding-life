package cn.tim.activiti.config;

import cn.tim.activiti.listener.MyActivitiEventListener;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.ProcessEngineConfigurationConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;
import javax.transaction.TransactionManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by luolibing on 2019/6/16.
 */
@Configuration
public class MyProcessEngineConfigurationConfigurer implements ProcessEngineConfigurationConfigurer {

    @Resource
    private PlatformTransactionManager platformTransactionManager;

    @Bean
    public ActivitiEventListener myActivitiEventListener() {
        return new MyActivitiEventListener();
    }

    @Override
    public void configure(SpringProcessEngineConfiguration processEngineConfiguration) {
        processEngineConfiguration.setTransactionManager(platformTransactionManager);
        ActivitiEventListener listener = myActivitiEventListener();
        List<ActivitiEventListener> listenerList = new ArrayList<>();
        listenerList.add(listener);
        processEngineConfiguration.setEventListeners(listenerList);
    }
}
