package cn.tim;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by LuoLiBing on 15/7/15.
 */
@SpringBootApplication
@EnableDiscoveryClient
@RestController
@EnableFeignClients
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @RequestMapping(value = "/")
    public void hello() throws InterruptedException {
        Map<String, Integer> datamap= new HashMap<>();
        for(int i=0; i<100000; i++) {
            //Thread.sleep(300);
            String service = helloClient.hello();
            System.out.println(i + ":" + service);
            if(datamap.get(service)==null)
                datamap.put(service, 1);
            else
                datamap.put(service, datamap.get(service)+1);
        }

        System.out.println();
        System.out.println("-----------------------------------");
        for(Map.Entry<String, Integer> entry: datamap.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }

    @Autowired
    HelloClient helloClient;

    @FeignClient("crm")
    interface HelloClient {
        @RequestMapping(value = "/hello", method = RequestMethod.GET)
        String hello();
    }
}
