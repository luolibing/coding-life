package cn.tim.springsrc.ioc;

import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

/**
 * Created by luolibing on 2017/4/12.
 */
public class ConstructorSample {

    public ConstructorSample(String name, String welcome) {
        System.out.println(name + ", welcome, " + welcome);
    }

    public static void main(String[] args) {
        XmlBeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource("sample1/mypojo5.xml"));
        for(int i = 0; i < 10; i++) {
            ConstructorSample constructorSample = beanFactory.getBean(ConstructorSample.class);
        }
    }
}
