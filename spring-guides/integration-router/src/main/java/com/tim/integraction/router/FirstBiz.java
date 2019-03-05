package com.tim.integraction.router;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;

/**
 * Created by luolibing on 2019/3/5.
 */
@Service
public class FirstBiz implements Biz {

    @ServiceActivator(inputChannel = "firstChannel", outputChannel = "routeOutput")
    @Override
    public String process(String name) {
        System.out.println("first");
        return "first";
    }
}
