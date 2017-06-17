package cn.tim.springsrc.ioc.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.util.logging.Logger;

/**
 * Created by luolibing on 2017/5/11.
 */
public class MyBeanPostProcessor implements BeanPostProcessor {

    private final static Logger logger = Logger.getLogger("MyBeanPostProcessor");

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        logger.info("bean[" +beanName+"] before initialization");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        logger.info("bean[" +beanName+"] after initialization");
        return bean;
    }
}
