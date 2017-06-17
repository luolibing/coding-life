package cn.tim.aop.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by LuoLiBing on 16/1/11.
 */
@RestController
public class MainController {

    @RequestMapping(value = "/")
    public Object index() {
        return ResponseEntity.ok().build();
    }
}
