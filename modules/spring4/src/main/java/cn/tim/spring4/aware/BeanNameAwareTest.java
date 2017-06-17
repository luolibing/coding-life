package cn.tim.spring4.aware;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.stereotype.Component;

/**
 * Created by LuoLiBing on 16/6/17.
 */
@Component
public class BeanNameAwareTest implements BeanNameAware {

    @Override
    public void setBeanName(String s) {
        System.out.println("setBeanName=" + s);
    }
}
