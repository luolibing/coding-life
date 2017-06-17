package cn.tim.springsrc.ioc;

import java.util.List;
import java.util.Map;

/**
 * Created by luolibing on 2017/4/13.
 */
public class Person {

    private Long id;

    private String name;

    private String desc;

    private List<String> tags;

    private Map<String, String> references;

    private Ablity ablity;

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Map<String, String> getReferences() {
        return references;
    }

    public void setReferences(Map<String, String> references) {
        this.references = references;
    }

    public Ablity getAblity() {
        return ablity;
    }

    public void setAblity(Ablity ablity) {
        this.ablity = ablity;
    }
}
