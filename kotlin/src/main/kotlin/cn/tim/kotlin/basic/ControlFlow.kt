package cn.tim.kotlin.basic

import java.util.*

/**
 *
 * User: luolibing
 * Date: 2017/6/12 14:48
 */
fun main(args: Array<String>) {
    ifExpression()

    whenExpression()

    hasPrefix("prefix11110000")

    loop()

    loopLabel()

    returnLabel()
}

fun ifExpression() {
    val num = Random().nextInt(10)
    // 单行表达式
    val flag = if(num % 2 == 0) 0 else 1
    val a = Random().nextInt(20)
    val b = Random().nextInt(20)

    // if表达式作为返回值，必须每个branch都包含返回值
    val max = if(a > b) {
        println("max is a")
        a
    } else {
        println("max is b")
        b
    }
}

/**
 * when用法
 */
fun whenExpression() {
    val num = Random().nextInt(5)
    when(num) {
        1 -> println("num = 1")
        2 -> println("num = 2")
        else -> println("other numbers")
    }

    when(num) {
        // 解决java中case只能单个常量的问题
        1, 2 -> println("num is 1 or 2")
        else -> println("other numbers")
    }

    when(num) {
        // 解决java中case只能使用常量的问题
        Integer.parseInt("1") -> print("num is 1")
        else -> println("other numbers")
    }

    val array = arrayOf(1, 2, 3)

    when(num) {
        // 新特性，范围in(not in) array
        in 1..10 -> println("num is in the range")
        in array -> println("num is in the array")
        !in 10..20 -> println("num not in 10..20")
        else -> println("other number")
    }

    // 等同于if-elseif-else
    when {
        num % 3 == 0 -> println("num is odd")
        num % 3 == 1 -> println("num is even")
        else -> println("other")
    }
}

// when表达式作为返回值
fun hasPrefix(x: Any) = when(x) {
    // is Class<T>类似于instanceOf
    is String -> x.startsWith("prefix")
    else -> false
}

// 循环遍历
fun loop() {
    val array = intArrayOf(1, 2, 3, 4)
    // 迭代器遍历, array.iterator(); array.next(); array.hasNext()
    for(x in array) {
        println("x = $x")
    }

    for(x: Int in array) {
        println("x = ${x * 2}")
    }

    // 使用index索引来遍历array
    for(i in array.indices) {
        println("array[$i] = ${array[i]}")
    }

    // 既有index又有元素的遍历方式withIndex()
    for((index, value) in array.withIndex()) {
        println("withIndex($index) value is $value")
    }
}


fun returns() {
    val person = Person(1, "luolibing")
    var name = person.id ?: return
}

open class Person(val id: Int, val name: String)

// loop-label标签, break label
fun loopLabel() {
    label@ for(i in 1..100) {
        if(i > 60) {
            break@label
        }
    }

    // 使用break label跳出外层循环
    exit@ for(i in 1..100) {
        continues@
        for(j in 1..100) {
            if(i * j >5000)
                break@exit
            else
                continue@continues
        }
    }
}

// return label
fun returnLabel() {
    val array = intArrayOf(0, 1, 2, 3)
    // 1 使用return@label跳出循环
    array.forEach exit@ {
        if(it == 0) return@exit
        println(it)
    }
    println("end of forEach return@exit")

    // 2 使用return@label跳出循环, 隐式的label
    array.forEach {
        if(it == 0) return@forEach
        println(it)
    }
    println("end of forEach return@forEach")

    // 3 使用匿名函数return，这个时候只会跳出匿名函数
    array.forEach(fun(value: Int) {
        if(value == 0) return
        println(value)
    })

    // 这个地方直接跳出整个方法了，不能直接使用return
    array.forEach {
        if(it == 0) return
        println(it)
    }
    println("end of forEach return")
}


