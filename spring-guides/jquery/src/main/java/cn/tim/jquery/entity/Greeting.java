package cn.tim.jquery.entity;

/**
 * Created by LuoLiBing on 15/10/29.
 */
public class Greeting {

    private Integer id;
    private String name;
    private String description;

    public Greeting() {}

    public Greeting(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
