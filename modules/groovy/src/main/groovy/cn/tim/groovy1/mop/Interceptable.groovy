package cn.tim.groovy1.mop


/**
 * Created by LuoLiBing on 15/9/24.
 */
class Interceptable {

    def sayHello() {
        "invoke Test1!"
    }

    def invokeMethod(String name, Object args) {
        println "invokeMethod"
        "called invokeMethod $name $args"
    }

    def methodMissing(String name, def args) {
        println "$name is missing $args"
        return "$name is missing $args"
    }

    def invoke(String methodName) {
        println this."$methodName"()
    }

    static def home() {
        System.out.println("it's my Home!")
    }

}
