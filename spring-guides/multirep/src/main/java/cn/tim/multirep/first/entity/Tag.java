package cn.tim.multirep.first.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by LuoLiBing on 15/10/31.
 */
@Entity
@Table(name = "tag", schema = "bebase")
public class Tag implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Long version;
    private String type;
    private Long lft;
    private Long rgt;

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

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getLft() {
        return lft;
    }

    public void setLft(Long lft) {
        this.lft = lft;
    }

    public Long getRgt() {
        return rgt;
    }

    public void setRgt(Long rgt) {
        this.rgt = rgt;
    }
}
