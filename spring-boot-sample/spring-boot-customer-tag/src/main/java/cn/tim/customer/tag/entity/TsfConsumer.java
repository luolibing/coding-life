package cn.tim.customer.tag.entity;

import org.springframework.beans.factory.InitializingBean;

/**
 * User: luolibing
 * Date: 2017/12/14 15:12
 */
public class TsfConsumer extends TsfConfig implements InitializingBean {

    private String server;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(this);
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    @Override
    public String toString() {
        return "TsfConsumer{" +
                "server='" + server + '\'' +
                '}';
    }
}
