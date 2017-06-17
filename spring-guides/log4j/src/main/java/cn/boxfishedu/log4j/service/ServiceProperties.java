package cn.timedu.log4j.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by LuoLiBing on 16/1/13.
 */
@Component
@ConfigurationProperties(prefix = "service", ignoreUnknownFields = false)
public class ServiceProperties {

    private String name = "World";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
