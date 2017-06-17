package cn.tim.restful.controller
import org.springframework.web.bind.annotation.RequestMapping
/**
 * Created by LuoLiBing on 15/10/15.
 */
class BaseController {

    @RequestMapping(value = "/index")
    public String test() {
        return "success";
    }
}
