package cn.tim.groovy1.inteceptor

import org.junit.Test

/**
 * Created by LuoLiBing on 15/10/13.
 */
class Test1 {

    @Test
    void test1() {
        new GroovyInterceptableTest().start()
    }

    @Test
    void test2() {
        /**
         * 在metaClass上添加invokeMethod方法，用于无权修改源码，或者JAVA代码的情况
         */
        GroovyMetaClassInterceptableTest.metaClass.invokeMethod = {String name,Object args ->
            System.out.println("$name $args")

            if(name != 'check') {
                System.out.println("before invoke check method before invoke $name")
                GroovyMetaClassInterceptableTest.metaClass.getMetaMethod("check").invoke(delegate, null)
            }

            System.out.println "call to $name interceptable!"
            if(GroovyMetaClassInterceptableTest.metaClass.respondsTo(delegate, name, args)) {
                GroovyMetaClassInterceptableTest.metaClass.getMetaMethod(name, args).invoke(delegate, args)
            } else {
                System.out.println "method $name $args not found!"
                GroovyMetaClassInterceptableTest.metaClass.invokeMissingMethod(delegate, name, args)
            }
        }

        new GroovyMetaClassInterceptableTest().drive1()
    }

    @Test
    void test3() {
        Integer.metaClass.invokeMethod = { String name, Object args ->
            println Integer.metaClass.getClass().name
        }
        3.toString()
    }
}
