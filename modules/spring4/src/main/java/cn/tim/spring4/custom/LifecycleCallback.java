package cn.tim.spring4.custom;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * Created by LuoLiBing on 16/6/7.
 * 通过InitializingBean接口来让容器在初始化的时候(设置完所有属性之后)调用afterPropertiesSet()方法
 * 通过DisposableBean接口来让容器在destruction销毁之后调用destroy()方法
 */
@Component
public class LifecycleCallback implements InitializingBean, DisposableBean {

    @Override
    public void destroy() throws Exception {
        System.out.println("invoke when destroy!!!");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("after Properties set call!!!");
    }
}
