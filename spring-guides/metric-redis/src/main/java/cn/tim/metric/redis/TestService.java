package cn.tim.metric.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by LuoLiBing on 16/2/23.
 */
@Component
@ConfigurationProperties(prefix = "service", ignoreUnknownFields = false)
public class TestService {

    private String name = "test";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHelloMessage() {
        return "Hello " + name;
    }
}
