package com.tim.camel.simple;

import org.springframework.stereotype.Component;

@Component
public class ReceiveHandler {

    public Long receive(Long id) {
        return id;
    }
}
