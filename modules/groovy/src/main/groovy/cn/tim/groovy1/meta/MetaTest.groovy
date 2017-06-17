package cn.tim.groovy1.meta

import org.junit.Test

/**
 * Created by TIM on 2015/8/20.
 */
class MetaTest {

    @Test
    void metaClassTest() {
        Integer.metaClass.percentRaise = { amount -> amount * (1 + delegate/100.00) }
        println 1.percentRaise(100)

        10.times {
            println it
        }
    }

    @Test
    void StringMetaClassTest() {
        String.metaClass.isPalindrome = { ->
            delegate = delegate.reverse()
        }
        def word = "luolibing"
        println word.isPalindrome()
        println word
    }

    @Test
    void arrayListTest() {
        def list = ['groovy', 'is', 'hip']
        println list.join(" ")
        println list.getClass()
    }
}
