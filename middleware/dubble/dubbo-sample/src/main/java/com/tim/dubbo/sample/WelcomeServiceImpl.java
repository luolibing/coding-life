package com.tim.dubbo.sample;

/**
 * Created by luolibing on 2018/8/30.
 */
public class WelcomeServiceImpl implements WelcomeService {

    private static final long RNADOM = System.currentTimeMillis();

    @Override
    public String welcome(String name) {
        return  RNADOM + " hello, welcome " + name;
    }

    @Override
    public void addPerson(Person person) {

    }

    @Override
    public void updatePerson(Person person) {

    }
}
