package com.tim.spi.impl;


import com.tim.spi.api.Connector;
import com.tim.spi.api.Driver;
import com.tim.spi.api.DriverManager;

/**
 * SPI  service provider interface
 * 是java提供一种第三方实现或者扩展的API,可以用来替换组件或者框架扩展
 *
 * 通常在api包中定义对应的API规范，不同厂商针对这个规范提供不同的实现，例如java.sql.Driver，mysql和psql分别提供了不同的实现。当引入mysql的包时自动使用mysql的实现，引入psql的包则自动使用
 *
 * 在resources下的META-INF/service创建以接口名称为名的文件，内容即为实现类。
 */
public class DataProcess {

    public static void main(String[] args) {
        Driver driver = DriverManager.getDriver();
        Connector connector = new Connector(driver);
        connector.connect();
    }
}
