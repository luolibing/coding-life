package cn.design.patterns.proxy.invocation;

import java.lang.reflect.Proxy;
import java.util.HashMap;

/**
 * Created by LuoLiBing on 16/6/24.
 * 动态代理与典型代理的区别,动态代理可以达到保护对象的作用
 * 1 动态代理只有到运行时才会将Proxy代理类创建出来
 * 2 invocationHandler帮助Proxy类实现所代理的类的所有方法
 * 3 判断是否是代理类的方式Proxy.isProxyClass()
 *
 * 其他代理方式
 * 1 防火墙代理Firewall Proxy
 * 2 智能引用Smart Reference Proxy
 * 3 缓存代理Caching Proxy
 * 4 同步代理Synchronization Proxy
 * 5 复杂隐藏代理Complexity Hiding Proxy
 * 6 写入时复制代理
 *
 */
public class MatchMarkingTestDrive {

    HashMap<String, PersonBean> datingDB = new HashMap<>();

    public static void main(String[] args) {
        new MatchMarkingTestDrive().drive();
    }

    public MatchMarkingTestDrive() {
        initializeDatabase();
    }

    public void drive() {
        PersonBean joe = getFromDatabase("Joe Javabean");
        PersonBean joeOwnerProxy = getOwnerProxy(joe);
        PersonBean joeNonOwnerProxy = getNonOwnerProxy(joe);
        System.out.println("Name is " + joeOwnerProxy.getName());
        joeOwnerProxy.setInterests("let's go");
        System.out.println(joeOwnerProxy.getInterests());

        joeNonOwnerProxy.setInterests("i am go");
        System.out.println();
    }

    public PersonBean getFromDatabase(String name) {
        return datingDB.get(name);
    }

    /**
     * 创建代理的方式 Proxy.newProxyInstance(classLoader, interfaces, invocationHandler)
     * 代理产生的对象和实际对象都实现了同一接口,所以可以和实际对象一样使用
     * 实际生成的是$Proxy类,
     * @param personBean
     * @return
     */
    PersonBean getOwnerProxy(PersonBean personBean) {
        return (PersonBean) Proxy.newProxyInstance(
                personBean.getClass().getClassLoader(),
                personBean.getClass().getInterfaces(),
                new OwnerInvocationHandler(personBean));
    }

    PersonBean getNonOwnerProxy(PersonBean personBean) {
        return (PersonBean) Proxy.newProxyInstance(
                personBean.getClass().getClassLoader(),
                personBean.getClass().getInterfaces(),
                new NonOwnerInvocationHandler(personBean));
    }

    void initializeDatabase() {
        PersonBean joe = new PersonBeanImpl();
        joe.setName("Joe Javabean");
        joe.setInterests("cars, computers, music");
        joe.setHotOrNotRating(7);
        datingDB.put(joe.getName(), joe);

        PersonBean kelly = new PersonBeanImpl();
        kelly.setName("Kelly Klosure");
        kelly.setInterests("ebay, movies, music");
        kelly.setHotOrNotRating(6);
        datingDB.put(kelly.getName(), kelly);
    }
}
