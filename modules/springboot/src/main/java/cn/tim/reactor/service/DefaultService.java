package cn.tim.reactor.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.bus.Event;
import reactor.bus.EventBus;

/**
 * Created by LuoLiBing on 15/8/10.
 */
@Component
public class DefaultService {

    private final static Logger log = LoggerFactory.getLogger(DefaultService.class);

    @Autowired
    private EventBus eventBus;

    public void test() {
        log.info("testing service..");
        eventBus.notify("test.topic", Event.wrap("hello world"));
    }

}
