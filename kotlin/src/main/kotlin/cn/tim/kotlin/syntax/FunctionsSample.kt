package cn.tim.kotlin.syntax

import com.sun.corba.se.impl.orbutil.graph.Graph
import sun.security.provider.certpath.Vertex
import java.util.*
// 导入别名，这个好，java里面遇到同路径同名类，还得一个带全路径
// kotlin会自动导入一大堆包，跟java类似，java也会默认导入java.lang包
import kotlin.collections.ArrayList as AL

/**
 *
 * User: luolibing
 * Date: 2017/6/8 18:05
 */
fun main(args: Array<String>) {
    // dsl?
    val i = 1 roll 100
    println(i)

    // 可命名参数，可读性会好一些
    reformat(
            str = "luolibing",
            nor = true,
            upperCase = false
            )

    // 有时候默认参数只想传第一个和第三个参数，在java里面这样是需要重写一大堆方法，这个时候可以指定参数
    // 但是调用Java方法不要这样使用，因为字节码经常会丢失一些参数名称
    reformat("luolibing", upperCase = true)

    printHello(null)

    // 变长参数
    val list = asList("a", "B", "c")
    println(list)

    // 变长参数不是第一个参数，可以使用命名参数来解决，但是好像得所有参数都命名
    asList(ts = intArrayOf(1, 2,3 ), name = "luolibing")

    var array = arrayOf("1", "2", "3")

    // 这个地方使用array和*array的区别就是，直接使用array会将这个list作为一个元素看待，而使用*array，会将list中的元素取出作为子元素看
    val asList = asList("b", "c", *array, "d")
    println(asList)

    Person().sayHello()
}

// 中间符号方法：infix, 感觉感觉有点像groovy呢，给类动态添加方法！！！
infix fun Int.roll(x: Int) : Int {
    return Random().nextInt(x) + this
}

/**
 * 方法提供默认值，例如offset可以从0开始也可以自定义，size同样
 *
 * 确实会节省很多的重载方法！！！
 */
fun read(b: Array<Byte>, offset: Int = 0, size: Int = b.size) {

}

open class A {
    open fun foo(i: Int = 10) {}
}

class B : A() {
    override fun foo(i: Int) {} // 这个地方不允许使用默认值, 要覆盖方法时，必须使用override
}

// 命名参数
fun reformat(str: String,
                nor: Boolean = true,
                upperCase: Boolean = true) {
}

/**
 * 无返回值，默认的是返回Unit类型
 */
fun printHello(name: String?): Unit {
    if(name != null) {
        println("Hello $name")
    } else {
        println("Hi")
    }
    return Unit // return和return Unit都是可选的，可以省略
}

// 单行表达式，可以省略大括号和返回值
fun single(a : Int, b : Int) : Int = a + b

// 对于复杂的方法体需要显示指明返回类型，除非返回的是Unit类型，因为复杂的方法体，有多个分支流程控制很难推断出具体的返回类型

// 可变长参数，等同于java里面的...，与Java一样，变长参数也需要在方法参数的末尾
fun <T> asList(vararg ts: T) : List<T> {
    var result = AL<T>()
    // 遍历方式
    for(t in ts) {
        result.add(t)
    }
    return result
}

// 可变长参数不是最后一个参数
fun <T> asList(vararg ts: T, name: String): List<T> {
    var result = AL<T>()
    // 遍历方式
    for(t in ts) {
        result.add(t)
    }
    println(name)
    return result
}

/**
 * Function Scope 方法域
 * function可以作为顶级元素，与类同级，调用不依赖于其他类，类似于php的系统函数。也可以指定为某个类的方法
 */
class Person {

    // 成员方法
    fun sayHello() {
        println("hello")
    }
}


/**
 * 本地方法： 方法中的方法
 */
fun dfs(graph: Graph) {
    // kotlin的闭包
    fun dfs(current: Vertex, visited: Set<Vertex>) {
        println(graph)
    }

    //dfs(null, HashSet())
}

// 尾递归函数
tailrec fun findFixPoint(x: Double = 1.0) : Double
        = if(x == Math.cos(x)) x else findFixPoint(Math.cos(x))

// 传统常规方式
private fun findFixPoint1() : Double {
    var x = 1.0;
    while (true) {
        var y = Math.cos(x)
        if(x == y) return y
        x = y
    }
}




