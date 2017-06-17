package cn.tim.jquery;

import cn.tim.jquery.entity.Greeting;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

/**
 * Created by LuoLiBing on 15/10/29.
 * jquery 整合 spring boot
 */
@SpringBootApplication
@RestController
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @RequestMapping(value = "/greeting", method = RequestMethod.GET)
    public Greeting getGreeting() {
        return new Greeting(1, "luolibing", "cool");
    }

    @PutMapping(value = "/greeting")
    public Greeting update(@RequestBody Greeting greeting) {
        return greeting;
    }
}
