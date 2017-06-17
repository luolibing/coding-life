import cn.tim.config.TestProperties;
import cn.tim.multipart.datasource.ConfigTest;
import cn.tim.multipart.datasource.DBUtils;
import cn.tim.multipart.datasource.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by TIM on 2015/6/26.
 */
@SpringBootApplication(exclude = {})
@ComponentScan(value = "cn.tim")
@RestController
public class Application extends SpringBootServletInitializer  {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder;
    }

    @RequestMapping(value = "/sayHello", method = RequestMethod.GET)
    public @ResponseBody String sayHello() {
        return configTest.getUsername() + ":" + configTest.getPassword();
    }

    @RequestMapping(value = "/sayHi", method = RequestMethod.GET)
    public @ResponseBody String sayHi() {
        System.out.println(testProperties);
        return rule.getUser() + ":" + rule.getPass();
    }

    @RequestMapping(value = "/getBean/{beanName}", method = RequestMethod.GET)
    public @ResponseBody String getBean(HttpServletRequest request, @PathVariable String beanName) {
        Object bean = DBUtils.getBean(request.getServletContext(), beanName);
        return "beanClassName:" + bean.getClass();
    }

    @Autowired
    private ConfigTest configTest;

    @Autowired
    private TestProperties testProperties;

    @Autowired
    private Rule rule;
}
