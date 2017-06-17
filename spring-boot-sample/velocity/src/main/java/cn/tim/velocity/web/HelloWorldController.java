package cn.tim.velocity.web;

import cn.tim.velocity.service.EventServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

/**
 * Created by luolibing on 2017/4/6.
 */
@Controller
public class HelloWorldController {

    @Autowired
    private EventServiceImpl eventService;

    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String hello(Model model) {
        model.addAttribute("data", eventService.list());
        model.addAttribute("array", eventService.array());
        model.addAttribute("time", new Date());
        return "helloworld";
    }
}
