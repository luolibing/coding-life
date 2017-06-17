package cn.tim.groovy1.mop

import org.junit.Test

/**
 * Created by LuoLiBing on 15/10/12.
 */
class TestMethodInvocation1 {

    @Test
    void test1() {
        def val = new Integer(3)
        Integer.metaClass.toString = { -> return  "integer $delegate" }
        println val.toString()
    }

    @Test
    void test2() {
        def obj = new TestMethodInvocation1()
        obj.test1()
        obj.testx()
    }


    /**
     * 在metaclass上定义的方法优先于它本身拥有方法
     */
    @Test
    void test3() {
        TestMethodInvocation1.metaClass.test2 = { -> println "metaclass Invoke" }
        new TestMethodInvocation1().test2()
    }


    @Test
    void test4() {
        println new TestMethodInvocation1().test2()
    }

    Object invokeMethod(String name, Object args) {
        /*
         * 使用println 会导致栈溢出，循环调用
         * println "aaa"
         */
        System.out.println("invokeMethod")

        return "invokeMethod"
    }

    /**
     * 没有找到方法时，默认调用的方法
     * @param name
     * @param args
     * @return
     */
    Object methodMissing(String name, Object args) {
        System.out.println("$name method not found!")
        return "methodmissing"
    }

    class TestInterceptor implements GroovyInterceptable {
        /*Object invokeMethod(String name, Object args) {
            *//**
             * 使用println 会导致栈溢出，循环调用
             * println "aaa"
             *//*
            System.out.println("invokeMethod")

            return "invokeMethod"
        }*/

        String test() {
            println "invoke test"
            return "test"
        }
/*
        Object methodMissing(String name, Object args) {
            System.out.println("$name method not found!")
            return "methodmissing"
        }*/

    }
}
