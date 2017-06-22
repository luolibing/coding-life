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
public class UserController extends AbstractController{

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        List<User> userList = new ArrayList<>();
        User userA = new User();
        User userB = new User();
        userA.setUsername("luo");
        userA.setAge(10);
        userB.setUsername("liu");
        userB.setAge(20);
        userList.add(userA);
        userList.add(userB);
        return new ModelAndView("userlist", "users", userList);
    }
}
