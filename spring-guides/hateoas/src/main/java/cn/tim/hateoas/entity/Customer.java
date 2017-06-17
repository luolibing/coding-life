package cn.tim.hateoas.entity;

/**
 * Created by LuoLiBing on 16/1/18.
 */
public class Customer {

    private final Long id;

    private final String firstName;

    private final String lastName;

    public Customer(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getId() {
        return this.id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }
}
