package cn.tim.interceptor;

import cn.tim.interceptor.annotation.GLock;

/**
 * User: luolibing
 * Date: 2017/8/8 10:18
 */
@org.springframework.stereotype.Service
public class GLockService {


    // GLOCK_1001
    @GLock(key = "'GLock_' + #id")
    public void lockTest(Long id) {
        System.out.println("测试被锁");
    }
}
