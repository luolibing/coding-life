package cn.tim.thinking.clazz14.transaction;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * Created by LuoLiBing on 16/10/8.
 */
public class FileTransaction implements InvocationHandler {

    private Handler fileHandler;

    public FileTransaction(Handler fileHandler) {
        this.fileHandler = fileHandler;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(Objects.equals(method.getName(), "append")) {
            try {
                Object result = method.invoke(fileHandler, args);
                fileHandler.commit();
                return result;
            } catch (Exception e ) {

            }
        }
        return method.invoke(fileHandler, args);
    }
}
