package cn.tim.groovy1.mop3

import org.junit.Test

/**
 * Created by LuoLiBing on 15/10/17.
 * Expando动态创建类
 */
class ExpandoTest {

    /**
     * 使用Expando创建动态类
     */
    @Test
    void test0() {
        def carA = new Expando()
        def carB = new Expando(name: "luolibing", age: 27)

        carA.name = "liuxiaoling"
        carA.age = 27

        println "carA: $carA"
        println "carB：$carB"
    }


    @Test
    void test1() {
        def carA = new Expando(name: "luolibing", miles: 0, sayHello: { -> println "sayHello...!!!"})
        carA.drive = {
            miles += 10
            println "$miles can driven"
        }

        carA.drive()
    }

    @Test
    void test2() {
        Map<String, String> actors = [:] as Map<String, String>

        actors.put(null, "111")

    }
}
