package cn.tim.mail.config;

import cn.tim.mail.annotation.CreateNode;
import cn.tim.mail.service.NodeMethodCallback;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * User: luolibing
 * Date: 2018/3/20 14:37
 */
@Component
public class NodeAnnotationProcessor implements BeanPostProcessor {

    private ConfigurableListableBeanFactory configurableListableBeanFactory;

    @Autowired
    public NodeAnnotationProcessor(ConfigurableListableBeanFactory configurableListableBeanFactory) {
        super();
        this.configurableListableBeanFactory = configurableListableBeanFactory;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        this.scanNodeAnnotation(bean, beanName);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // this.scanNodeAnnotation(bean, beanName);
        return bean;
    }

    protected void scanNodeAnnotation(Object bean, String beanName){
        this.configureMethodAction(bean);
    }

    private void configureMethodAction(Object bean){
        Class<?> managedBeanClass = bean.getClass();
        ReflectionUtils.MethodCallback methodCallback = new NodeMethodCallback(configurableListableBeanFactory, bean);
        ReflectionUtils.doWithMethods(managedBeanClass, methodCallback, new ReflectionUtils.MethodFilter() {
            @Override
            public boolean matches(Method method) {
                return method.getAnnotation(CreateNode.class) != null;
            }
        });
    }

//    public static void main(String[] args) {
//        ReflectionUtils.doWithMethods(OrderJpaRepository.class, new ReflectionUtils.MethodCallback() {
//            @Override
//            public void doWith(Method method) throws IllegalArgumentException, IllegalAccessException {
//                System.out.println("method name = " + method.getName());
//            }
//        });
//    }
}
