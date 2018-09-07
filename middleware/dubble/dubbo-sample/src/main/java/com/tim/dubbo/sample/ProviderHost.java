package com.tim.dubbo.sample;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProviderConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;

import java.io.IOException;

/**
 *
 * multicast广播注册模式，有可能出现这个错误Can't assign requested address，原因是获取到ipv6的地址，导致报错，解决方法-Djava.net.preferIPv4Stack=true
 * 有多种注册方式，我这里使用zk
 * 使用zk会默认使用curator包来进行zk的连接管理，dubbo应该有提供这样的包，我这里就直接引入这么个包。
 * 但是curator有版本兼容问题，不同的zk对应不同的curator版本，这个很蛋痛。
 * http://curator.apache.org/zk-compatibility.html
 *
 * Created by luolibing on 2018/8/30.
 */
public class ProviderHost {

    public static void main(String[] args) throws IOException {
        exportWelcomeProvider();
        exportHelloProvider();
        System.in.read();
    }

    private static void exportHelloProvider() {
        ServiceConfig<WelcomeService> serviceConfig = new ServiceConfig<>();
        serviceConfig.setApplication(new ApplicationConfig("first-dubbo-provider"));
        RegistryConfig registryConfig = new RegistryConfig("zookeeper://127.0.0.1:2181");
        registryConfig.setId("registry1");
        serviceConfig.setRegistry(registryConfig);
        serviceConfig.setInterface(WelcomeService.class);
        serviceConfig.setRef(new HelloServiceImpl());

        serviceConfig.setLoadbalance("myLoadBalance");

        serviceConfig.setGroup("group2");

//        serviceConfig.setVersion("1.0.1");
        serviceConfig.setMerger("myMerger");

        // 缓存
        serviceConfig.setCache("lru");

        // 可以设置provider的port
        ProviderConfig providerConfig = new ProviderConfig();
        // 应该还有其他更好的配置，这里过时也不标注更好的解决方案是啥。。。
        providerConfig.setPort(-1);
        serviceConfig.setProvider(providerConfig);

        serviceConfig.export();
    }

    private static void exportWelcomeProvider() {
        ServiceConfig<WelcomeService> serviceConfig = new ServiceConfig<>();
        serviceConfig.setApplication(new ApplicationConfig("first-dubbo-provider"));
        RegistryConfig registryConfig = new RegistryConfig("zookeeper://127.0.0.1:2181");
        registryConfig.setId("registry1");
        serviceConfig.setRegistry(registryConfig);
        serviceConfig.setInterface(WelcomeService.class);
        serviceConfig.setRef(new WelcomeServiceImpl());

        serviceConfig.setLoadbalance("myLoadBalance");

        serviceConfig.setGroup("group1");
        serviceConfig.setMerger("com.tim.dubbo.sample.plugin.MyMerger");

        // 可以设置provider的port
        ProviderConfig providerConfig = new ProviderConfig();
        // 应该还有其他更好的配置，这里过时也不标注更好的解决方案是啥。。。
        providerConfig.setPort(-1);
        serviceConfig.setProvider(providerConfig);

        serviceConfig.export();
    }
}
