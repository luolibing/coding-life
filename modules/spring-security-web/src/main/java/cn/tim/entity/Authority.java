package cn.tim.entity;

import javax.persistence.*;

/**
 * Created by LuoLiBing on 15/8/31.
 */
@Entity
@Table(name = "authority")
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String url;

    // 默认为读权限
    @Column(name = "authority_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private AuthorityType type = AuthorityType.READ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public AuthorityType getType() {
        return type;
    }

    public void setType(AuthorityType type) {
        this.type = type;
    }
}
