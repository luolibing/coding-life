package cn.tim.springsrc.aop.simple;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by luolibing on 2017/6/8.
 */
public class Test {

    /**
     * 当被代理的类有实现接口的时候会使用jdk代理，但是仅仅代理接口中有的方法
     * 当被代理的类没有实现任何接口，则会使用cglib代理， 但是因为cglib代理使用的是继承，所以final方法无法被代理
     *
     * Cglib代理性能比Jdk强?
     *
     * 如果不强制使用cglib代理的情况下
     * 当有接口实现的时候，不指定类型会使用什么代理? 如果不强制指定默认就是使用的JDK代理，在没有接口的时候会使用cglib代理
     * @param args
     */
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("aop/aop.xml");
//        TestBean bean = context.getBean(TestBean.class);
//        bean.test();

        AService bean1 = context.getBean(AService.class);
        bean1.a();
    }
}
