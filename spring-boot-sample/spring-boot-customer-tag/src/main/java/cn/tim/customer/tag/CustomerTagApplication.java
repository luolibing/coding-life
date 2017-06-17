package cn.tim.customer.tag;

import cn.tim.customer.tag.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;

/**
 * Created by luolibing on 2017/4/17.
 * 自定义spring标签
 *
 * 要点：
 * 1 定义标签规范，即dtd或者xsd等文件，得对xml熟悉才可
 * 2 定义解析标签的类，BeanDefinitionParser，指定解析的目标Bean类型，和中间解析的细节
 * 3 指定命名空间帮助类，即spring提供的钩子接口，提供了注册标签的方法，实现这个接口，对接上即可
 * 4 编写spring.handlers指定HandlerSupport类
 * 5 编写spring.schemas指定要导入的命名空间或者说schema文件
 * 6 在编写spring.xml文件时，需要声明命名空间，标签的前缀等，例如<jsf:provider />
 */
//@SpringBootApplication
//@ImportResource("classpath:META-INF/customer.xml")
public class CustomerTagApplication implements CommandLineRunner {

    public static void main(String[] args) {
//        SpringApplication.run(CustomerTagApplication.class, args);
        XmlBeanFactory xmlBeanFactory = new XmlBeanFactory(new ClassPathResource("META-INF/customer.xml"));
        Employee bean = xmlBeanFactory.getBean(Employee.class);
        System.out.println(bean);
    }

    @Autowired
    private Employee employee;

    @Override
    public void run(String... strings) throws Exception {
        System.out.println(employee);
    }
}
