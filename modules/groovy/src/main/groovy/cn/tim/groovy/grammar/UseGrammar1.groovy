package cn.tim.groovy.grammar

import org.junit.Test

/**
 * Created by LuoLiBing on 15/7/27.
 */
class UseGrammar1 {

    @Test
    public void useTest1() {
        Entity entity = new Entity();
        entity.getHello()
    }

    class Entity implements GroovyInterceptable {
        String getHello() {
            return "hello entity"
        }

        Object invokeMethod(String name, Object args) {
            println "invoke $name $args"
        }
    }

    static class UseGrammar {
        static String getHello() {
            return "hello grammer"
        }

        static String getGoodbye(Entity entity) {
            return "hello grammer goodbye"
        }
    }

}
