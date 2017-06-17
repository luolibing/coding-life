package cn.tim.groovy.demo1

import groovy.transform.PackageScope
import org.junit.Test
import sun.reflect.generics.scope.ClassScope

/**
 * Created by TIM on 2015/7/15.
 */
class Demo1 {

    @Test
    void calc() {
        def i = 1+3.2
        println i

        Date date = new Date()
        date = 1
        println date
    }

    String hello = "hello"

    void checkHello() {
        System.out.println(hello)
    }

    @Test
    void calcCheckHello() {
        checkHello()
    }

    @Test
    void list() {
        def jvmLanguages = ["java", "groovy", "scala", "clojure",1, new Date()]
        println(jvmLanguages[0])

        println(jvmLanguages.size())
        println(jvmLanguages)

        println jvmLanguages[-1]

        println jvmLanguages[0..2]
    }

    @Test
    void map() {
        def beanMap = [java:100, groovy:50, scale:40, now:new Date(), name: 'luolibing']
        println(beanMap["java"])
        beanMap["java"] = 8000
        println beanMap[0..2]
        println beanMap[-1]
    }

    String helloworld() {
        def str = "hello";
        str += " my name is luolibing"
    }

    @Test
    void helloTest() {
        println helloworld()
    }

    int method(String arg) {
        return 1;
    }

    int method(Object arg) {
        return 2;
    }

    @Test
    void assertEquals() {
        Object o = "object";
        int result = method(o)
        println result
        assertEquals(1, result)
    }

    void arrayTest() {
        int[] array = [1,2,3]
    }

    String name = "luolibing"

    private int age = 27
}
