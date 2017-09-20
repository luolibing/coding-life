package cn.tim.interceptor;

import org.springframework.stereotype.Component;

/**
 * User: luolibing
 * Date: 2017/9/20 17:51
 */
@Component
public class AopServiceImpl implements AopService {

    public void sayGood() {
        System.out.println("good");
    }
}
