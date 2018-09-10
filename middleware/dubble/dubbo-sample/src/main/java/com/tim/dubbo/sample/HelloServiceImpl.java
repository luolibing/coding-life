package com.tim.dubbo.sample;

import com.alibaba.dubbo.rpc.RpcContext;

/**
 * Created by luolibing on 2018/9/4.
 */
public class HelloServiceImpl implements WelcomeService {
    @Override
    public String welcome(String name) {
        Object tenantId = RpcContext.getContext().getAttachment("tenantId");
        System.out.println(tenantId);
        return "hello " + name;
    }

    @Override
    public void addPerson(Person person) {

    }

    @Override
    public void updatePerson(Person person) {

    }
}
