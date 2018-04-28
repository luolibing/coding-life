package com.tim.vue.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by luolibing on 2018/2/7.
 */
@Controller
public class WelcomeController {

    @GetMapping("/welcome")
    public String helloWorld() {
        return "hello";
    }

    @GetMapping("/component")
    public String component() {
        return "component";
    }

    @GetMapping("/lifecycle")
    public String lifecycle() {
        return "lifecycle";
    }

    @GetMapping("/wx/{viewName}")
    public String view(@PathVariable String viewName) {
        return viewName;
    }
}
