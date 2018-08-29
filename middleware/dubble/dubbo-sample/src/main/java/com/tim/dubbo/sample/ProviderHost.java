package com.tim.dubbo.sample;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ServiceConfig;

import java.io.IOException;

/**
 * Created by luolibing on 2018/8/30.
 */
public class ProviderHost {

    public static void main(String[] args) throws IOException {
        ServiceConfig<WelcomeService> providerConfig = new ServiceConfig<>();
        ApplicationConfig applicationConfig = new ApplicationConfig("tim-dubbo-test");
        applicationConfig.setDumpDirectory("./dump");
        applicationConfig.setOwner("tim");
        // 得开启自身的注册中心
//        applicationConfig.setRegistry(new RegistryConfig(""));
        providerConfig.setApplication(applicationConfig);
        providerConfig.setInterface(WelcomeService.class);
        providerConfig.setRef(new WelcomeServiceImpl());
        providerConfig.export();
        System.in.read();
    }
}
