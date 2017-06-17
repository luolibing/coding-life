package cn.tim.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by LuoLiBing on 16/7/15.
 * simple:
 - key: luolibing
 - value: ll
 - key: luo
 - value: liu
 */
@Configuration
@ConfigurationProperties
public class TestProperties {
    private Map[] simple;

    public TestProperties() {
    }

    public TestProperties(Map[] simple) {
        this.simple = simple;
    }

    public Map[] getSimple() {
        return simple;
    }

    public void setSimple(Map[] simple) {
        this.simple = simple;
    }
}


