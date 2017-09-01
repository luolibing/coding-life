package cn.tim.springboot.returnvalue.handler.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * User: luolibing
 * Date: 2017/8/31 18:40
 */
@RestController
public class UserRestController {

    @GetMapping("/person/throw/rest")
    public Object throwPerson() throws Exception {
        throw new Exception("aaaaa");
    }
}
