package cn.tim.starter;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * Created by luolibing on 2017/3/28.
 * 所有对象的实例化都会调用BeanPostProcessor接口
 */
//@Priority(Ordered.HIGHEST_PRECEDENCE + 2)
@Primary
@Component
public class Sing implements BaseInterface, BeanPostProcessor {
    @Override
    public void say() {
        System.out.println("我在唱歌");
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("实例化" + beanName + "之前" + bean);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("实例化" + beanName + "之后" + bean);
        return bean;
    }
}
