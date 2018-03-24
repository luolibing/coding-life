package cn.tim.aspect.api.service;

import cn.tim.aspect.api.annotation.MyLog;
import org.springframework.stereotype.Service;

/**
 * User: luolibing
 * Date: 2018/3/24 18:16
 */
@Service
public class WelcomeService {

    @MyLog
    public void welcome() {
        System.out.println("Welcome");
    }
}
