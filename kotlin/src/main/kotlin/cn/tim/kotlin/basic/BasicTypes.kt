package cn.tim.kotlin.basic

import java.util.*

/**
 * 数字类型：大致与java一样，整形Int, 没有char
 * 字面常量：没有八进制
 * User: luolibing
 * Date: 2017/6/12 11:25
 */
fun main(args: Array<String>) {
    representation()

    val d = decimalDigitValue('1')
    println(d)

    array()

    array1()

    strings()
}

fun representation() {
    val a: Int = 10000
    print(a === a)
    val boxedA: Int? = a
    val anotherBoxedA: Int? = a
    // 3个=代表判断对象
    print(boxedA === anotherBoxedA)
    // 2个=代表判断数值
    println(boxedA == anotherBoxedA)
}

fun explicitConversions() {
    val a: Int? = 1
    // val b: Long? = a // 明确转换

    // 较小的数据类型不能隐式转换成较大的数据类型
    val b: Byte = 1
    // val i: Int = b; 不能隐式将byte向上转换为int
    val i: Int = b.toInt() // 使用toInt()显式转换

    val l = 1L + 3 // 类型推断 Long + Integer
}

// 位运算
fun bitwise() {
    val x = (1 shl 2) and 0x000FF000 // 左移2位
    1 shr 2// 右移
    1 ushr 2 // 无符号右移
}

fun decimalDigitValue(c: Char): Int {
    if(c !in '0'..'9') {
        throw IllegalArgumentException("out of range")
    }
    return c.toInt() - '0'.toInt()
}

fun array() {
    val a1 = arrayOf(1, 2, 3, 4)
    val nullArray = arrayOfNulls<Int>(10) // 生成空数组
    val array = Array(10, { a -> (a * a) })
    println(Arrays.toString(array))
}

// 不允许将一个Array<Int>赋值给Array<Any>，但是当作为返回值的时候，方法返回类型允许使用Array<Any>
fun array1(): Array<out Any> {
    val array = Array(3, { x -> x * x })
    // 相当于set
    array[0] = 1
    // set get
    array[1] = array[0] + array[2]
    return array
}

fun intArray() {
    // IntArray,ByteArray,ShortArray这些类都拥有和Array一样的方法列表，但是却不是Array的子类
    val x: IntArray = intArrayOf(1, 2, 3)
    x[0] = x[1] + x[2]
}

fun strings() {
    val str = "hello,world"
    for(c in str) {
        println(c)
    }

    // 这个用法和groovy一样
    val s = """
        for(int i = 0; i < 100; i++) {
            println(i)
        }
    """
    println(s)

    // trim前缀
    val s1 = """
    |good idea,
    |let me see,
    |good night,
    |hello world,
    """.trimMargin("|")
    println(s1)

    println("$str.length is ${str.length}")
}