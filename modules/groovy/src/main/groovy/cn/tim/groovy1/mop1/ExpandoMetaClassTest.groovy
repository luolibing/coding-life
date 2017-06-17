package cn.tim.groovy1.mop1

import org.junit.Test

/**
 * Created by LuoLiBing on 15/10/16.
 */
class ExpandoMetaClassTest {

    /**
     * 在expandoMetaClass上注入方法
     */
    @Test
    void test1() {
        Integer.metaClass.daysFormNow = { ->
            def today = Calendar.instance
            today.add(Calendar.DAY_OF_MONTH, delegate)
            today.time
        }
        println 5.daysFormNow()

        Integer.metaClass.getDaysFormNow = { ->
            def today = Calendar.instance
            today.add(Calendar.DAY_OF_MONTH, delegate)
            today.time
        }
        println 5.daysFormNow
    }

    /**
     * 将闭包封装分别注入到两个不同的类
     */
    @Test
    void test2() {
        def daysFromNow = { ->
            def today = Calendar.instance
            today.add(Calendar.DAY_OF_MONTH, (int)delegate)
            today.time
        }

        Integer.metaClass.getDaysFromNow = daysFromNow
        Long.metaClass.getDaysFromNow = daysFromNow

        println 10.daysFromNow
        println 5L.daysFromNow
    }

    /**
     * 静态注入
     */
    @Test
    void test3() {
        Integer.metaClass.'static'.isEven = { val -> val%2==0}
        println Integer.isEven(3)
        println Integer.isEven(4)
    }

    /**
     * 构造函数注入
     */
    @Test
    void test4() {
        Integer.metaClass.constructor << { Calendar calendar ->
            new Integer(calendar.get(Calendar.DAY_OF_YEAR))
        }

        println new Integer(Calendar.instance)
    }

    /**
     * 覆盖构造函数
     */
    @Test
    void test5() {
        Integer.metaClass.constructor = {int val ->
            println "before constructor!"
            def constructor = Integer.class.getConstructor(Integer.TYPE)
            constructor.newInstance(val)
        }
        println new Integer(4)
        println new Integer(Calendar.instance)
    }

    /**
     *
     */
    @Test
    void test6() {
        def interface1 = [
                sayHello: { ->
                    println "sayHello"
                }
        ] as Interface1
        interface1.sayHello()

        def p = [] as Person
        p.name = "luolibing"
        p.age = 28
        println p
    }

    /**
     * ExpandoMetaClass  EMC DSL语法
     * 代码只能从groovy内部调用，不能从编译后的代码中调用
     */
    @Test
    void test7() {
        Integer.metaClass {
            dayFromNow = { ->
                Calendar calendar = Calendar.instance
                calendar.add(Calendar.DAY_OF_MONTH, (int)delegate)
                calendar.time
            }

            getDayFromNow = { ->
                Calendar calendar = Calendar.instance
                calendar.add(Calendar.DAY_OF_MONTH, (int)delegate)
                calendar.time
            }

            /**
             * static静态方法
             */
            'static' {
                isEven = { val -> val % 2 == 0 }
            }

            constructor = { Calendar calendar ->
                new Integer(calendar.get(Calendar.DAY_OF_YEAR))
            }

            constructor = {int val ->
                println "before constructor!"
                def constructor = Integer.class.getConstructor(Integer.TYPE)
                constructor.newInstance(val)
            }
        }

        println 6.dayFromNow()
        println 10.dayFromNow

        println new Integer(6)
        println new Integer(Calendar.instance)
    }

    /**
     * EMC 封装Person，将EMC设置到一个实例中
     */
    @Test
    void test8() {
        def emc = new ExpandoMetaClass(Person)
        emc.sing = {
            println "I can sing!"
        }
        emc.initialize()

        def jack = new Person()
        def paul = new Person()
        jack.metaClass = emc
        jack.sing()

        try {
            paul.sing()
        } catch (Exception e) {
            println e
        }

        jack.metaClass = null
        try {
            jack.sing()
        } catch (Exception e) {
            println e
        }
    }

    @Test
    void test9() {
        def jack = new Person()
        def paul = new Person()
        jack.metaClass.sing = { ->
            'I can sing!'
        }
        println jack.sing()

        try {
            paul.sing()
        } catch (Exception e) {
            println e
        }

        jack.metaClass = null
        try {
            println jack.sing()
        } catch (Exception e) {
            println e
        }

    }

    class Person {
        int age
        String name
        String toString() {
            println "name: $name, age：$age"
        }
    }

    interface Interface1 {
        void sayHello()

        void sayGoodBye()
    }

}
