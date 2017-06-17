package cn.tim.customer.tag.entity;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.w3c.dom.Element;

/**
 * Created by luolibing on 2017/4/17.
 * 一个标签只映射一个BeanDefinition, SingleBeanDefinitionParser
 *
 */
public class  EmployeeBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {


    /**
     * 指定映射的Bean类型
     * @param element
     * @return
     */
    @Override
    protected Class<?> getBeanClass(Element element) {
        return Employee.class;
    }


    /**
     * 将标签中的属性或者子元素映射到类对应的属性值
     * 这个地方使用了builder模式，在解析器里面设置了属性值，然后在builder.build(）的时候创建
     * @param element
     * @param builder
     */
    @Override
    protected void doParse(Element element, BeanDefinitionBuilder builder) {
        String id = element.getAttribute("id");
        if(id != null) {
            builder.addPropertyValue("id", id);
        }

        String email = element.getAttribute("email");
        if(email != null) {
            builder.addPropertyValue("email", email);
        }

        String username = element.getAttribute("username");
        if(username != null) {
            builder.addPropertyValue("username", username);
        }
    }
}
