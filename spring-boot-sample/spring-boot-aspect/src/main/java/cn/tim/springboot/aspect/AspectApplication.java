package cn.tim.springboot.aspect;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * User: luolibing
 * Date: 2017/6/1 14:11
 */
@RestController
@SpringBootApplication
public class AspectApplication {

    public static void main(String[] args) {
        SpringApplication.run(AspectApplication.class, args);
    }

    @Autowired
    private UserManage userManage;

    @RequestMapping(value = "/api/user")
    public void run() {
        userManage.delete();
    }
}
