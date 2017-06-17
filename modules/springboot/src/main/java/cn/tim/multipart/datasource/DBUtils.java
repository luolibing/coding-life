package cn.tim.multipart.datasource;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;

/**
 * Created by LuoLiBing on 15/7/22.
 */
public class DBUtils {

    public static Object getBean(ServletContext ctx, String bean) {
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(ctx);
        return webApplicationContext.getBean(bean);
    }
}
