package cn.tim.springsrc.ioc.aware;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;

/**
 * 自定注入想要使用的BeanFactory或者其他的aware类
 * Created by luolibing on 2017/5/25.
 */
public class AwareClass implements BeanFactoryAware, BeanNameAware, ResourceLoaderAware {


    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        Hello hello = beanFactory.getBean(Hello.class);
        hello.say();
    }

    @Override
    public void setBeanName(String name) {

    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {

    }
}
