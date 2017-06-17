package cn.tim.validate.entity;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

/**
 * Created by LuoLiBing on 15/10/8.
 */
public class Person {

    @Size(min = 2, max = 30)
    private String name;

    @Min(18)
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
