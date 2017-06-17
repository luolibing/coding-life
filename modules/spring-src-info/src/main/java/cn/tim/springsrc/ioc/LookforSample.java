package cn.tim.springsrc.ioc;

import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

/**
 * Created by luolibing on 2017/4/12.
 */
public class LookforSample {

    public static void main(String[] args) {
        XmlBeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource("sample1/mypojo3.xml"));
        UserTest userTest = (UserTest) beanFactory.getBean("userTest");
        userTest.welcome();
    }
}

class User {
    public void sayHello() {
        System.out.println("i am a user");
    }
}

class Teacher extends User {
    @Override
    public void sayHello() {
        System.out.println("i am a teacher");
    }
}

class Student extends User {
    @Override
    public void sayHello() {
        System.out.println("i am a student");
    }
}

abstract class UserTest {
    public void welcome() {
        getBean().sayHello();
    }

    abstract User getBean();
}