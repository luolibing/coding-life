package cn.tim.springboot.returnvalue.handler.web;

import cn.tim.springboot.returnvalue.handler.entity.Person;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by luolibing on 2017/7/10.
 */
@Controller
public class UserController {

    @GetMapping("/person/{id}")
    public ModelAndView get(@PathVariable Long id) {
        Person person = new Person();

        person.setId(1);
        person.setName("luolibing");
        person.setAge(30);
        return new ModelAndView("person", "person", person);
    }
}
