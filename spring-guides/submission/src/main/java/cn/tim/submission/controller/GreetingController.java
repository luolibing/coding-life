package cn.tim.submission.controller;

import cn.tim.submission.entity.Greeting;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * Created by LuoLiBing on 15/10/26.
 */
@Controller
public class GreetingController {

    @RequestMapping(value = "/greeting", method = RequestMethod.GET)
    public String greetingForm(@ModelAttribute(name = "name") String name, @ModelAttribute Greeting greeting) {
        System.out.println(name);
        return "greeting";
    }


    @RequestMapping(value = "/greeting", method = RequestMethod.POST)
    public String greetingSubmit(@ModelAttribute Greeting greeting, Model model) {
        model.addAttribute("greeting", greeting);
        return "result";
    }

    /**
     * RequestParam和ModelAttribute的区别，后者会在一次请求的前后生效，即如果在request中包含的参数，在响应的时候也可以访问
     * 而RequestParam仅仅在请求的时候会传递到controller，之后不会在响应中传出。
     *
     * RequestParam和没有这个注解的区别，两者最终使用的HandlerMethodArgumentResolver不一样
     * 没有注解修饰使用的是ServletModelAttributeMethodProcessor这个来处理
     * 而用@RequestParam修饰的则使用RequestParamMethodArgumentResolver来处理
     * @param list
     * @param name
     * @return
     */
    @PutMapping("/long")
    @ResponseBody
    public Object acceptLong(List<Long> list, Long name) {
        System.out.println(list);
        System.out.println("name = " + name);
        return Collections.singletonMap("success", "y");
    }
}
