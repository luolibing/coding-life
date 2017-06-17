package cn.tim.kotlin.clazz

/**
 *
 * User: luolibing
 * Date: 2017/6/13 14:21
 */
// 动态给类添加方法, 泛型方法
fun <T> MutableList<T>.swap(index1: Int, index2: Int) {
    // 这个地方的this,就是指的.swap前面这个地方传入的类MutableList类
    val temp = this[index1]
    this[index1] = this[index2]
    this[index2] = temp
    println(this::class)
}

// 对nullable的扩展
fun Any?.toString(): String {
    if(this == null) {
        return "null value"
    }
    return toString()
}

fun main(args: Array<String>) {
    val list = MutableList(10, { i -> i * i })
    list.swap(1, 0)
    println(list)

    printMethod(BB())

    Member().declare()

    println(null.toString())

    val list1 = listOf(1, 2, 3, 4)
    println(list1.lastIndex)

    MyCompanion.foo()
    MyCompanion.bar()

    MyCompanion().baz()

    F().caller(E())
}

open class AA

class BB : AA()

fun AA.a() = "a"

fun BB.a() = "b"

// 扩展方法被静态解析，根据a的声明类型来执行
fun printMethod(a: AA) {
    println(a.a()) // a
}


// 类的方法，被extension，会使用类的成员方法
open class Member {
    open fun declare() {
        println("Member")
    }
}

// 无法覆盖掉类原有的方法
fun Member.declare() = println("extension")

// 只读的lastIndex属性扩展, kotlin库包含这个扩展
val <T> List<T>.lastIndex: Int
    get() = size - 1

// 扩展属性不能有直接初始化值，得通过getter,setter来扩展
val Person.bar get() = 10


class MyCompanion {
    // 默认是Companion
    companion object {
        fun foo() = println("MyCompanion.foo()")
    }
}

// 扩展伴侣对象Companion
fun MyCompanion.Companion.bar() = println("MyCompanion.bar()")


fun MyCompanion.baz() {
    println("MyCompanion.baz()")
}


class E {
    fun bar() {
        println("E.bar()")
    }
}

class F {
    fun baz() {
        println("F.baz()")
    }

    fun E.foo() {
        bar() // 隐式调用E.bar()
        baz() // 隐式调用F.baz()

        // 当扩展发送方和扩展接收方方法同名时，会优先调用扩展接收方方法，如果要调用扩展发送方的方法得使用this@F来指定
        println(toString())
        println(this@F.toString())
    }

    fun caller(e: E) {
        e.foo()
    }
}


open class DD

class D1 : DD()

open class CC {
    open fun DD.foo() {
        println("DD.foo in CC")
    }

    open fun D1.foo() {
        println("D1.foo in C")
    }

    fun caller(dd: DD) {
        dd.foo()
    }
}