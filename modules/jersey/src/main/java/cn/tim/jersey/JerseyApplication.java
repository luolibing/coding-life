package cn.tim.jersey;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * Created by LuoLiBing on 17/3/15.
 * Jersey一个Restful框架
 * https://jersey.java.net
 * Restful的一个重要的概念就是资源的概念, 取代了以前那种模式化的Mvc贫血模型, MVC往往容易造就纯粹的增删改.
 * 与SpringMvc很明显的一个不一样是, 启动的时候没有标记那么多的接口
 */
@SpringBootApplication
public class JerseyApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        new JerseyApplication()
                .configure(new SpringApplicationBuilder(JerseyApplication.class))
                .run(args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(JerseyApplication.class);
    }
}
