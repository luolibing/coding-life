package cn.tim.thinking.clazz14;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * Created by LuoLiBing on 16/8/6.
 */
public class MethodHandler {

    Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public void methodType() {
        MethodType methodType = MethodType.methodType(String.class);
        MethodType mtSetter = MethodType.methodType(void.class, Object.class);
    }

    public void methodHandler() {
        MethodHandle mh;
        MethodType mt = MethodType.methodType(String.class);
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        try {
            mh = lookup.findVirtual(getClass(), "toString", mt);
            System.out.println(mh.invoke());
            LoggerUtils.logger.info("toString");
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "MethodHandler{}";
    }

    @Test
    public void methodHandlerTest() {
        new MethodHandler().methodHandler();
    }
}
