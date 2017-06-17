package cn.tim.groovy1.mop2

import org.junit.Test

/**
 * Created by LuoLiBing on 15/10/17.
 * 方法合成
 */
class Person2 {

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

            Person2 instance = this
            instance.metaClass."$name" = impl
            impl(args)
        } else {
            throw new MissingMethodException(name, Person1.class, args)
        }
    }

    @Test
    void test0() {
        def person = new Person2()
        println person.playFootBall()
        println person.work()
        println person.playBasketBall()
    }
}
