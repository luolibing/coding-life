package cn.tim.springsrc.ioc.init;

import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

/**
 * Created by luolibing on 2017/5/25.
 */
public class InitialzingTest {

    public static void main(String[] args) {
        XmlBeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource("sample1/mypojo12.xml"));
        beanFactory.getBean(InitializingBeanClass.class);
    }
}
