package cn.tim.counting;

import cn.tim.autoconfiguration.beans.Counting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

/**
 * Created by luolibing on 2017/4/5.
 */
@RestController
@SpringBootApplication
public class CountingApplication {

    public static void main(String[] args) {
        SpringApplication.run(CountingApplication.class, args);
    }


    private Counting counting;

    @Autowired
    @ConditionalOnBean(Counting.class)
    public void setCounting(Counting counting) {
        this.counting = counting;
    }

    @ConditionalOnBean(Counting.class)
    @RequestMapping(value = "/increment", method = RequestMethod.POST)
    public Object increment() {
        return Collections.singletonMap("result", counting.increment());
    }

    @ConditionalOnBean(Counting.class)
    @RequestMapping(value = "/currentVal", method = RequestMethod.GET)
    public Object current() {
        return Collections.singletonMap("result", counting.currentVal());
    }

    @ConditionalOnBean(Counting.class)
    @RequestMapping(value = "/reset", method = RequestMethod.GET)
    public Object reset() {
        counting.reset();
        return Collections.singletonMap("result", counting.currentVal());
    }
}
