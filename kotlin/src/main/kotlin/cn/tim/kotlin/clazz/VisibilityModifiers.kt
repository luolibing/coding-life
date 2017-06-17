

/**
 * kotlin的访问标识符有四种：private protected internal public
 * 当没有指定任何标识符的时候，默认为public
 *
 * private只在当前类有效
 * protected = private + subclasses
 * internal在同一个module里面访问
 *
 * 外部类不能访问内部类的private成员，这个与java不一样
 * User: luolibing
 * Date: 2017/6/13 11:18
 */

package cn.tim.kotlin.clazz

fun baz() {}

class Bar {}

private fun foo() {} // 在当前文件有效

public var bar: Int = 5 // get到处都能访问
    private set         // set方法当前文件有效

internal val baz = 6    // 同一个Module有效

open class Outer {
    private val a = 1

    protected open val b = 2

    internal val c = 3

    val d = 4 // 默认为public

    protected class Nested {
        public val e: Int = 5
    }
}

class Subclass: Outer() {
    fun execute() {
        // super.a  a因为是private不能访问，bcd可以访问
        Nested() // Nested也可以访问
    }

    // 只能覆盖b,而且覆盖后的b还是protected
    public override val b = 10
}

class Unrelated(o: Outer) {
    init {
        o.c // internal
        o.d // public
    }

    fun execute(subClass: Subclass) {
        subClass.b
    }
}


// 将主构造函数声明为private
class D private constructor(a: Int) {
    private constructor(a: Int, name: String) : this(a)
}