package com.tim.dubbo.sample;

import javax.validation.constraints.NotNull;

/**
 * Created by luolibing on 2018/8/30.
 */
public interface WelcomeService {

    String welcome(String name);

    void addPerson(@NotNull(groups = Person.AddPerson.class) Person person);

    void updatePerson(@NotNull(groups = Person.UpdatePerson.class) Person person);
}
