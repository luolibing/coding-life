package cn.tim.function.pro;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.function.Function;

public class FunctionalPro2 {

    public void sayHello(String name, MyFunctionInterface myFunctionInterface) {
        String welcomeStr = myFunctionInterface.getWelcomeStr(name);
        System.out.println("sayHello " + welcomeStr);
    }

    public Function<String, String> action() {
        return (name) -> name + " haha";
    }

    final static class FunctionalPro2$lambda implements MyFunctionInterface {

        @Override
        public String getWelcomeStr(String name) {
            return null;
        }
    }

    /**
     * 通过方法句柄查找到对应的方法类型，进行调用，与反射还是有一定的区别
     * @param clazz
     * @param methodName
     * @return
     */
    public MethodHandle getToStringMH(Class clazz, String methodName) {
        MethodHandle mh = null;
        MethodType mt = MethodType.methodType(clazz);
        MethodHandles.Lookup lk = MethodHandles.lookup();

        try {
            mh = lk.findVirtual(clazz, methodName, mt);
        } catch (NoSuchMethodException | IllegalAccessException mhx) {
            throw (AssertionError)new AssertionError().initCause(mhx);
        }

        return mh;
    }

    public static void main(String[] args) throws Throwable {
        new FunctionalPro2().sayHello("luolibing", (name) -> name + " welcome!");
        new FunctionalPro2().action().apply("liuxiaoling");
        FunctionalPro2 instance = new FunctionalPro2();
        MethodHandle toStringMH = new FunctionalPro2().getToStringMH(String.class, "toString");
        String str = (String) toStringMH.invoke("aaaaaaaaaa");
        System.out.println(str);
    }
}
