package cn.tim.springboot.conditional;

import cn.tim.springboot.conditional.entity.Company;
import cn.tim.springboot.conditional.entity.Employee;
import cn.tim.springboot.conditional.entity.Person;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

/**
 * User: luolibing
 * Date: 2017/5/12 17:31
 */
@SpringBootApplication
public class ConditionalApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConditionalApplication.class, args);
    }

    @Bean
    @Primary
    // 好奇怪这个地方value指向的是class，如果不在classpath中的类早报编译错了。 官方也提示必须使用name来作为条件。
    @ConditionalOnClass(name = "cn.tim.springboot.conditional.entity.Person")
    public Person person() {
        System.out.println("ConditionalOnClass On class");
        return new Person(0L, "luolibing");
    }

    @Bean
    // 当某个类不存在时，调用.同样要是有class的话，这个地方也会矛盾
    @ConditionalOnMissingClass(value = "cn.tim.springboot.conditional.entity.Person1")
    public Person person1() {
        System.out.println("ConditionalOnMissingClass On class");
        return new Person(0L, "luolibing");
    }

    @Bean
    // 当spring上下文中存在Person类型的Bean时调用
    @ConditionalOnBean(Person.class)
    public Person person2() {
        System.out.println("ConditionalOnBean On class");
        return new Person(1L, "luolibing");
    }

    @Bean
    // 当不存在对应的Bean时，调用. 在类上使用这个，相当于每个@Bean中都添加了这几个注解
    @ConditionalOnMissingBean(value = Company.class)
    public Employee employee() {
        System.out.println("ConditionalOnMissingBean On class");
        return new Employee();
    }

    @Primary
    @Bean
    // 当conditional.enable=true 时调用, false或者空配置，默认不调用
    @ConditionalOnProperty(prefix = "conditional", name = "enable")
    public Employee employee1() {
        System.out.println("ConditionalOnProperty On class");
        return new Employee();
    }

    @Bean
    // 当某个资源存在时调用
    @ConditionalOnResource(resources = "classpath:token1.txt")
    public Employee employee2() {
        System.out.println("ConditionalOnResource On class");
        return new Employee();
    }

    @Bean
    // 当项目为web项目，拥有WebApplicationContext时调用，相反有@ConditionalOnNotWebApplication注解
    // @ConditionalOnNotWebApplication
    @ConditionalOnWebApplication
    public Company company() {
        System.out.println("ConditionalOnWebApplication On class");
        return new Company(1L, "company");
    }

    @Primary
    @Bean
    // SPEL表达式, 当cpu数超过3个时调用
    @ConditionalOnExpression("#{T(java.lang.Runtime).getRuntime().availableProcessors() > 3}")
    public Company company1() {
        System.out.println("ConditionalOnExpression On class");
        return new Company(2L, "company");
    }
}
