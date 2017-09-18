package cn.tim.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

/**
 * User: luolibing
 * Date: 2017/9/18 16:43
 */
@RestController
public class TestController {

    @GetMapping("/person")
    public Object person() {
        return Collections.singletonMap("name", "luolibing");
    }
}
