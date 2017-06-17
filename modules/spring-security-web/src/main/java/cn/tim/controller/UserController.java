package cn.tim.controller;

import cn.tim.entity.User;
import cn.tim.entity.UserCreateForm;
import cn.tim.entity.validate.UserCreateFormValidator;
import cn.tim.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by LuoLiBing on 15/8/31.
 */
@Controller
public class UserController {


    @Autowired
    private UserService userService;


    @Autowired
    private UserCreateFormValidator userCreateFormValidator;


    @InitBinder("form")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(userCreateFormValidator);
    }


    /**
     * 将访问控制放到service上
     * @param id
     * @return
     */
    @RequestMapping("/user/{id}")
    public ModelAndView getUserPage(@PathVariable Long id) {
        return new ModelAndView("user", "user", userService.getUserById(id));
    }


    @PreAuthorize("hasAuthority('ROLE_TEST')")
    @RequestMapping(value = "/user/create", method = RequestMethod.GET)
    public String getCreateUserPage() {
        return "create";
    }


    @PreAuthorize("@baseUserDetailService.canAccessUser(principal,#id)")
    @RequestMapping(value = "/user/saveOrUpdateUser", method = RequestMethod.POST)
    public String saveOrUpdateUser(@Valid @ModelAttribute("form") UserCreateForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/user/create";
        }
        try {
            userService.save(form);
        } catch (DataIntegrityViolationException e) {
            bindingResult.reject("user.exists", "用户已经存在");
            return "/user/create";
        }
        return "redirect:/user/list";
    }

    @PreAuthorize("hasAuthority('USER_LIST')")
    @RequestMapping(value = "/user/list", method = RequestMethod.GET)
    public ModelAndView getUserList() {
        List<User> userList = userService.getUserList();
        return new ModelAndView("users", "userList", userList);
    }


    @RequestMapping("/")
    public String getHomePage(Map<String, Object> model) {
        model.put("message", "Hello World");
        model.put("title", "Hello Home");
        model.put("date", new Date());
        return "home";
    }


    /**
     * 进入支付页面必须是非匿名非记住用户
     * @param model
     * @return
     */
    @PreAuthorize("isFullyAuthenticated()")
    @RequestMapping("/pay/center")
    public String getPayPage(Map<String, Object> model) {
        model.put("money", new Random(1000l).nextDouble());
        return "pay";
    }


    @PreAuthorize("isFullyAuthenticated()")
    @RequestMapping("/pay/execute")
    public @ResponseBody Object pay() {
        System.out.println("pay");
        return ResponseEntity.ok("支付成功!");
    }


    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public @ResponseBody String error() {
        return "error";
    }

}
