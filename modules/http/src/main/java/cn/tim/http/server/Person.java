package cn.tim.http.server;

/**
 * Created by LuoLiBing on 16/7/28.
 */
public class Person {

    private Long id;

    private String name;


    public Person() {
    }

    public Person(Long id, String name) {
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

    public static Person nullPerson() {
        return new Person(-1L, "not found!!");
    }
}
