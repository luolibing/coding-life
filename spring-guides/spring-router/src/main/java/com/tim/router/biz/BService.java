package com.tim.router.biz;

import com.tim.router.domain.Router;
import org.springframework.stereotype.Service;

@Service("bService")
public class BService implements BizService {

    @Router(order = 2)
    @Override
    public String work(String name) {
        System.out.println("Aservice work " + name);
        hello();
        return name + ":B";
    }

    @Router(order = 10)
    @Override
    public String hello() {
        return "B hello";
    }
}
