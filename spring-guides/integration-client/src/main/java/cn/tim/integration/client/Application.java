package cn.tim.integration.client;

import cn.tim.integration.client.service.RequestGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.integration.config.EnableIntegration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LuoLiBing on 16/1/26.
 */
@SpringBootApplication
@EnableIntegration
@ImportResource("integration-client.xml")
public class Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    RequestGateway requestGateway;

    @Override
    public void run(String... strings) throws Exception {
        List<Map<String, String>> request = new ArrayList<>();
        Map<String, String> beanMap = new HashMap<>();
        beanMap.put("name", "ku");
        request.add(beanMap);
        beanMap = new HashMap<>();
        beanMap.put("name", "zhang");
        request.add(beanMap);
        final Object attack = requestGateway.attack(request);
        System.out.println(attack);
    }
}
