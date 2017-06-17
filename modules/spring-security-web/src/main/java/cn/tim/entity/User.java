package cn.tim.entity;

import com.google.common.collect.Lists;

import javax.persistence.*;
import java.util.*;

/**
 * Created by LuoLiBing on 15/8/29.
 */
@Table(name = "user")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;

    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    @Transient
    private List<Authority> authorities;

    @Transient
    private List<String> allAuthorities;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String[] getRoleNames() {
        String [] roleNames = null;
        if(roles != null) {
            roleNames = new String[roles.size()];
            for(int i=0; i<roleNames.length; i++) {
                roleNames[i] = roles.get(i).getName();
            }
        }
        return roleNames;
    }

    public List<Authority> getAuthorities() {
        return authorities;
    }


    public List<String> getAllAuthorities() {

        if(allAuthorities == null) {
            List<String> _allAuthorities = Lists.newArrayList();
            // 角色权限
            if (roles != null) {
                for (Role role : roles) {
                    _allAuthorities.add("ROLE_" + role.getName());
                }
            }

            // 获取角色所拥有的权限去重
            initRoleAuthority();
            // 操作权限
            if (authorities != null) {
                for (Authority authority : authorities) {
                    _allAuthorities.add("AUTH_" + authority.getUrl());
                }
            }
            this.allAuthorities = _allAuthorities;
        }
        return allAuthorities;
    }

    public void initRoleAuthority() {
        Set<Authority> results = new HashSet<>();
        for(int i=0; roles != null && i<roles.size(); i++) {
            Role role = roles.get(i);
            if(role.getAuthorties() != null) {
                results.addAll(role.getAuthorties());
            }
        }
        authorities = Lists.newArrayList(results);

    }
}
