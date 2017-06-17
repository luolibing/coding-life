package cn.tim.jsp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by LuoLiBing on 15/8/6.
 */
@Controller
public class JspController {

    @RequestMapping(value = "/index")
    public String index() {
        System.out.println(data);
        System.out.println("index");
        return "index";
    }

    String data;

}
