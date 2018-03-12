package cn.tim.web.entity;

import cn.tim.web.merger.AgeMerger;
import cn.tim.web.merger.NameMerger;
import org.jdto.annotation.Source;
import org.jdto.annotation.Sources;

/**
 * Created by luolibing on 2018/3/11.
 */
public class PersonView {

    private Long id;

    @Sources(value = {@Source("firstName"), @Source("secondName")}, merger = NameMerger.class)
    private String fullName;

    @Source(value = "age", merger = AgeMerger.class)
    private String age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
