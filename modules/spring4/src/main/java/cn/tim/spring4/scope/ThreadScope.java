package cn.tim.spring4.scope;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

/**
 * Created by LuoLiBing on 16/6/2.
 * 自定义线程scope
 */
public class ThreadScope implements Scope {

    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {
        return objectFactory.getObject();
    }

    @Override
    public Object remove(String name) {
        return null;
    }

    @Override
    public void registerDestructionCallback(String name, Runnable callback) {

    }

    @Override
    public Object resolveContextualObject(String key) {
        return null;
    }

    @Override
    public String getConversationId() {
        return null;
    }
}
