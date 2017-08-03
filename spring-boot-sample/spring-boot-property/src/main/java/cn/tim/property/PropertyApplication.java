package cn.tim.property;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by luolibing on 2017/4/14.
 */
@SpringBootApplication
@RestController
//@ImportResource("classpath:*.xml")
public class PropertyApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(PropertyApplication.class, args);
    }

    @Autowired
    private Employee employee;


    @RequestMapping(value = "/index")
    public ResponseEntity index() {
        System.out.println(employee);
        System.out.println("bbb");
        return ResponseEntity.ok().build();
    }

    @Override
    public void run(String... args) throws Exception {
        Person p = new Person();
        System.out.println(p.getName());
    }
}
