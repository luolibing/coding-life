package cn.tim.starter;

import org.springframework.stereotype.Component;

/**
 * Created by luolibing on 2017/3/28.
 */
//@Priority(Ordered.HIGHEST_PRECEDENCE)
@Component
public class Cry implements BaseInterface {
    @Override
    public void say() {
        System.out.println("我在大叫");
    }
}
