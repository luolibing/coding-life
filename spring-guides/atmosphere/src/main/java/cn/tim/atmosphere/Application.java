package cn.tim.atmosphere;

import org.atmosphere.cpr.AtmosphereInitializer;
import org.atmosphere.cpr.AtmosphereServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Collections;

/**
 * Created by LuoLiBing on 16/1/19.
 * 简单聊天服务,采用websocket
 * atmoshpere框架
 * http://async-io.org/tutorial.html
 */
@Configuration
@EnableAutoConfiguration
@EnableScheduling
public class Application {


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public EmbeddedAtmosphereInitializer atmosphereInitializer() {
        return new EmbeddedAtmosphereInitializer();
    }

    @Bean
    public ServletRegistrationBean atmosphereServlet() {
        ServletRegistrationBean registration = new ServletRegistrationBean(
                new AtmosphereServlet(), "/chat/*");
        registration.addInitParameter("org.atmosphere.cpr.packages", "tim");
        // 心跳
        registration.addInitParameter("org.atmosphere.interceptor.HeartbeatInterceptor"
                + ".clientHeartbeatFrequencyInSeconds", "10");
        registration.setLoadOnStartup(0);
        // Need to occur before the EmbeddedAtmosphereInitializer
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registration;
    }

    @Configuration
    static class MvcConfiguration extends WebMvcConfigurerAdapter {
        @Override
        public void addViewControllers(ViewControllerRegistry registry) {
            registry.addViewController("/").setViewName("forward:/home/home.html");
        }
    }

    private static class EmbeddedAtmosphereInitializer extends AtmosphereInitializer
            implements ServletContextInitializer {
        @Override
        public void onStartup(ServletContext servletContext) throws ServletException {
            onStartup(Collections.<Class<?>>emptySet(), servletContext);
        }
    }
}
