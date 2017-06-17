package cn.tim.multipart.datasource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Created by LuoLiBing on 15/7/21.
 */
@Component
@ConfigurationProperties(prefix = "db")
public class Rule {

    String user;
    String pass;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
