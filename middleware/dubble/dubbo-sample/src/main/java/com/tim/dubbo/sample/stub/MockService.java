package com.tim.dubbo.sample.stub;

import com.tim.dubbo.sample.Person;
import com.tim.dubbo.sample.WelcomeService;

import javax.validation.constraints.NotNull;

/**
 * Created by luolibing on 2018/9/14.
 */
public class MockService implements WelcomeService {
    @Override
    public String welcome(String name) {
        return "mock welcome " + name;
    }

    @Override
    public void addPerson(@NotNull(groups = Person.AddPerson.class) Person person) {

    }

    @Override
    public void updatePerson(@NotNull(groups = Person.UpdatePerson.class) Person person) {

    }
}
