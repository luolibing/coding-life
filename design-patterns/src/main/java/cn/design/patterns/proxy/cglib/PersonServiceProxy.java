package cn.design.patterns.proxy.cglib;

import net.sf.cglib.core.DefaultNamingPolicy;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by LuoLiBing on 17/2/24.
 * JDK动态代理依靠接口实现, 代理类和被代理类实现同样的接口. 所以没有接口的类, 不能使用JDK代理.
 * cglib只要实现MethodInterceptor即可, 并不会对原有代码有什么限制, 因为是使用的继承, 所以不能给final型的类添加cglib代理.
 */
public class PersonServiceProxy implements MethodInterceptor {

    private Object object;

    /**
     * 产生代理类
     * @param target
     * @return
     */
    public Object getInstance(Object target) {
        this.object = target;
        Enhancer enhancer = new Enhancer();
        // 设置父类, 所以代理类实际上是被代理类的子类, 使用了继承
        enhancer.setSuperclass(target.getClass());
        // 回调方法
        enhancer.setCallback(this);
        // 创建代理对象
        return enhancer.create();
    }

    // 回调方法
    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("start transaction");
        Object result = methodProxy.invoke(object, args);
        System.out.println("commit");
        return result;
    }

    private Class<?> createEnhancedSubclass(Object target) {
        this.object = target;
        Enhancer enhancer = new Enhancer();
        // 设置父类, 所以代理类实际上是被代理类的子类, 使用了继承
        enhancer.setSuperclass(target.getClass());
        enhancer.setNamingPolicy(DefaultNamingPolicy.INSTANCE);
        // 回调方法
        enhancer.setCallback(this);
        return enhancer.createClass();
    }
}
