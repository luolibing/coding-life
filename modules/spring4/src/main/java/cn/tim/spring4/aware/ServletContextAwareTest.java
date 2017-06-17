package cn.tim.spring4.aware;

import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;

/**
 * Created by LuoLiBing on 16/6/22.
 */
@Component
public class ServletContextAwareTest implements ServletContextAware {
    @Override
    public void setServletContext(ServletContext servletContext) {
        System.out.println(servletContext.getMinorVersion());
    }
}
