package cn.design.patterns.proxy.cglib;

/**
 * Created by LuoLiBing on 17/2/24.
 */
public class CglibTest {

    public static void main(String[] args) {
        PersonServiceProxy proxy = new PersonServiceProxy();
        PersonService personService = (PersonService) proxy.getInstance(new PersonServiceImpl());
        personService.sayHello();
    }
}
