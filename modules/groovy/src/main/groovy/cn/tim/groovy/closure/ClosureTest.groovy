package cn.tim.groovy.closure

import groovy.util.logging.Slf4j
import org.junit.Test

/**
 * Created by LuoLiBing on 15/8/11.
 */
@Slf4j
class ClosureTest {

    @Test
    void sleepTest() {
        sleep 1000l, {
            println "hand"
            return true
        }

        sleep(100) {

        }

        sleep(1000l, {
            println "hander"
            return false
        })
    }

    @Test
    void slf4jTest() {
        log.info("hello world")
    }

    @Test
    void parameterTest() {
        def closureWithOneArg = { String str-> str.toUpperCase() }
        println closureWithOneArg("abcdef")

        def closureWithTwoArgs = { a, b -> a + b }
        println closureWithTwoArgs(1, 3)
    }

    @Test
    void implicit() {
        def greeting = {"hello $it"}
        println greeting("luolibing")

        def greeting1 = { it -> "hello $it" }
        println greeting1("tim")

        def magicNumber = { -> 42 }
        println magicNumber()
    }

    @Test
    void varargs() {
        def concat1 = { String... args -> args.join(" ")}
        println concat1('1', '2', '3')

        def concat2 = { String[] args -> args.join(',') }
        println concat2('1', '2', '3')

        def multiConcat = { int n, String... args -> args.join(' ') * n }
        println multiConcat(2, 'abc', 'efg')
    }

    @Test
    void thisTest1() {
        assert {} instanceof Closure
        def whatIsThisObject = { getThisObject() }
        assert whatIsThisObject() == this
        def whatIsThis = { this }
        assert whatIsThis() == this
    }

    @Test
    void thisTest2() {
        def inner = new Inner()
        assert inner.cl == inner
    }

    class Inner {
        Closure cl = { this }
    }

    class NestedClosures {
        void run() {
            def nestedClosures = {
                def cl = {this}
                cl()
            }
            assert nestedClosures() == this
        }
    }

    @Test
    void thisTest3() {
        def p = new Person(name: 'Tim', age: 25)
        assert p.dump() == 'Tim is 25 years old'
    }

    class Person {
        String name
        int age
        String toString() { "$name is $age years old" }

        String dump() {
            def cl = {
                String msg = this.toString()
                println msg
                msg
            }
            cl()
        }
    }
}
