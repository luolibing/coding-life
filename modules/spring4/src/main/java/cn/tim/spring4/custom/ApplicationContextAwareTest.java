package cn.tim.spring4.custom;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by LuoLiBing on 16/6/17.
 */
@Component
public class ApplicationContextAwareTest implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
//        Map<String, Object> beans = this.applicationContext.getBeansWithAnnotation(Component.class);
//        for(Map.Entry<String, Object> entry : beans.entrySet()) {
//            System.out.println(entry.getKey() + ":" + entry.getValue());
//        }
    }
}
