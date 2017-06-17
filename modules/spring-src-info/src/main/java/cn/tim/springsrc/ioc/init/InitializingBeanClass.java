package cn.tim.springsrc.ioc.init;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * Created by luolibing on 2017/5/25.
 * 我们可以使用两种方式来，在创建bean时调用init方法，一种是实现initializingBean接口，另外一种是手工指定init-method方法
 * 执行顺序是先afterPropertiesSet后customerInitMethod.
 * 从方法afterPropertiesSet方法名可以看出，这个方法是在设置完所有属性之后调用的。
 *
 * 同样后置的销毁方法也有两个，一个是disposableBean接口的destroy()方法，另外一个是手工指定的destroy-method
 */
public class InitializingBeanClass implements InitializingBean, DisposableBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("execute afterPropertiesSet");
    }

    public void customerInitMethod() {
        System.out.println("execute customerInitMethod");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("execute destroyMethod");
    }

    public void customerDestroyMethod() {
        System.out.println("execute customerDestroyMethod");
    }
}
