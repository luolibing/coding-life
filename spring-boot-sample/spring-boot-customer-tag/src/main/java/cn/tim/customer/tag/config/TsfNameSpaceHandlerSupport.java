package cn.tim.customer.tag.config;

import cn.tim.customer.tag.entity.TsfBeanDefinitionParser;
import cn.tim.customer.tag.entity.TsfConsumer;
import cn.tim.customer.tag.entity.TsfProvider;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * User: luolibing
 * Date: 2017/12/14 14:59
 */
public class TsfNameSpaceHandlerSupport extends NamespaceHandlerSupport {
    @Override
    public void init() {
        registerBeanDefinitionParser("provider", new TsfBeanDefinitionParser(TsfProvider.class));
        registerBeanDefinitionParser("consumer", new TsfBeanDefinitionParser(TsfConsumer.class));
    }
}
