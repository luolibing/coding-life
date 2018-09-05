package com.tim.dubbo.sample;

import javax.validation.Valid;

/**
 * Created by luolibing on 2018/8/30.
 */
public interface WelcomeService {

    String welcome(String name);

    void addPerson(@Valid Person person);

    void updatePerson(@Valid Person person);
}
