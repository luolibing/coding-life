package cn.tim.quartz.database;

import cn.tim.quartz.database.config.ScheduleConfig;
import cn.tim.quartz.database.utils.ApplicationContextProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by LuoLiBing on 16/6/23.
 */
@SpringBootApplication
@Import({ScheduleConfig.class})
@ImportResource(value = "classpath*:META-INF/spring-*.xml")
public class Application {

    public static void main(String[] args) {
        ApplicationContextProvider.createInstance(SpringApplication.run(Application.class, args));
    }
}
