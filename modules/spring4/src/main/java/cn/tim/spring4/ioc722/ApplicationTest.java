package cn.tim.spring4.ioc722;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by LuoLiBing on 16/5/19.
 * IOC容器管理了多个beans,这些beans通过一些用户提供的配置的源信息创建出来,例如xml或者annotation
 * 这些bean被当成BeanDefinition对象,这个对象包含以下信息:
 * 1 类的名字,与实际定义的实现类
 * 2 类的配置属性,类在容器中表现的行为(scope, lifecycle callbacks)
 * 3 对其他类的应用或者说是依赖
 * 4 其他一些配置信息,配置线程池大小等等
 *
 * DI有两种方式
 * 1 构造函数注入
 * 2 setter方法注入 @Required注解标注方法注入值,一般是setter方法
 *
 * spring 建议使用构造函数的方式进行注入
 * 1 可以实现不变对象final对象,需要确保所有依赖不为null
 * 2 构造函数注入能保持客户端一直处于一个完成的初始化状态,但是过多的构造参数是一种不好的设计,表明对象负有太多的责任需要进行重构
 * 3 setter一般用在可选的注入,可有可无的依赖关系,否则这个类的所有地方都需要判断not-null
 *
 * 依赖
 * 1 applicationContext通过配置源数据进行创建与初始化用来描述所有的bean,源数据包括xml,javacode,annotations
 * 2 bean的依赖注入一般通过properties配置、构造函数参数,或者静态工厂方法
 * 3 所有属性或者构造函数参数都是通过值或者引用去设置
 * 4 spring能够自动转换基本类型
 *
 * 循环依赖,循环依赖使用构造方法注入的话,会抛出一个BeanCurrentlyInCreationException异常
 * 解决方案:
 * 使用setter注入的方式
 */
public class ApplicationTest {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"services.xml"});
        TestService1 testService1 = context.getBean(TestService1.class);
//        TestDao clientServiceProxy = (TestDao) context.getBean("clientServiceProxy");
//        System.out.println(clientServiceProxy);
        testService1.sayHello();

        CollectionsTest collectionsTest = context.getBean(CollectionsTest.class);
        collectionsTest.execute();

        CommandManager commandManager = context.getBean(CommandManager.class);
        commandManager.process("init");
    }
}
