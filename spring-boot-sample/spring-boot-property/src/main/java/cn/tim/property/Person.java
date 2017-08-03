package cn.tim.property;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * User: luolibing
 * Date: 2017/7/31 17:51
 */
@Component
public class Person {


    public static String name;

    @Value("${person.name}")
    public void setName(String name) {
        Person.name = name;
    }

    public String getName() {
        return name + "/aaaa";
    }
}
