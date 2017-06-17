package cn.tim.web;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * Created by luolibing on 2017/5/25.
 */
@Component
public class Hello implements InitializingBean, DisposableBean {
    @Override
    public void destroy() throws Exception {
        System.out.println("execute destroy");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("execute afterPropertiesSet");
    }

    public void say() {
        System.out.println("sayHello");
    }
}
