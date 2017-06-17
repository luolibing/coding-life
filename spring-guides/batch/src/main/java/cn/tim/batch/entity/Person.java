package cn.tim.batch.entity;

/**
 * Created by LuoLiBing on 15/10/8.
 */
public class Person {

    private String lastName;
    private String firstName;

    public Person() {}

    public Person(String lastName, String firstName) {
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
