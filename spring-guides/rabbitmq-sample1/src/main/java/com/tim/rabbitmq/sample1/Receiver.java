package com.tim.rabbitmq.sample1;

import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

/**
 * Created by luolibing on 2017/6/16.
 */
@Component
public class Receiver {

    private CountDownLatch latch = new CountDownLatch(100);

    public void receiveMessage(String message) {
        System.out.println("Received <" + message + ">");
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
