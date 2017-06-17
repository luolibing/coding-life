package cn.tim.groovy.metaclass

import org.junit.Test

/**
 * Created by LuoLiBing on 15/8/7.
 */
class Pogo {
    def filed1 = "luo"
    def field2 = "bing"
    def getField1() {
        return "getLuo"
    }

    @Test
    void pogoTest1() {
        def pogo = new Pogo()
        assert pogo.metaClass.getAttribute(pogo, 'field1') == 'luo'
        assert pogo.metaClass.getAttribute(pogo, 'field2') == 'bing'
    }

    private String field
    String property1
    void setProperty1(String property1) {
        this.property1 = "setProperty1"
    }

    @Test
    void pogoTest2() {
        def pogo = new Pogo()
        pogo.@property1 = "timbing"
        println pogo.property1

        pogo.property1 = "luolibing"
        println pogo.property1

        pogo.metaClass.setAttribute(pogo, 'field', 'liuxiaoling')
        println pogo.field
    }

    def methodMissing1(String name, def args) {
        return "the function $name($args) is not def"
    }

    @Test
    void methodMissingTest() {
        println new Pogo().unknowMethod(1, 2, 3)
    }

    def dynamicMethods = [
            sayHi: { name-> "sayHello $name"},
            goodBye: { "goodbye!"},
            info: { name,age -> "name:$name, age:$age"}
    ]

    def methodMissing(String name, args) {
        def method = dynamicMethods.find {
            it.key == name
        }
        if(method) {
            Pogo.metaClass."$name" = { Object[] varArgs ->
                method.invoke(delegate, name, args)
            }
            return method.invoke(delegate, name, args)
        } else {
            throw new MissingMethodException(name, delegate, args)
        }
    }

    @Test
    void methodMissing2Test() {
        def pogo = new Pogo()
        pogo.sayHi("luolibing")
    }
}
