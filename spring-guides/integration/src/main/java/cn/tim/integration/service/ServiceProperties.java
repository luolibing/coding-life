package cn.tim.integration.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;

/**
 * Created by LuoLiBing on 16/1/21.
 */
@ManagedResource
@ConfigurationProperties(prefix = "service", ignoreUnknownFields = false)
@Configuration
public class ServiceProperties {

    private String greeting = "hello";

    @ManagedAttribute
    public String getGreeting() {
        return this.greeting;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }
}
