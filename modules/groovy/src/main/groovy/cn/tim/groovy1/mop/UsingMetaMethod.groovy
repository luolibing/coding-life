package cn.tim.groovy1.mop

import org.junit.Test

/**
 * Created by LuoLiBing on 15/10/12.
 */
class UsingMetaMethod {

    @Test
    void test1() {
        def str = "hello"
        def methodName = 'toUpperCase'

        def method = str.metaClass.getMetaMethod(methodName)
        println method.invoke(str)
    }

    void test1(a) {
        println a
    }

    void test1(a,b) {
        println "$a$b"
    }

    /**
     * 是否有对应的方法,respondsTo方法本身返回的是一个List<MetaMethod>，将其作为一个布尔判断，groovy会自动转换为true或者false
     * 使用名字不带参数可以找出所有的同名方法
     */
    @Test
    void test2() {
        def str = "hello"
        println String.metaClass.respondsTo(str, 'toUpperCase')?'yes':'no'
        println String.metaClass.respondsTo(str, 'getString')?'yes':'no'

        println UsingMetaMethod.metaClass.respondsTo(new UsingMetaMethod(), 'test1', 'a')
    }

    void test3(obj) {
        def usrRequestedProperty = 'bytes'
        def usrRequestedMethod = 'toUpperCase'
        println obj[usrRequestedProperty]
        println obj."$usrRequestedProperty"
        println obj."$usrRequestedMethod"()
        println obj.invokeMethod(usrRequestedMethod, null)
    }

    @Test
    void test4() {
        new UsingMetaMethod().test3("luolibing")
    }

    @Test
    void test5() {
        "luolibing".properties.each { property ->
            println property
        }
    }

    @Test
    void test6() {

    }
}
