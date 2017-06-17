package cn.tim.springsrc.context;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by luolibing on 2017/5/26.
 */
public class ClassPathXmlApplicationContextTest {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("sample1/mypojo.xml");
    }
}
