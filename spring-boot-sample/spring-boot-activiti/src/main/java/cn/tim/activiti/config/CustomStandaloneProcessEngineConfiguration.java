//package cn.tim.activiti.config;
//
//import cn.tim.activiti.listener.MyActivitiEventListener;
//import cn.tim.activiti.listener.MyActivityInstanceEndHandler;
//import cn.tim.activiti.listener.MyActivityInstanceStartHandler;
//import cn.tim.activiti.listener.MyUserTaskIdHandler;
//import org.activiti.engine.delegate.event.ActivitiEventListener;
//import org.activiti.engine.impl.history.handler.ActivityInstanceEndHandler;
//import org.activiti.engine.impl.history.handler.ActivityInstanceStartHandler;
//import org.activiti.engine.impl.history.handler.UserTaskIdHandler;
//import org.activiti.spring.SpringProcessEngineConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * User: luolibing
// * Date: 2017/6/14 10:07
// */
//@Configuration
//public class CustomStandaloneProcessEngineConfiguration {
//
//    @Bean
//    public ActivitiEventListener myActivitiEventListener() {
//        return new MyActivitiEventListener();
//    }
//
//    @Bean
//    public ActivityInstanceEndHandler activityInstanceEndHandler() {
//        return new MyActivityInstanceEndHandler();
//    }
//
//    @Bean
//    public ActivityInstanceStartHandler activityInstanceStartHandler() {
//        return new MyActivityInstanceStartHandler();
//    }
//
//    @Bean
//    public UserTaskIdHandler MyUserTaskIdHandler() {
//        return new MyUserTaskIdHandler();
//    }
//
//    @Bean
//    public SpringProcessEngineConfiguration springProcessEngineConfiguration() {
//        SpringProcessEngineConfiguration springProcessEngineConfiguration = new SpringProcessEngineConfiguration();
//        ActivitiEventListener listener = myActivitiEventListener();
//        List<ActivitiEventListener> listenerList = new ArrayList<>();
//        listenerList.add(listener);
//        springProcessEngineConfiguration.setEventListeners(listenerList);
//        return springProcessEngineConfiguration;
//    }
//}
