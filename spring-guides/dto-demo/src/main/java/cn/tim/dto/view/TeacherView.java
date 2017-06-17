package cn.tim.dto.view;

import cn.tim.dto.merger.SimpleEnumMerger;
import org.jdto.annotation.Source;
import org.jdto.annotation.Sources;
import org.jdto.mergers.AgeMerger;
import org.jdto.mergers.DateFormatMerger;
import org.jdto.mergers.StringFormatMerger;

import java.io.Serializable;

/**
 * Created by LuoLiBing on 16/3/17.
 */
public class TeacherView implements Serializable {

    private String name;

    @Source(value = "birthday", merger = DateFormatMerger.class, mergerParam = "yyyy年MM月dd日")
    private String birthday;

    @Sources(value = {@Source("name"), @Source("birthday")},
            merger = StringFormatMerger.class, mergerParam = "%s 的生日是 %s")
    private String description;

    @Source(value = "gender", merger = SimpleEnumMerger.class, mergerParam = "gender")
    private String gender;

    @Source(value = "birthday", merger = AgeMerger.class)
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
