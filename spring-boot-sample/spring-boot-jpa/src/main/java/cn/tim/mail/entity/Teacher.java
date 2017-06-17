package cn.tim.mail.entity;

import javax.persistence.*;

/**
 * Created by LuoLiBing on 16/3/17.
 */
@Entity
@Table
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

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
}
