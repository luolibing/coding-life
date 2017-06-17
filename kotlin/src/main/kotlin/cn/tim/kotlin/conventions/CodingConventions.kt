package cn.tim.kotlin.conventions

import java.io.Serializable

/**
 *
 * User: luolibing
 * Date: 2017/6/9 20:06
 */
interface Bar {}

interface Foo<out T : Any> : Bar {
    fun foo(a: Int): T
}

fun lambdas(list : ArrayList<Int>) {
    list.filter { it > 10 }.map { e -> e * 2 }
}

// 不定义为open，默认是final类？
open class Human(id: Int, name: String)

// 继承自某个类
class Person(
        id: Int,
        name: String,
        surname: String
) : Human(id, name)

// 继承类，并且实现接口, 继承的类要写在第一个，后面跟着一个一个的接口
class Person1(
        id: Int,
        name: String,
        surname: String
) : Human(id, name), Serializable

fun main(args: Array<String>) {
    Person(1, "name", "sur")
    Human(1, "liu")
}

// 没有参数的方法和属性可以互换，虽然风格上相似，但是属性的性能更高一些，时间复杂度为o(1)