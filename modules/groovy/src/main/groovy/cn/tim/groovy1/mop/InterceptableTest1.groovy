package cn.tim.groovy1.mop

import org.junit.Before
import org.junit.Test

/**
 * Created by LuoLiBing on 15/9/24.
 */
class InterceptableTest1 implements GroovyInterceptable {

    def interceptable

    @Before
    void setUp() {
        this.interceptable = new Interceptable()
    }

    @Test
    void test1() {
        //println interceptable.sayHello()
        println "test1"
        // interceptable.foo("mark", 20)
    }

    /**
     * 实例上使用metaClass在上面添加方法
     */
    @Test
    void testMetaClass1() {
        interceptable.metaClass.method1 = { name ->
            println "method1Invoke: $name"
        }
        interceptable.method1("luolibing")

        def test1 = new Interceptable()
        test1.method1("liuxiaoling")
    }

    /**
     * 动态执行方法 test1."$methodName"()
     */
    @Test
    void testMetaClass2() {
        interceptable.invoke("sayHello")

        def test1 = new Interceptable()
        test1.method1("liuxiaoling")
    }

    /**
     * 类上添加metaClass
     */
    @Test
    void testClassMetaClass1() {
        Interceptable.metaClass.method1 = { name ->
            println "method1Invoke: $name"
        }
        println interceptable.sayHello()
        interceptable.method1("luolibing")
        new Interceptable().method1("luolibing")
    }

    /**
     * 在静态方法上添加invokeMethod
     */
    @Test
    void testStaticMetaClassTest1() {
        Interceptable.metaClass.'static'.invokeMethod = { String name, Object args ->
            def staticMethod = Interceptable.metaClass.getStaticMetaMethod(name, args)
            def result = staticMethod.invoke(delegate, args)
            System.out.println("invoke method $name")
            return result
        }

        Interceptable.home()
        println ""
        Interceptable.home()
    }

    public static void main(String[] args) {
        def test1 = new Interceptable()
        println test1.sayHello()
        println test1.foo("mark", 20)
    }

    @Test
    void invokeMethod1() {
        def test1 = [] as InterceptableTest1
        test1.test1()
    }

    def invokeMethod(String name, Object args) {
        return "called method $name $args"
    }

    def test() {
        return 'method exists'
    }

    @Test
    void test2() {
        println new InterceptableTest1().test();
    }
}
