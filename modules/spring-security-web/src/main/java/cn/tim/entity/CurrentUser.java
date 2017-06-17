package cn.tim.entity;


import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;


/**
 * Created by LuoLiBing on 15/8/29.
 */
public class CurrentUser extends User {

    private cn.tim.entity.User user;

    public CurrentUser(cn.tim.entity.User user) {
        super(user.getUsername(), user.getPassword(),
                AuthorityUtils.createAuthorityList(user.getAllAuthorities().toArray(new String[]{})));
        this.user = user;
    }

    public cn.tim.entity.User getUser() {
        return user;
    }

    public void setUser(cn.tim.entity.User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "currentUser{" +
                "user=" + user +
                "} " + super.toString();
    }


}
