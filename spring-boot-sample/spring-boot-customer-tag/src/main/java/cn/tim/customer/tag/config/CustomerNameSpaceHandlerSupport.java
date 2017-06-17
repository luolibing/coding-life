package cn.tim.customer.tag.config;

import cn.tim.customer.tag.entity.EmployeeBeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Created by luolibing on 2017/4/17.
 * 注册标签解析器
 */
public class CustomerNameSpaceHandlerSupport extends NamespaceHandlerSupport {

    // 在解析配置文件的时候，解析到对应标签时，会获取到对应的NamespaceHandlerSupport，然后调用它的init方法
    @Override
    public void init() {
        // 这个时候讲解析器加入到Parser集合当中用于专门解析employee标签， 指定当前命名空间支持的标签解析， 也可以添加其他的标签解析
        registerBeanDefinitionParser("employee", new EmployeeBeanDefinitionParser());
        // registerBeanDefinitionParser("manager", new EmployeeBeanDefinitionParser());
        // 会将标签对应的解析器都加入到一个map当中
    }
}
