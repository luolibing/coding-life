package cn.tim.groovy1.mop2

import org.junit.Test

/**
 * Created by LuoLiBing on 15/10/17.
 */
class Person6 {

    @Test
    void test0() {
        def emc = new ExpandoMetaClass(Person6)
        emc.methodMissing = { String name, args ->
            "I'm Jack of all trades... I can $name"
        }
        emc.initialize()

        def jack = new Person6()
        def lee = new Person6()

        jack.metaClass = emc

        println jack.playFootBall()
        try {
            println lee.playGames()
        } catch (Exception ex) {
            println ex.message
        }
    }
}
