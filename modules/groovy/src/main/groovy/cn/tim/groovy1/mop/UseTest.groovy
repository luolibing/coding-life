package cn.tim.groovy1.mop

import org.junit.Test

import javax.script.ScriptEngine
import javax.script.ScriptEngineManager

/**
 * Created by LuoLiBing on 15/9/23.
 */
class UseTest {

    static String revert(String delegate) {
        return delegate.reverse()
    }

    @Test
    void test1() {
        use(UseTest, User1Test) {
            println "luolibing".revert()
        }
    }

    static class User1Test {
        static String revert(String delegate) {
            return delegate.reverse()
        }
    }

    @Test
    void callScript() {
        def manager = new ScriptEngineManager()
        def engine = manager.getEngineByName("groovy")

        engine.eval("println 'hello world!'")
    }

    void useClouse(clouse) {
        clouse.call()
    }
}
