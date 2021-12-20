package cn.tim.springboot.yml;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * User: luolibing
 * Date: 2017/5/9 9:57
 */
@Controller
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

    @GetMapping("/mip")
    public String index() {
        return "index";
    }
}
