package cn.tim.web.cycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * User: luolibing
 * Date: 2017/11/2 14:22
 */
@Component
public class B3 implements InitializingBean, ApplicationContextAware {

    private ApplicationContext applicationContext;

    private A3 a3;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.a3 = applicationContext.getBean(A3.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
