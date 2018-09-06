package cn.tim.reactor.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.bus.EventBus;
import reactor.spring.context.annotation.Consumer;
import reactor.spring.context.annotation.Selector;

/**
 * Created by LuoLiBing on 15/8/10.
 */
@Consumer
public class DefaultHandler {

    private final static Logger log = LoggerFactory.getLogger(DefaultHandler.class);

    @Autowired
    private EventBus eventBus;

    @Selector("test.topic")
    public void ontestTopic(String msg) {
        log.info("onTestTopic message:" + msg);
    }
}
