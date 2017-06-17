package cn.tim.retry;

import cn.tim.retry.exception.RemoteAccessException;
import cn.tim.retry.service.RetryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by LuoLiBing on 16/4/25.
 * 重试机制
 */
@RestController
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    private RetryService retryService;

    @RequestMapping(value = "/index")
    public String retry() throws RemoteAccessException {
        retryService.call1("aaaa");
        return "success";
    }

    @RequestMapping(value = "/index1")
    public String retry1() throws RemoteAccessException {
        retryService.call2("aaaa");
        return "success";
    }
}
