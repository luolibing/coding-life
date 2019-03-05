package com.tim.integraction.router;

import org.springframework.stereotype.Service;

/**
 * Created by luolibing on 2019/3/5.
 */
@Service
public class SimpleRouterService implements Router {

    @org.springframework.integration.annotation.Router(inputChannel = "routeChannel1")
    @Override
    public String route(String name) {
        System.out.println("router name[" + name + "]");
        if("jack".equals(name)) {
            return "firstChannel";
        } else {
            return "secondChannel";
        }
    }
}
