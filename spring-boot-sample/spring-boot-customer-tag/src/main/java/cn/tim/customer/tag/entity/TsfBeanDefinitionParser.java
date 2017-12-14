package cn.tim.customer.tag.entity;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * User: luolibing
 * Date: 2017/12/14 15:00
 */
public class TsfBeanDefinitionParser implements BeanDefinitionParser {

    private Class<?> beanClass;

    public TsfBeanDefinitionParser(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        return parse(element, parserContext, this.beanClass);
    }

    private BeanDefinition parse(Element element, ParserContext parserContext, Class<?> beanClass) {
        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setBeanClass(beanClass);
        beanDefinition.setLazyInit(false);
        String id = element.getAttribute("id");
        if(StringUtils.isEmpty(id)) {
            throw new IllegalArgumentException("no id");
        }

        String host = element.getAttribute("host");
        Assert.notNull(host, "host can not be null");
        beanDefinition.getPropertyValues().addPropertyValue("host", host);
        String port = element.getAttribute("port");
        if(StringUtils.isEmpty(port)) {
            port = "8888";
        }
        beanDefinition.getPropertyValues().addPropertyValue("port", port);
        String alias = element.getAttribute("alias");
        Assert.notNull(alias, "alias can not be null");
        beanDefinition.getPropertyValues().addPropertyValue("alias", alias);

        String server = element.getAttribute("server");
        if(!StringUtils.isEmpty(server)) {
            beanDefinition.getPropertyValues().addPropertyValue("server", server);
        }

        parserContext.getRegistry().registerBeanDefinition(id, beanDefinition);
        return beanDefinition;
    }
}
