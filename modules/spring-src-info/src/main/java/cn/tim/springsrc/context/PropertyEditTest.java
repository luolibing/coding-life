package cn.tim.springsrc.context;

import cn.tim.springsrc.context.mng.UserManage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by luolibing on 2017/5/31.
 */
public class PropertyEditTest {

    /**
     * 如果这个地方直接将date字符串注入进来会抛出异常，因为无法识别
     * no matching editors or conversion strategy found
     *
     * 解决方法一：
     * 自定义PropertyEditorSupport解析支持，然后将其注册到CustomEditorConfigurer上，key为Date类型，以后只要遇到需要注入Date的属性，都会使用这个editor来辅助
     *
     * 解决方法二：
     * 使用Spring自带的CustomDateEditor来解决，在CustomEditorConfigurer中有一个propertyEditorRegistrars属性，配置这个也可以解决这个问题。
     *
     * @param args
     */
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("context/mypojo.xml");
        UserManage bean = context.getBean(UserManage.class);
        System.out.println(bean.getDateValue());
    }
}
