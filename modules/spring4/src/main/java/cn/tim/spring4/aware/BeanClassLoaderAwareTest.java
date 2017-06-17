package cn.tim.spring4.aware;

import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.stereotype.Component;

/**
 * Created by LuoLiBing on 16/6/17.
 */
@Component
public class BeanClassLoaderAwareTest implements BeanClassLoaderAware {

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        System.out.println(classLoader);
        try {
            Class<?> clazz = classLoader.loadClass("cn.tim.spring4.ioc722.ExampleBean");
//            ExampleBean bean = (ExampleBean) clazz.newInstance();
//            System.out.println(bean.getUltimateAnswer());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
