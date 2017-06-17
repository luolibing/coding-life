package cn.tim.springboot.yml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * User: luolibing
 * Date: 2017/5/9 9:57
 */
@SpringBootApplication
public class YmlApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(YmlApplication.class, args);
    }

    @Autowired
    private YmlConfig ymlConfig;

    @Override
    public void run(String... strings) throws Exception {
        System.out.println(ymlConfig.getAppCode());
    }
}
