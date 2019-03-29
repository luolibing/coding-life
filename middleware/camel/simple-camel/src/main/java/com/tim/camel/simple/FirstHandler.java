package com.tim.camel.simple;

import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class FirstHandler {

    public void preHandle(File file) {
        System.out.println("preHandler " + file);
    }
}
