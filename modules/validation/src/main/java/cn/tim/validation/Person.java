package cn.tim.validation;

import javax.validation.constraints.NotNull;

/**
 * User: luolibing
 * Date: 2017/6/7 20:00
 */
public class Person {

    private Long id;

    @NotNull
    private String name;

    private int age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
