package cn.tim.groovy1.inteceptor

/**
 * Created by LuoLiBing on 15/10/13.
 */
class GroovyMetaClassInterceptableTest {

    def start() { println "start" }

    def check() { println "check" }

    def drive() { println "drive" }

    def methodMissing(String name, Object args) {
        System.out.println("method missing $name $args")
    }
}
