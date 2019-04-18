package com.tim.asm.simple;

/**
 * Created by luolibing on 2019/4/18.
 */
public class Person {

    private Long id;

    private String name;

    public Person() {
    }

    private Person(Long id, String name) {
        this.id = id;
        this.name = name;
    }

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

    public static Person build() {
        return new Person();
    }

    public void sayGoodBye() {
        System.out.println("good bye");
    }
}
