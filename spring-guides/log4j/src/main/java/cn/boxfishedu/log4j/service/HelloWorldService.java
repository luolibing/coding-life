package cn.timedu.log4j.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by LuoLiBing on 16/1/13.
 */
@Component
public class HelloWorldService {

    public final static Logger logger = LoggerFactory.getLogger(HelloWorldService.class);

    @Autowired
    ServiceProperties properties;

    public String getHelloMessage() {
        logger.info("getHelloMessage!!!");
        return "Hello " + this.properties.getName();
    }
}
