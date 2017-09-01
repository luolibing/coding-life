package com.tim.springboot.source.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * User: luolibing
 * Date: 2017/9/1 16:23
 */
@ConfigurationProperties(prefix = "tim.data")
public class DataConfig {

    private String user;

    private String pass;

    private String host;


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

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public String toString() {
        return "DataConfig{" +
                "user='" + user + '\'' +
                ", pass='" + pass + '\'' +
                ", host='" + host + '\'' +
                '}';
    }
}
