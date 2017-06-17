package cn.tim.flyway.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by LuoLiBing on 16/1/12.
 */
@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastname) {
        this.lastName = lastname;
    }

    @Override
    public String toString() {
        return "Person [firstName=" + this.firstName + ", lastName=" + this.lastName
                + "]";
    }
}

