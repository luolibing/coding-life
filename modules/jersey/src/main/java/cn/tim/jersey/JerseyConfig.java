package cn.tim.jersey;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

/**
 * Created by LuoLiBing on 17/3/15.
 * Jersey需要注册到ResourceConfig当中
 */
@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(WorkFlowResource.class);
    }
}
