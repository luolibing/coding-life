package cn.design.patterns.proxy.cglib;

/**
 * Created by LuoLiBing on 17/2/24.
 */
public class PersonServiceImpl implements PersonService {
    @Override
    public void sayHello() {
        System.out.println("Hi good job!");
    }
}
