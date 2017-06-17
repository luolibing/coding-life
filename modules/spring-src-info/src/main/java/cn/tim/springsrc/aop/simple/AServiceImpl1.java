package cn.tim.springsrc.aop.simple;

import org.springframework.aop.framework.AopContext;

/**
 * Created by luolibing on 2017/6/9.
 */
public class AServiceImpl1 implements AService {

    public void a() {
        System.out.println("execute a");
        // 这个地方的this指向的是目标对象，因此this.b()不会执行事务切面
        this.b();

        // 从AopContext上下文中取出当前代理对象，执行代理对象的b()方法
        ((AService)AopContext.currentProxy()).b();
    }

    public void b() {
        System.out.println("execute b");
    }
}
