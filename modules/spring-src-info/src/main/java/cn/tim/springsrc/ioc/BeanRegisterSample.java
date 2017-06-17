package cn.tim.springsrc.ioc;

import org.junit.Test;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

/**
 * Created by luolibing on 2017/4/9.
 */
public class BeanRegisterSample {

    @Test
    public void register() {
        XmlBeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource("sample1/mypojo2.xml"));
        //
        MyPojo myBean = (MyPojo) beanFactory.getBean("myBean");
        System.out.println(myBean);
    }
}
