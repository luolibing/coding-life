package cn.tim.validate.controller;

import cn.tim.validate.entity.Person;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.nio.file.Paths;

/**
 * Created by LuoLiBing on 15/10/8.
 */
@Controller
public class ValidateController extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/results").setViewName("results");
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showForm(Person person) {
        return "form";
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public String checkPersonInfo(@Valid Person person, BindingResult bindingResult) {
        // 绑定,校验查看是否有教研错误
        if(bindingResult.hasErrors()) {
            return "form";
        }
        return "redirect:/results";
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public void staticResource(HttpServletRequest request) {
        File file = Paths.get(request.getServletContext().getRealPath("index.html")).toFile();
        System.out.println(file);
    }

    @GetMapping(value = "/detail")
    public Object detail(Long id) {
        System.out.println("detail id = " + id);
        return ResponseEntity.ok().build();
    }
}
