package com.tim.integraction.router;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;

/**
 * Created by luolibing on 2019/3/6.
 */
@Service
public class NullOutputChannel {

    @ServiceActivator(inputChannel = "routeOutput")
    public void response(Message<?> requestMessage) {
        Object payload = requestMessage.getPayload();
        MessageHeaders headers = requestMessage.getHeaders();
        System.out.println("payload = " + payload + ", headers = " + headers);
    }
}
