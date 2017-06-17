package cn.tim.groovy1.mop2

import cn.tim.groovy.closure.ClosureTest
import org.junit.Test

/**
 * Created by LuoLiBing on 15/10/17.
 */
class Person5 {

    def work() { 'working...' }

    @Test
    void test0() {
        Person5.metaClass.invokeMethod = { String name, args ->
            System.out.println "intercepting call for $name"
            def method = ClosureTest.Person.metaClass.getMetaMethod(name, args)
            if(method) {
                method.invoke(delegate, args)
            } else {
                Person5.metaClass.invokeMissingMethod(delegate, name, args)
            }
        }

        Person5.metaClass.methodMissing = { String name, args ->
            def plays = [ 'BasketBall', 'FootBall', 'PingPang' ]
            def methodInList = plays.find { it == name.split('play')[1] }
            if(methodInList) {
                // 实现
                def impl = { Object[] vargs ->
                    "playing ${name.split('play')[1]}..."
                }

                Person5 instance = this
                instance.metaClass."$name" = impl
                impl(args)
            } else {
                throw new MissingMethodException(name, Person1.class, args)
            }
        }

        def p5 = new Person5()
        p5.playGames()
    }
}
