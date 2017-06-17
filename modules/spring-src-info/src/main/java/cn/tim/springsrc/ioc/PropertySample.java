package cn.tim.springsrc.ioc;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by luolibing on 2017/4/13.
 */
public class PropertySample {

    @Test
    public void property() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("sample1/mypojo6.xml");
        Person person = applicationContext.getBean(Person.class);
        System.out.println(person.getName());
    }
}
