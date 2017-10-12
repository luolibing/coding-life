package cn.tim.submission.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * User: luolibing
 * Date: 2017/10/12 17:25
 */
@Service
public class BService {

    @Autowired
    private AService aService;

    public BService() {
        System.out.println("init BService");
    }
}
