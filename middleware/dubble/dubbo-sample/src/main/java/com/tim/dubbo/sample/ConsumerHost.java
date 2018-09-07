package com.tim.dubbo.sample;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.rpc.service.EchoService;

/**
 * Created by luolibing on 2018/8/30.
 */
public class ConsumerHost {

    public static void main(String[] args) {
        ReferenceConfig<WelcomeService> referenceConfig = new ReferenceConfig<>();
        // check
        referenceConfig.setCheck(false);
        referenceConfig.setApplication(new ApplicationConfig("first-dubbo-consumer"));
        RegistryConfig registryConfig = new RegistryConfig("zookeeper://127.0.0.1:2181");
        // check
        registryConfig.setCheck(false);

        referenceConfig.setRegistry(registryConfig);
        referenceConfig.setInterface(WelcomeService.class);
        referenceConfig.setTimeout(2000);

        // 调用指定分组
        referenceConfig.setGroup("group2");
//        referenceConfig.setGroup("*");
        referenceConfig.setLoadbalance("myLoadBalance");
        // 重试次数
        referenceConfig.setRetries(2);
        // 多版本
//        referenceConfig.setVersion("1.0.1");

        // 合并结果 TODO 如果开启回声测试的同时，开启合并结果，会报空指针异常
//        referenceConfig.setMerger("myMerger");

        // 缓存
        referenceConfig.setCache("lru");

        referenceConfig.setValidation("true");

        // 当check=true时，provider不可用的时候，抛出异常。check=false，不提前验证，如果是spring管理，先返回对应的引用，在恢复可用的时候再可以访问
        referenceConfig.setCheck(false);
        WelcomeService welcomeService = referenceConfig.get();
        welcomeService.addPerson(new Person());
        System.out.println(welcomeService.welcome("luolibing"));
        System.out.println(welcomeService.welcome("luolibing"));
        // TODO， 我没实现接口为什么能够进行强转！
        Object sayHello = ((EchoService) welcomeService).$echo("sayHello");
        System.out.println(sayHello);
    }
}
