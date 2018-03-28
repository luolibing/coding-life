package cn.tim.aspect.api.controller;

import cn.tim.aspect.api.service.WelcomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

/**
 * User: luolibing
 * Date: 2018/3/24 18:17
 */
@RestController
public class AspectController {

    @Autowired
    private WelcomeService welcomeService;

    @GetMapping("/welcome")
    public Object welcome() {
        welcomeService.welcome();
        return Collections.singletonMap("success", "Y");
    }
}
