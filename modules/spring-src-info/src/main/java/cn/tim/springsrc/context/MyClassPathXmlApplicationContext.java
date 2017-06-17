package cn.tim.springsrc.context;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by luolibing on 2017/5/26.
 */
public class MyClassPathXmlApplicationContext extends ClassPathXmlApplicationContext {

    public MyClassPathXmlApplicationContext(String... configLocation) throws BeansException {
        super(configLocation);
    }

    @Override
    protected void initPropertySources() {
        // 判断是否存在环境变量PATH
        getEnvironment().setRequiredProperties("PATH");
    }

    @Override
    protected void customizeBeanFactory(DefaultListableBeanFactory beanFactory) {
        super.setAllowBeanDefinitionOverriding(false);
        super.setAllowCircularReferences(false);
        super.customizeBeanFactory(beanFactory);
    }

    public static void main(String[] args) {
        ApplicationContext context = new MyClassPathXmlApplicationContext("sample1/mypojo2.xml");
    }
}
