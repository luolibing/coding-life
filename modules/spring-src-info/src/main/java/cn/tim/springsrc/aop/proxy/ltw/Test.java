package cn.tim.springsrc.aop.proxy.ltw;

import cn.tim.springsrc.aop.proxy.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by luolibing on 2017/6/12.
 */
public class Test {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("aop/ltw.xml");
        UserService userService = context.getBean(UserService.class);
        userService.add();
    }
}
