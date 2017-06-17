package cn.tim.spring4.aware;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

/**
 * Created by LuoLiBing on 16/6/17.
 */
@Component
public class ApplicationEventPublisherAwareTest implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void notifyMessage() {
        this.applicationEventPublisher.publishEvent(new Message("startUp ApplicationEventPublisherAware"));
    }
}
