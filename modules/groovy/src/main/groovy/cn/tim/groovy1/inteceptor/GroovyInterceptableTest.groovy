package cn.tim.groovy1.inteceptor

/**
 * Created by LuoLiBing on 15/10/13.
 * 实现GroovyInterceptable和不实现的区别，不实现，只有当方法找不到时才会调用invokeMethod方法，实现则都会被拦截进GroovyInterceptable
 */
class GroovyInterceptableTest implements GroovyInterceptable {

    def start() { System.out.println "start" }

    def check() { start(); System.out.println "check" }

    def drive() { println "drive" }

    def invokeMethod(String name, Object args) {
        System.out.println("$name $args")
        /**
         * 为什么不能用println 因为println 是在本身基础上调用 println(object, String),
         * groovy在调用者GroovyInterceptableTest上动态注入了println方法，这样又会被拦截到invokeMethod
         * 然后就陷入了死循环调用，导致栈溢出，应该使用System.out.println，同理不能在其他方法中直接调用println，
         * 而且互相调用，在使用invokeMethod时极易导致栈溢出异常，而应该通过invoke或者call的方式
         */
        // 前置建议advice
        if(name != 'check') {
            System.out.println("before invoke check method before invoke $name")
            GroovyInterceptableTest.metaClass.getMetaMethod("check").invoke(this, null)
        }

        System.out.println "call to $name interceptable!"
        if(GroovyInterceptableTest.metaClass.respondsTo(this, name, args)) {
            GroovyInterceptableTest.metaClass.getMetaMethod(name, args).invoke(this, args)
        } else {
            System.out.println "method $name $args not found!"
        }
    }
}
