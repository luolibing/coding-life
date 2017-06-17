package cn.tim.kotlin.clazz

import org.junit.Before
import org.junit.Test
import java.util.*

/**
 *
 * User: luolibing
 * Date: 2017/6/12 20:23
 */
class Address {
    // var可变的值，自动创建出get/set方法
    var name: String = ""
    // 可以省略类型，通过get()方法进行推断
    val street: Boolean get() = this.name.isEmpty()
    var city: String = "abc"
        private set // 将set方法置为私有
    var state: String? = ""// 必须属性初始化或者构造函数中初始化

    // 定义属性的完整语法
    var zip: String = ""
        get() = zip
        set(value) {field = value}

    // 单行表达式
    val isEmpty: Boolean get() = this.name.isEmpty()

    var setterWithAnnotation: Any? = null
        // @Inject set 也可以通过@Inject注解注入
}

fun copyAddress(address: Address): Address {
    val result = Address()
    // 自动判断是要赋值还是要取值
    result.name = address.name
    return result
}

class Back {
    var counter: Int = 0
        set(value) {
            // backing fields其实就是
            if(value >= 0) {
                // this.counter = value 如果这样调用，等同于setCounter(value)，这样就造成了无限递归了
                field = value
            }
        }
}

fun main(args: Array<String>) {
    val back = Back()
    back.counter = 10
    println(back.counter)

    val address = Address()
    address.zip = "aaaa"
}


class BackingProperties {
    private var _table: Map<String, Int> ?= null

    // 使用另外一个成员变量辅助getTable()，进行优化
    val table: Map<String, Int>
        get() {
            if(_table != null) {
                _table = HashMap<String, Int>()
            }
            return _table?:throw RuntimeException("set by another thread")
        }
}

/**
 * 编译时常量
 * 1 const必须作为顶级元素，不能包含在某个类里面
 * 2 不能包含自定义的getter方法
 * 3 必须有初始化值
 */
const val SUBSYSTEM_DEPRECATED: String = "子系统过期提醒"

// 编译时常量
@Deprecated(SUBSYSTEM_DEPRECATED)
class Constant

class TestSubject {
    fun execute() {
        println("testSubject execute()")
    }
}

class Lateinit {

    /**
     * lateinit要求
     * 1 必须使用var来设置值，不能是原生类型
     * 2 没有get set方法, 不能给subject设置null
     */
    lateinit var subject: TestSubject

    @Before fun setUp () {
        subject = TestSubject()
    }

    @Test fun test() {
        subject.execute()
    }
}