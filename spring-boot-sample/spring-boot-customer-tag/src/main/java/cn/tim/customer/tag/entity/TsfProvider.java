package cn.tim.customer.tag.entity;

import org.springframework.beans.factory.InitializingBean;

/**
 * User: luolibing
 * Date: 2017/12/14 15:11
 */
public class TsfProvider extends TsfConfig implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(this);
    }

}
