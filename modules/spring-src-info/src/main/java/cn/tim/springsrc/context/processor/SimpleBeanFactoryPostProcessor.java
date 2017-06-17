package cn.tim.springsrc.context.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionVisitor;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.util.StringValueResolver;

import java.util.List;

/**
 * Created by luolibing on 2017/6/2.
 */
public class SimpleBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    private List<String> obscenes;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        String[] beanNames = beanFactory.getBeanDefinitionNames();
        for(String beanName : beanNames) {
            BeanDefinition bd = beanFactory.getBeanDefinition(beanName);
            // 这个地方有点奇怪，要是能屏蔽某个属性可能还有点用
            StringValueResolver stringResolver = strVal -> {
                if(isObscene(strVal)) {
                    return "*****";
                }
                return strVal;
            };

            BeanDefinitionVisitor visitor = new BeanDefinitionVisitor(stringResolver);
            visitor.visitBeanDefinition(bd);
        }
    }

    private boolean isObscene(String val) {
        return obscenes.contains(val);
    }

    public void setObscenes(List<String> obscenes) {
        this.obscenes = obscenes;
    }
}
