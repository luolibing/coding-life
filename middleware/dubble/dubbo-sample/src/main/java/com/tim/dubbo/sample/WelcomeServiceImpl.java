package com.tim.dubbo.sample;

/**
 * Created by luolibing on 2018/8/30.
 */
public class WelcomeServiceImpl implements WelcomeService {
    @Override
    public String welcome(String name) {
        return "hello, welcome " + name;
    }
}
