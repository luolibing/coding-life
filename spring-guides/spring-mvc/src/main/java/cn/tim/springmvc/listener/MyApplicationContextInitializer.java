package cn.tim.springmvc.listener;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.web.context.support.XmlWebApplicationContext;

/**
 * 提供可扩展的初始化通道
 * Created by luolibing on 2017/6/23.
 */
public class MyApplicationContextInitializer implements ApplicationContextInitializer<XmlWebApplicationContext> {
    @Override
    public void initialize(XmlWebApplicationContext applicationContext) {
        System.out.println("初始化applicationContext");
    }
}
