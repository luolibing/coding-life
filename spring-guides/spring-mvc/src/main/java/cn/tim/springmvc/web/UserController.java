package cn.tim.springmvc.web;

import cn.tim.springmvc.entity.User;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by luolibing on 2017/6/22.
 */
public class UserController extends AbstractController {

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("handleRequestInternal");
        List<User> userList = new ArrayList<>();
        User user = new User();
        user.setUsername("luo");
        user.setAge(18);
        userList.add(user);
        ModelAndView modelAndView = new ModelAndView("userlist");
        modelAndView.addObject("userList", userList);
        modelAndView.addObject("user", user);
        return modelAndView;
    }


}
