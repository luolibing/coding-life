package cn.tim.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collections;

/**
 * User: luolibing
 * Date: 2017/9/18 16:43
 */
@Controller
public class TestController {

    @GetMapping("/person")
    public Object person() {
        return Collections.singletonMap("name", "luolibing");
    }

    @GetMapping("/mip")
    public String index() {
        return "index";
    }

    @GetMapping("/404")
    public String notFound() {
        return "404";
    }
}
