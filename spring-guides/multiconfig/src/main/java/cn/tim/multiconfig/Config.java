package cn.tim.multiconfig;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LuoLiBing on 16/6/2.
 */
@Configuration
@ConfigurationProperties(prefix = "my")
public class Config {

    private List<String> tests = new ArrayList<>();

    public List<String> getTests() {
        return tests;
    }
}
