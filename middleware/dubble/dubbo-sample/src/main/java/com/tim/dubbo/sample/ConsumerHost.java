package com.tim.dubbo.sample;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.config.*;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.service.EchoService;
import com.tim.dubbo.sample.future.CallbackListener;
import com.tim.dubbo.sample.future.Futurable;
import com.tim.dubbo.sample.notify.NotifyImpl;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by luolibing on 2018/8/30.
 */
public class ConsumerHost {

    public static void main(String[] args) {
        WelcomeService welcomeService = getWelcomeConsumer().get();
        welcomeService.addPerson(new Person());

        // 隐式传递参数
        RpcContext.getContext().setAttachment("tenantId", "1");

        System.out.println(welcomeService.welcome("luolibing"));
        System.out.println(welcomeService.welcome("luolibing"));
        // 我没实现接口为什么能够进行强转, see cast package！
        Object sayHello = ((EchoService) welcomeService).$echo("sayHello");
        RpcContext.getContext().get().forEach((k, v) -> System.out.println(k + "=" + v));
        String remoteHost = RpcContext.getContext().getRemoteHost();

        URL url = RpcContext.getContext().getUrl();
        System.out.println(url);

        System.out.println(remoteHost);
        System.out.println(sayHello);

        Futurable futurable = getFuturable().get();
        // 第一次返回的是个Null
        String data = futurable.getData();
        System.out.println("data=" + data);
        // 当consumer调用成功之后，会notify同时set返回结果到RpcContext的Future上，这个时候再去获取
        Future<String> firstFuture = RpcContext.getContext().getFuture();

        // 调用完成之后，需要将future获取到，防止被覆盖。应该每次都由外部进行创建Future然后set到RpcContext当中
        data = futurable.getData();
        System.out.println("data=" + data);
        Future<String> secondFuture = RpcContext.getContext().getFuture();

        try {
            System.out.println(firstFuture.get());
            System.out.println(secondFuture.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        // TODO CallbackListener为什么需要实现序列化接口！！！
        futurable.callback("welcome", new CallbackListener() {
            @Override
            public void callback(String name) {
                System.out.println("搞什么飞机啊，" + name);
            }
        });
    }

    private static ReferenceConfig<WelcomeService> getWelcomeConsumer() {
        ReferenceConfig<WelcomeService> referenceConfig = new ReferenceConfig<>();
        // check
        referenceConfig.setCheck(false);
        referenceConfig.setApplication(new ApplicationConfig("first-dubbo-consumer"));
        RegistryConfig registryConfig = new RegistryConfig("zookeeper://127.0.0.1:2181");
        // check
        registryConfig.setCheck(false);

        referenceConfig.setRegistry(registryConfig);
        referenceConfig.setInterface(WelcomeService.class);
//        referenceConfig.setTimeout(2000);

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
        return referenceConfig;
    }


    private static ReferenceConfig<Futurable> getFuturable() {
        ReferenceConfig<Futurable> referenceConfig = new ReferenceConfig<>();
        // check
        referenceConfig.setCheck(false);
        referenceConfig.setApplication(new ApplicationConfig("first-dubbo-consumer"));
        RegistryConfig registryConfig = new RegistryConfig("zookeeper://127.0.0.1:2181");
        // check
        registryConfig.setCheck(false);

        referenceConfig.setRegistry(registryConfig);
        referenceConfig.setInterface(Futurable.class);
        referenceConfig.setTimeout(10_000);

        // 调用指定分组
        referenceConfig.setGroup("group1");
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
        referenceConfig.setCallbacks(1000);
        referenceConfig.setConnections(10);
        MethodConfig callbackMethod = new MethodConfig();
        callbackMethod.setName("callback");
        ArgumentConfig argumentConfig = new ArgumentConfig();
        argumentConfig.setType("com.tim.dubbo.sample.future.CallbackListener");
        argumentConfig.setCallback(true);
        callbackMethod.setArguments(Collections.singletonList(argumentConfig));

        MethodConfig getMethod = new MethodConfig();
        getMethod.setName("getData");
        getMethod.setAsync(true);
        // 设置事件回调，notify,有onreturn onthrow oninvoke
        NotifyImpl notify = new NotifyImpl();
        getMethod.setOnreturn(notify);
        getMethod.setOnreturnMethod("onreturn");
        getMethod.setOnthrow(notify);
        getMethod.setOnthrowMethod("onthrow");

        referenceConfig.setMethods(Arrays.asList(callbackMethod, getMethod));

        // 当check=true时，provider不可用的时候，抛出异常。check=false，不提前验证，如果是spring管理，先返回对应的引用，在恢复可用的时候再可以访问
        referenceConfig.setCheck(false);
        return referenceConfig;
    }
}
