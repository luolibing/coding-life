package cn.tim.groovy.closure

import org.junit.Test

/**
 * Created by TIM on 2015/8/12.
 */
class OwerClosure {

    @Test
    void run() {
        def whatIsOwnerMethod = { getOwner() }
        assert whatIsOwnerMethod() == this
        def whatIsOwner = { owner }
        assert whatIsOwner() == this
    }

    class Inner {
        Closure cl = { owner }
    }

    @Test
    void run1() {
        def inner = new Inner()
        assert inner.cl() == inner
    }

    @Test
    void run2() {
        def nestedClosures = {
            def cl = { owner }
            cl()
        }
        assert nestedClosures() == nestedClosures
    }
}
