package cn.tim.spring4.aware;

import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletConfigAware;

import javax.servlet.ServletConfig;
import java.util.Enumeration;

/**
 * Created by LuoLiBing on 16/6/22.
 */
@Component
public class ServiceConfigAwareTest implements ServletConfigAware {
    @Override
    public void setServletConfig(ServletConfig servletConfig) {
        final Enumeration<String> initParameterNames = servletConfig.getInitParameterNames();
        while(initParameterNames.hasMoreElements()) {
            System.out.println(initParameterNames.nextElement());
        }
    }
}
