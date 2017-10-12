package cn.tim.submission.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * User: luolibing
 * Date: 2017/10/11 11:15
 */
@Validated
@Service
public class GreetingService {

    public GreetingService() {
        System.out.println("init GreetingService");
    }

    public void validate(
            @Min(message = "service中的page不能小于5", value = 5) int page,
            @Max(message = "service中的size不能大于20", value = 20) int size) {
        System.out.println(page + " : " + size);
    }
}
