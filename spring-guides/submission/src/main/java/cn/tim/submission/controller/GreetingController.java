package cn.tim.submission.controller;

import cn.tim.submission.entity.Greeting;
import cn.tim.submission.service.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.List;

/**
 * Created by LuoLiBing on 15/10/26.
 */
@Validated
@RestController
public class GreetingController {

    @Autowired
    private GreetingService greetingService;

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

    /**
     * 方法级别的参数验证，实现方式
     * 使用@Validated注解配合MethodValidationPostProcessor，另外需要捕获ConstraintViolationException异常
     * @param name
     * @param page
     * @return
     */
    @GetMapping("/validate")
    public Object validate(
            @NotNull(message = "不能为空")
            @Size(message = "长度在1-10之间", min = 1, max = 10)
            @RequestParam("name") String name,
            @Min(value = 3, message = "页面不能小于3") int page) {
        greetingService.validate(page, 100);
        return Collections.singletonMap("success", "y");
    }


}
