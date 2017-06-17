package cn.tim.groovy1.mop2

import org.junit.Test

/**
 * Created by LuoLiBing on 15/10/17.
 * 合成方法，拦截、缓存、调用
 */
class Person1 {

    def work() { "working..." }

    def plays = [ 'BasketBall', 'FootBall', 'PingPang' ]

    @Test
    void test0() {
        def jack = new Person1()

        println jack.work()
        println jack.playBasketBall()
        println jack.playFootBall()
        try{
            println jack.playBall()
        } catch (Exception ex) {
            println "Error：" + ex.message
        }
    }

    def methodMissing(String name, args) {
        System.out.println "methodMissing called for $name"
        def methodInList = plays.find { it == name.split('play')[1] }
        if(methodInList) {
            "playing ${name.split('play')[1]}!"
        } else {
            throw new MissingMethodException(name, Person1.class, args)
        }
    }
}
