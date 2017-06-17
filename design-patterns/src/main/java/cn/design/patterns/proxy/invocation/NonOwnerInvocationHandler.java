package cn.design.patterns.proxy.invocation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by LuoLiBing on 16/6/24.
 * 不能修改别人的资料,但是可以给别人打分
 */
public class NonOwnerInvocationHandler implements InvocationHandler {

    private PersonBean personBean;

    public NonOwnerInvocationHandler(PersonBean personBean) {
        this.personBean = personBean;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(method.getName().startsWith("get")) {
            return method.invoke(personBean, args);
        } else if(method.getName().equals("setHotOrNotRating")) {
            return method.invoke(personBean, args);
        } else if(method.getName().startsWith("set")) {
            throw new IllegalAccessException();
        }
        return null;
    }
}
