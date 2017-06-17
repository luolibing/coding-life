package cn.tim.groovy1.mop1

import org.junit.Test

/**
 * Created by LuoLiBing on 15/10/17.
 */
class MixinTest {

    /**
     * 在需要注入的类上，使用@Mixin([Person,Teacher])注解注入
     */
    @Test
    void test1() {
        def jack = new Person(firstName: "Jack", lastName: "lee")
        println jack.listen()
    }

    /**
     * 不修改源代码注入mixin类
     */
    @Test
    void test2() {
        // mixin注入
        Dog.mixin Friend
        def dog = new Dog(firstName: "Buddy")
        println dog.listen()
    }

    /**
     * 不注入mixin类，无法调用
     */
    @Test
    void test3() {
        def dog = new Dog(firstName: "jack")
        try {
            println dog.listen()
        } catch (Exception e) {
            println e.message
        }
    }

    /**
     * 将mixin注入到指定的实例中
     */
    @Test
    void test4() {
        def dog = new Dog(firstName: "jack")
        dog.metaClass.mixin Friend
        println dog.listen()

        def socks = new Dog(firstName: "jack")
        try{
            println socks.listen()
        } catch (Exception e) {
            println e.message
        }
    }
}
