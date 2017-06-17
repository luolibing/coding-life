package cn.tim.validate.controller;

import cn.tim.validate.entity.Person;
import cn.tim.validate.validator.FormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.validation.Valid;

/**
 * Created by LuoLiBing on 15/10/8.
 */
@Controller
public class FormValidateController extends WebMvcConfigurerAdapter {

    @Autowired
    private FormValidator validator;

    @InitBinder("person")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(validator);
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showForm(Person person) {
        return "forward: index.html";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String checkPersonInfo(@Valid @ModelAttribute("person") Person person, BindingResult bindingResult) {
        // 绑定,校验查看是否有教研错误
        if(bindingResult.hasErrors()) {
            return "login";
        }
        return "redirect:/results";
    }

}
