package cn.tim.springsrc.aop.proxy.jdk;

import cn.tim.springsrc.aop.proxy.UserService;
import cn.tim.springsrc.aop.proxy.UserServiceImpl;

/**
 * Created by luolibing on 2017/6/11.
 */
public class JdkProxyTest {

    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        UserService proxy = (UserService) new MyInvocationHandler(userService).getProxy();
        proxy.add();
    }
}
