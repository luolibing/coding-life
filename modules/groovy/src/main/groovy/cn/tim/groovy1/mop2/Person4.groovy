package cn.tim.groovy1.mop2

import org.junit.Test

/**
 * Created by LuoLiBing on 15/10/17.
 * EMC=ExpandoMetaClass
 */
class Person4 {

    def work() { 'working...' }

    /**
     * methodMissing方法，没有找到对应的方法默认调用
     */
    @Test
    void test0() {
        Person4.metaClass.methodMissing = { String name, args ->
            def plays = [ 'BasketBall', 'FootBall', 'PingPang' ]
            def methodInList = plays.find { it == name.split('play')[1] }
            if(methodInList) {
                // 实现
                def impl = { Object[] vargs ->
                    "playing ${name.split('play')[1]}..."
                }

                Person4 instance = this
                instance.metaClass."$name" = impl
                impl(args)
            } else {
                throw new MissingMethodException(name, Person1.class, args)
            }
        }

        def p4 = new Person4()
        println p4.work()
        println p4.playBasketBall()

        try {
            println p4.playPolitics()
        } catch (Exception ex) {
            println ex.message
        }
    }
}
