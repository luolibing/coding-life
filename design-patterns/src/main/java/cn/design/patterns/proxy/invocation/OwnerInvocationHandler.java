package cn.design.patterns.proxy.invocation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by LuoLiBing on 16/6/24.
 * 只能修改自己的资料,但是不能给自己打分
 *
 * 调用处理器,被代理对象所有方法调用都会经过invoke方法
 */
public class OwnerInvocationHandler implements InvocationHandler {

    private PersonBean personBean;

    public OwnerInvocationHandler(PersonBean personBean) {
        this.personBean = personBean;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(method.getName().startsWith("get")) {
            return method.invoke(personBean, args);
        } else if(method.getName().equals("setHotOrNotRating")) {
            throw new IllegalAccessException();
        } else if(method.getName().startsWith("set")) {
            return method.invoke(personBean, args);
        }
        return null;
    }
}
