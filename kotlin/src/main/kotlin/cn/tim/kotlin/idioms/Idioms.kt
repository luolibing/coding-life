package cn.tim.kotlin.idioms

import cn.tim.kotlin.syntax.Person
import java.io.File
import java.lang.System.currentTimeMillis
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import kotlin.collections.HashMap

/**
 *
 * User: luolibing
 * Date: 2017/6/9 13:38
 */
fun main(args: Array<String>) {
    val address = Address("北京")
    val customer = Customer(1, "luolibing", address)
    println(customer)

    val customerClone = customer.copy()
    customerClone.address = Address("上海")
    println(customer)

    // filter过滤使用, 跟groovy有异曲同工，一毛一样
    val numbers = listOf(1, 4, 5, 3, 10, 20, 11, 12, 15)
    val subNumbers = numbers.filter { it % 2 == 0 }
    println(subNumbers)

    // 在字符串中使用变量，用法跟groovy又一样
    // println("Name $name")

    whenFun(Address("亦庄"))

    // 延迟属性
    lazyPro()

    // 动态扩展方法
    Person().sayGoodBye()

    // 单例
    Singleton.res

    // notNull增强 obj?.method当不为空时调用
    shortHand()

    // returnWhen
    val code = returnWhen("Black")
    println(code)

    // return tryCatch返回tryCatch
    tryCatch()

    // build风格方式
    val array = arrayOfMinusOnes(10)
    println(Arrays.toString(array))

    // with用法
    withMethod()

    // java7的 try-resources
    tryResource()


    // onsumNullableBoolean(null)
}

/**
 * DTOs
 * 自动添加 get/set，且使用的方式是obj.pro，当给他赋值时算set，读取值时算get
 * 自动添加
 * equals()
 * hashCode()
 * toString()
 * copy()       感觉像是深度克隆
 */
data class Customer(var id: Int, var name: String, var address: Address)

data class Address(var data: String)

// 默认方法参数
fun foo(a: Int = 0, b: String = "") {}

// 对于Object，这个地方上会用Any来代替，有可能是任何类型
fun whenFun(x: Any) {
    // 有点类似于switch, 同时is类似于instanceOf
    when(x) {
        is Person -> {
            println("person " + x)
        }
        is Address -> {
            println("address " + x)
        }
        else -> {
            println("Any ")
        }
    }
}

/**
 * 遍历map和list集合
 */
fun <K, V> travers(map: Map<K, V>, list: List<K>) {
    for((k, v) in map) {
        println("$k : $v")
    }

    for(k in list) {
        println(k)
    }
}

/**
 * 范围
 */
fun range() {
    for(i in 1..100) {} // 1<=i<=100
    for(i in 1 until 100) {} // 1 <= i < 100
    for(i in 1..100 step 2) {} // i += 2
    for(i in 100 downTo 1) {} // i = 100; i >=1; i--
    var x = Random().nextInt(20)
    if(x in 1..10) {} // x 在1..10范围内
}

/**
 * 只读集合, kotlin通过var与val来区分可变与不可变变量，var为可变，val为不可变变量
 */
fun readOnly() {
    val list = listOf(1, 2, 3)
    // list.add(4) 只读的list

    val map = mapOf(1 to "one", 2 to "two")
    // map[3] = "three" 只读map
    // val不可变，这个地方不能再将map = HashMap();重新复制，类似于final

    // hashMap的赋值与取值
    val hashMap = HashMap<Int, String>()
    hashMap[1] = "one"
    hashMap[2] = "two"
    println(hashMap[2])
}

/**
 * 延迟获取property:
 * 类似于java8的Supplier<T>函数
 */
fun lazyPro() {
    val timestamp = currentTimeMillis()
    // 类似于一个闭包
    val name : String by lazy {
        var res = "b"
        if(timestamp.toInt() % 2 == 0) {
            res = "t"
        }
        res
    }

    println(name)
}


// 动态的给类添加方法
fun Person.sayGoodBye() {
    println("new method[sayGoodBye()]")
}

// 单例对象 object关键字
object Singleton {
    val res = "luolibing"
}

// not null增强，跟groovy差不多
fun shortHand() {
    val files = File("D:\\a").listFiles()
    // 当files为null时不调用.size，直接返回null
    println(files?.size)

    // 当为null时返回empty
    println(files?.size ?: "empty")

    val data = mapOf(1 to "one", 2 to "two")
    // 当data[-1]为null时抛出异常
    data[1] ?: throw Exception("invalid key")

    // 当data[1]不为null时执行闭包
    data[1].let {
        println("number[CN] = " + it)
    }
}

// 返回when
fun returnWhen(color : String) :Int {
    return when(color) {
        "Red" -> 0
        "Green" -> 1
        "Black" -> 2
        else -> throw RuntimeException("valid color")
    }
}

// tryCatch
fun tryCatch() {
    val result = try {
        100 / Random().nextInt(10)
    } catch (e: Exception) {
        throw RuntimeException(e)
    }
}

// 使用if表达式赋值，必须包含else
fun ifExpression(param : Int) {
    val result = if(param == 1) {
        "one"
    } else if(param == 2) {
        "two"
    } else {
        "other"
    }
}

// builder风格写法
fun arrayOfMinusOnes(size : Int) : IntArray {
    val random = Random()
    // fill都是填充的一样的数据
    return IntArray(size).apply { fill(random.nextInt(100)) }
}

// 单行表达式
fun defaultNum() = 42

// 更加简洁的预发，省略了返回值大括号
fun ifExpression1(param : Int)
  = if(param == 1) {
        "one"
    } else if(param == 2) {
        "two"
    } else {
        "other"
    }


class Turtle {
    fun start() {
        println("start")
    }
    fun stop() {
        println("stop")
    }
    fun execute(i : Int) {
        println("execute $i")
    }
}

// with用法，相当于groovy的use用法
fun withMethod() {
    val turtle = Turtle()
    with(turtle) {
        start()
        for(i in 0..100) {
            execute(i)
        }
        stop()
    }
}

// try-resource java7 中的try resources
fun tryResource() {
    val stream = Files.newInputStream(Paths.get("D:\\all.log"))
    stream.buffered().reader().use { reader->
        println(reader.readText())
    }
}

//  public final class Gson {
//     ...
//     public <T> T fromJson(JsonElement json, Class<T> classOfT) throws JsonSyntaxException {
//     ...

// 泛型函数的简便写法，这样写真的好吗？
//inline fun <reified T : Any> Gson.fromJson(json) : T = this.fromJson(json, T::class.java)

fun consumNullableBoolean(b: Boolean) {
    // b: Boolean ?= true; 如果Boolean为null做为false
    var b: Boolean ? = false
    if(b == true) {
        println("true")
    } else {
        println("False")
    }
}
