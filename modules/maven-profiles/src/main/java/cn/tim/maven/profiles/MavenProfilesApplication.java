package cn.tim.maven.profiles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by luolibing on 2017/4/6.
 * 使用maven支持多环境配置切换
 */
//@ImportResource(locations = "classpath: **/spring.xml")
@SpringBootApplication
public class MavenProfilesApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(MavenProfilesApplication.class, args);
    }

    @Autowired
    private MavenConfig mavenConfig;

    @Override
    public void run(String... strings) throws Exception {
        System.out.println(mavenConfig);
    }
}
