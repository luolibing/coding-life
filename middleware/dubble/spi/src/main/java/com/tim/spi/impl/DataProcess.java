package com.tim.spi.impl;


import com.tim.spi.api.Connector;
import com.tim.spi.api.Driver;
import com.tim.spi.api.DriverManager;
import org.junit.Test;

/**
 * SPI  service provider interface
 * 是java提供一种第三方实现或者扩展的API,可以用来替换组件或者框架扩展
 *
 * 通常在api包中定义对应的API规范，不同厂商针对这个规范提供不同的实现，例如java.sql.Driver，mysql和psql分别提供了不同的实现。当引入mysql的包时自动使用mysql的实现，引入psql的包则自动使用
 *
 * 在resources下的META-INF/service创建以接口名称为名的文件，内容即为实现类。
 *
 * ServiceLoader的实现，本身返回的是一个Iterator迭代器，内部包含有一个Map，存储对应的接口与实例，当存在的时候直接返回，第一次获取的时候读取配置文件获取并且进行实例化，之后返回结果
 *
 */
public class DataProcess {

    public static void main(String[] args) {
        Driver driver = DriverManager.getDriver();
        Connector connector = new Connector(driver);
        connector.connect();
    }

    @Test
    public void test() {
        ClassLoader appClassLoader = Thread.currentThread().getContextClassLoader();
        System.out.println(appClassLoader);
        System.out.println(appClassLoader.getParent());
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        System.out.println(systemClassLoader);
        System.out.println(systemClassLoader.getParent());
        appClassLoader.getResource("META-INF/services/com.tim.spi.api.Driver");
        systemClassLoader.getResource("META-INF/services/com.tim.spi.api.Driver");
    }
}
