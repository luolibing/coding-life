package cn.tim.service;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

/**
 * Created by LuoLiBing on 15/8/28.
 */
@Service
class SimpleService {

    @Secured("ROLE_USER")
    public String access() {
        return "hello access!";
    }

}
