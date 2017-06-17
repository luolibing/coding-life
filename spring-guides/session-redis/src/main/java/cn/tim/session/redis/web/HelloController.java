package cn.tim.session.redis.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * Created by LuoLiBing on 16/1/15.
 */
@RestController
public class HelloController {

    @RequestMapping(value = "/uid", method = RequestMethod.GET)
    public String userId(HttpSession session) {
        UUID uuid = (UUID) session.getAttribute("uid");
        if(uuid == null) {
            uuid = UUID.randomUUID();
        }
        session.setAttribute("uid", uuid);
        System.out.println(uuid.toString());
        return uuid.toString();
    }
}
