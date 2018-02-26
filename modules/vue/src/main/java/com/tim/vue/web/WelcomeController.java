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
    public String index() {
        return "welcome";
    }

    @GetMapping("/wx/{view}")
    public String view(@PathVariable String view) {
        return view;
    }
}
