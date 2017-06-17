
/**
 * 方法使用了fun代替
 * 数组使用Array代替
 * 包路径可以和文件夹路径不一致
 * User: luolibing
 * Date: 2017/6/8 17:15
 */
fun main(args: Array<String>) {
    println("hello world")
    var sum = sum1(1, 2)
    println(sum.javaClass)

    printSum1(1, 2)
}

/**
 * 参数的表达方式 a: Int, 参数名: 参数类型（内置了一些类型，不需要import了）
 * 返回值: Int，有点类似于c#
 */
fun sum(a: Int, b: Int): Int {
    return a + b
}

/**
 * 返回值不指定，默认要返回Unit(?)
 * 返回值自动猜测了, 如果是返回null呢，会抛出编译错！
 */
fun sum1(a: Int, b: Int) = a + b

// 无返回值: Unit 也可以省略
fun printSum1(a: Int, b : Int) :Unit {
    println("a($a) + b($b) = ${a + b}")
}

