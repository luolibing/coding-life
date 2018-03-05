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

    public static void main(String[] args) {
        int i = 155;
        i = (i&0x55555555) + ((i >> 1) & 0x55555555);
        i = (i&0x33333333) + ((i >> 2) & 0x33333333);
        i = (i&0x0F0F0F0F) + ((i >> 4) & 0x0F0F0F0F);
        i = ((i * 0x01010101) >> 24);
        System.out.println(i);
    }
}
