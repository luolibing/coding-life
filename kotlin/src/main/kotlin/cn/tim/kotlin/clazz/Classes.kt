package cn.tim.kotlin.clazz

import kotlin.reflect.full.allSuperclasses
import kotlin.reflect.full.superclasses

/**
 *
 * User: luolibing
 * Date: 2017/6/12 16:51
 */
class Invoice {}

class Empty

// kotlin可以有一个主要的构造函数，还有一个或者多个次要的构造函数
class Person constructor(firstName: String)

/**
 * 如果主要的构造函数没有任何注解或者访问控制，则constructor也可以省略.
 * 主要的constructor不能包含任何代码，初始化代码可以使用init方法
 */
class Person1(firstName: String) {
    init {
        println("customer initialized with value $firstName")
    }

    val customerKey = firstName.toUpperCase()
}

fun main(args: Array<String>) {
    Person1("jackson")

    Person4()

    Person4("jack")

    Person4("rose", 4)

    println(Members::class.superclasses)

    println(Any::class.allSuperclasses)

    val myClass = MyClass.create()
    println(myClass)
}

// 使用val和var来标记访问标识，val标记只读，var标记可读可写.
class Person2(val firstName: String, val lastName: String, var age: Int)


// 当使用访问修饰符或者注解来标记构造函数的时候，constructor不能省略
class Person3 public constructor(name: String)


// 定义次要的构造函数
class Person4() {

    // 次要的构造函数，参数不允许使用var或者val修饰。并且次要构造函数必须有委托给主要构造函数, 如果主要构造函数参数为空，也可以省略
    constructor(name: String, age: Int = 0) : this()
}

// 将构造函数私有化
class Person5 private constructor()

/**
 * class可以包含
 * 1 构造函数和initializer块
 * 2 Functions
 * 3 Properties
 * 4 嵌套类或者内部类
 * 5 Object Declarations对象声明
 *
 * kotlin所有的类都直接或者间接继承自Any, Any不是Object
 */
open class Members(size: Int)

// 使用继承，需要声明调用父类的构造函数
class Group(size: Int) : Members(size)

// 方法和类不声明为open，默认是final型
open class View(name: String) {
    open fun render() {
        println("View render")
    }
}

// 如果没有显式声明主构造函数，则需要在次要构造函数中使用super(args)来指定
class MyView : View {
    constructor(name: String) : super(name)

    constructor(name: String, left: Int, right: Int) : super(name)

    // 覆盖方法，需要显式覆盖，而且被覆盖方法还必须是open
    override fun render() {
        println("MyView render")
    }
}

open class Foo {
    open val name : String = "aaa"
    open val age : Int = 0
}

class FooSub : Foo() {
    // 直接覆盖属性
    override val name : String = "bbb"

    // 覆盖父类的属性，访问控制范围必须要比父类的大，如果覆盖的访问标识为var则需要覆盖get()和set()方法
    override var age: Int = 0
        get() = super.age
//        set(value) { age = value}
}


interface FooInterface {
    val count: Int
}

// 构造函数中覆盖
class Bar1(override val count: Int) : FooInterface

// 属性中覆盖
class Bar2 : FooInterface {
    override val count: Int = 3
}

open class A {
    open fun f() {
        println("A")
    }

    fun a() {
        println("a")
    }
}

interface B {
    fun f() {
        println("B")
    }

    fun b() {
        println("b")
    }
}

// class C extend A implements B
class C() : A(), B {

    // 因为接口B和类A都有f方法，这个地方需要覆盖f()方法，来消除歧义
    override fun f() {
        // 使用super<Base>.f()来指定实现哪一个方法
        super<A>.f()
        super<B>.f()
    }
}


// 抽象类
open class Base {
    open fun f() {}
}

abstract class Derived : Base() {
    override abstract fun f()
}

/**
 * 在kotlin中，类没有静态方法，要使用静态方法，可以使用包级别的function来代替.
 * 如果想调用静态方法，使用class.staticMethod()，例如Factory.create()，可以使用伴侣对象来实现，companion object指定.
 * MyClass.create()
 */
class MyClass {
    // 这个地方可以使用匿名伴侣对象，也可以显式指定名称
    companion object Factory {
        fun create(): MyClass = MyClass()
    }
}

