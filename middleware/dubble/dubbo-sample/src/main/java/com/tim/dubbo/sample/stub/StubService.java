package com.tim.dubbo.sample.stub;

import com.tim.dubbo.sample.Person;
import com.tim.dubbo.sample.WelcomeService;

import javax.validation.constraints.NotNull;

/**
 * Created by luolibing on 2018/9/14.
 */
public class StubService implements WelcomeService {

    private final WelcomeService welcomeService;

    public StubService(WelcomeService welcomeService) {
        this.welcomeService = welcomeService;
    }


    @Override
    public String welcome(String name) {
        System.out.println("stu invoke welcome");
        try {
            return welcomeService.welcome(name);
        } catch (Exception e) {
            e.printStackTrace();
            return "exception " + name;
        }
    }

    @Override
    public void addPerson(@NotNull(groups = Person.AddPerson.class) Person person) {

    }

    @Override
    public void updatePerson(@NotNull(groups = Person.UpdatePerson.class) Person person) {

    }
}
