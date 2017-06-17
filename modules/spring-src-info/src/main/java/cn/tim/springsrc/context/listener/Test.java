package cn.tim.springsrc.context.listener;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by luolibing on 2017/6/6.
 */
public class Test {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("listener/listener.xml");
        applicationContext.publishEvent(new TestEvent("hello啊"));
    }
}
