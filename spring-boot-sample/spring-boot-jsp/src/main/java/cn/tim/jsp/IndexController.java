package cn.tim.jsp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by LuoLiBing on 16/1/29.
 */
@Controller
public class IndexController {

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String index() {
        return "index";
    }
}
