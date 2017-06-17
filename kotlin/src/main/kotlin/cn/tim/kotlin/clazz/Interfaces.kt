package cn.tim.kotlin.clazz

/**
 *
 * User: luolibing
 * Date: 2017/6/13 10:48
 */
interface Interfaces {
    // 抽象的name
    val name: String

    // 默认的accessor实现
    val address get() = "wudaokou"

    fun foo()

    fun bar() {
        println("implements bar()")
    }
}

class Imple : Interfaces {

    // 实现抽象name
    override val name: String = "luolibing"

    override fun foo() {
        println("implements foo()")
    }
}

interface AI {
    fun a() = println("AI.a()")
    fun b() = println("AI.b()")
}

interface BI {
    fun a() = println("BI.a()")
    fun b()
}

class ImpleI : AI, BI {
    // 因为有实现冲突，这个方法必须实现
    override fun a() {
        super<AI>.a()
        super<BI>.a()
    }

    override fun b() {
        super<AI>.b()
    }

}