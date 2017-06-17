package cn.tim.integration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by LuoLiBing on 16/1/21.
 */
@Component
public class HelloWorldService {

    @Autowired
    private ServiceProperties serviceProperties;

    public String getHelloMessage(String name) {
        return this.serviceProperties.getGreeting() + ":" + name;
    }
}
