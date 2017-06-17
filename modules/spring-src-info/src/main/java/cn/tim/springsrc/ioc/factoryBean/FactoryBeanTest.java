package cn.tim.springsrc.ioc.factoryBean;

import org.junit.Test;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

/**
 * Created by luolibing on 2017/5/9.
 */
public class FactoryBeanTest {

    @Test
    public void test1() {
        /**
         * 这里实际调用的是factoryBean.getObject()，并不是直接返回factoryBean
         */
        XmlBeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource("sample1/mypojo7.xml"));
        //Car car = beanFactory.getBean("car", Car.class);

        Object bean = beanFactory.getBean("&car");
        System.out.println(bean);
        //System.out.println(car);
    }
}
