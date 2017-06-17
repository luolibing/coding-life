package cn.tim.springsrc.ioc;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by luolibing on 2017/5/11.
 */
public class BeanPostProcessorTest {

    /**
     * beanPostProcessor作为全局的Bean创建AOP，进行前置处理和后置处理。
     * @param args
     */
    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("sample1/mypojo8.xml");
        MyPojo person = applicationContext.getBean(MyPojo.class);
        System.out.println(person.getName());
    }
}
