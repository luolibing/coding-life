package cn.tim.github.webhook;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * User: luolibing
 * Date: 2017/5/17 15:50
 */
@RestController
public class WebHookController {

    @PostMapping(value = "/webHook")
    public void webHook(@RequestBody Object object) {
        System.out.println(object);
    }
}
