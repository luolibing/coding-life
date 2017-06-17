package cn.timedu.log4j.web;

import cn.timedu.log4j.service.HelloWorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

/**
 * Created by LuoLiBing on 16/1/13.
 */
@RestController
public class HelloWorldController {

    @Autowired
    HelloWorldService helloWorldService;

    /*@RequestMapping(value = "/")
    public Map<String, String> helloWorld() {
        return Collections.singletonMap("message", this.helloWorldService.getHelloMessage());
    }

    @RequestMapping(value = "/foo")
    public String foo() {
        throw new IllegalArgumentException("Server error");
    }*/

    @RequestMapping("/")
    public String home(Map<String, Object> model) {
        model.put("message", "Hello World");
        model.put("title", "Hello Home");
        model.put("date", new Date());
        return "home";
    }

    @RequestMapping("/foo")
    public String foo() {
        throw new RuntimeException("Expected exception in controller");
    }
}
