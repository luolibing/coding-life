package cn.tim.spring4.scope;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by LuoLiBing on 16/6/2.
 * 自定义Scope,实现一个scope,然后在自定义CustomScopeConfigurer中添加一个Scope
 * 最后指定目标bean为当前的scope即可,同样需要加上<aop:scope-proxy />
 */
public class ApplicationTest {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"scope.xml"});
        final Object foo = context.getBean("foo");
        context.registerShutdownHook();
    }
}
