package cn.tim.springsrc.ioc.aware;

import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

/**
 * Created by luolibing on 2017/5/25.
 */
public class Test {

    public static void main(String[] args) {
        XmlBeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource("sample1/mypojo11.xml"));
        AwareClass awareClass = beanFactory.getBean(AwareClass.class);
    }
}
