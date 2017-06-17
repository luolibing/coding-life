package cn.tim.groovy1.mop2

import org.junit.Test

/**
 * Created by LuoLiBing on 15/10/17.
 */
class Person3 implements GroovyInterceptable {

    def work() {
        'working...'
    }

    def plays = [ 'BasketBall', 'FootBall', 'PingPang' ]

    /**
     * 方法合成 methodMissing
     * @param name
     * @param args
     * @return
     */
    def methodMissing(String name, args) {
        System.out.println "methodMissing called for $name"
        def methodInList = plays.find { it == name.split('play')[1] }
        if(methodInList) {
            // 实现
            def impl = { Object[] vargs ->
                "playing ${name.split('play')[1]}..."
            }

            Person3 instance = this
            instance.metaClass."$name" = impl
            impl(args)
        } else {
            throw new MissingMethodException(name, Person1.class, args)
        }
    }

    /**
     * 调用method
     * @param name
     * @param args
     */
    def invokeMethod(String name, args) {
        System.out.println "intercepting call for $name"
        def method = metaClass.getMetaMethod(name, args)
        if(method) {
            method.invoke(this, args)
        } else {
            metaClass.invokeMethod(this, name, args)
        }
    }


    @Test
    void test0() {
        def p3 = new Person3()
        System.out.println p3.playFootBall()
    }
}
