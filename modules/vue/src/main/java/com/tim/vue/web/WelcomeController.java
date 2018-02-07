package com.tim.vue.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by luolibing on 2018/2/7.
 */
@Controller
public class WelcomeController {

    @GetMapping("/welcome")
    public String index() {
        return "welcome";
    }
}
