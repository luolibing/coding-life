package cn.tim.maven.profiles;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by luolibing on 2017/4/6.
 * 使用maven支持多环境配置切换
 */
@SpringBootApplication
public class MavenProfilesApplication {
    public static void main(String[] args) {
        SpringApplication.run(MavenProfilesApplication.class, args);
    }
}
