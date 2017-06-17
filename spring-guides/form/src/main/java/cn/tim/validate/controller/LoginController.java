package cn.tim.validate.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

/**
 * Created by TIM on 2017/3/12.
 */
@RestController
public class LoginController {

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object login(LoginForm loginForm) {
        if(loginForm.getUsername().equals("admin") && loginForm.getPassword().equals("admin")) {
            return Collections.singletonMap("code", 0);
        }
        return Collections.singletonMap("code", 1);
    }


    class LoginForm {
        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
