package cn.tim.springsrc.context.processor;

import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

/**
 * Created by luolibing on 2017/6/1.
 */
public class MailTest {

    /**
     * spring可以使用PropertyPlaceholderConfigurer来进行占位符替换，也可以对其进行扩展，例如yaml文件
     * @param args
     */
    public static void main(String[] args) {
        XmlBeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource("context/processor.xml"));

        SimpleBeanFactoryPostProcessor postProcessor = beanFactory.getBean(SimpleBeanFactoryPostProcessor.class);
        postProcessor.postProcessBeanFactory(beanFactory);
        Mail mail = beanFactory.getBean(Mail.class);
        System.out.println(mail);
    }
}
