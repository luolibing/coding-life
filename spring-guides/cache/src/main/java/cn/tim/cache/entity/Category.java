package cn.tim.cache.entity;

/**
 * Created by LuoLiBing on 15/11/14.
 */
public class Category {
    String id;
    String name;

    public Category(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
