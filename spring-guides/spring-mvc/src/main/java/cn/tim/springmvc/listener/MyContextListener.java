package cn.tim.springmvc.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 上下文监听器 ServletContextListener
 * Created by luolibing on 2017/6/23.
 */
public class MyContextListener implements ServletContextListener {

    private ServletContext context;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        this.context = servletContextEvent.getServletContext();
        this.context.setAttribute("name", "luolibing");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        this.context = null;
    }
}
