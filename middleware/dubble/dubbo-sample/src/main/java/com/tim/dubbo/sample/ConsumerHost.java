package com.tim.dubbo.sample;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;

/**
 * Created by luolibing on 2018/8/30.
 */
public class ConsumerHost {

    public static void main(String[] args) {
        ReferenceConfig<WelcomeService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setApplication(new ApplicationConfig("first-dubbo-consumer"));
        referenceConfig.setRegistry(new RegistryConfig("zookeeper://127.0.0.1:2181"));
        referenceConfig.setInterface(WelcomeService.class);
        referenceConfig.setTimeout(2000);
        WelcomeService welcomeService = referenceConfig.get();
        System.out.println(welcomeService.welcome("luolibing"));
    }
}
