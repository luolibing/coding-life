package com.tim.camel.simple;

import org.springframework.stereotype.Component;

@Component
public class SecondHandler {

    public void handle(Long id) {
        System.out.println("second handle " + id);
    }
}
