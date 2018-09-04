package com.tim.dubbo.sample;

/**
 * Created by luolibing on 2018/9/4.
 */
public class HelloServiceImpl implements WelcomeService {
    @Override
    public String welcome(String name) {
        return "hello " + name;
    }
}
