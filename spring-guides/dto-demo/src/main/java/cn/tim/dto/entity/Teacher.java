package cn.tim.dto.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by LuoLiBing on 16/3/17.
 */
@Table
@Entity
public class Teacher extends PersistentObject {

    private String name;

    private Integer gender;

    private Date birthday;

    @Column(name = "telephone_1")
    private Long telephone1;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Long getTelephone1() {
        return telephone1;
    }

    public void setTelephone1(Long telephone1) {
        this.telephone1 = telephone1;
    }
}
