package cn.tim.multipart.datasource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Created by LuoLiBing on 15/7/21.
 */
@Configuration
@ConfigurationProperties(prefix = "db")
@Profile({"development","test"})
public class ConfigTest {
    String username;
    String password;
    String[] tables;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String[] getTables() {
        return tables;
    }

    public void setTables(String[] tables) {
        this.tables = tables;
    }
}
