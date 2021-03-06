package com.tim.dubbo.sample;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by luolibing on 2018/9/6.
 */
public class Person implements Serializable {

    @NotNull(groups = UpdatePerson.class, message = "更新id不能为空")
    private Long id;

    @NotNull(groups = AddPerson.class, message = "添加时，name不能为空")
    private String name;

    @NotNull(groups = AddPerson.class, message = "添加时，age不能为空")
    private Integer age;

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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public interface AddPerson {}

    public interface UpdatePerson {}

    @AssertTrue(message = "age必须大于10", groups = {AddPerson.class})
    public boolean isAge() {
        if(this.age != null) {
            return this.age > 10;
        }
        return true;
    }
}
