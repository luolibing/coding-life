package com.tim.router.biz;

import com.tim.router.domain.Router;
import org.springframework.stereotype.Service;

@Service("aService")
public class AService implements BizService {

    @Router(order = 10)
    @Override
    public String work(String name) {
        System.out.println("Aservice work " + name);
        hello();
        return name + ":A";
    }

    @Router(order = 10)
    @Override
    public String hello() {
        return "A hello";
    }


}
