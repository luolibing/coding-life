package cn.tim;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by LuoLiBing on 15/7/15.
 */
@SpringBootApplication
@EnableCircuitBreaker
@EnableDiscoveryClient
@EnableHystrix
@RestController
public class Application {

    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class).web(true).run(args);
    }

    @Autowired
    private StoreIntegration storeIntegration;

    @RequestMapping(value = "/")
    public Object index() {
        return storeIntegration.getStores(null);
    }

}
