package cn.tim.groovy1.mop1.mixin

import org.junit.Before
import org.junit.Test

/**
 * Created by LuoLiBing on 15/10/17.
 */
class Filters {

    @Before
    void before() {
        /**
         * 执行过滤器链
         */
        Object.metaClass.invokeOnPreviousMixin = {
            MetaClass metaClass, String method, Object[] args ->
                def previousMixin = delegate.getClass()
                // 遍历mixinClasses，找到对应的过滤器链，然后执行对应的方法
                for(mixin in mixedIn.mixinClasses) {
                    if(mixin.mixinClass.theClass == metaClass.delegate.theClass) {
                        break
                    }
                    previousMixin = mixin.mixinClass.theClass
                }
                mixedIn[previousMixin]."$method"(*args)
        }
    }

    def writeStuff(writer) {
        writer.write("This is stupid")
        println writer
    }

    def create(theWriter, Object[] filters = []) {
        def instance = theWriter.newInstance()
        // 将filter加入到mixin中
        filters.each { filter ->
            instance.metaClass.mixin filter
        }
        instance
    }

    @Test
    void test1() {
        writeStuff(create(StringWriter))
    }

    /**
     * 过滤器链
     */
    @Test
    void test2() {
        writeStuff(create(StringWriter, UppercaseFilter))
    }


    @Test
    void test3() {
        writeStuff(create(StringWriter, ProfanityFilter))
    }

    @Test
    void test4() {
        // 过滤器链
        writeStuff(create(StringWriter, UppercaseFilter, ProfanityFilter))
        writeStuff(create(StringWriter, ProfanityFilter, UppercaseFilter))
    }
}

/**
 * 过滤器
 */
class UppercaseFilter {

    void write(String message) {
        def allUpper = message.toUpperCase()
        invokeOnPreviousMixin(metaClass, "write", allUpper)
    }
}

/**
 * 过滤器
 */
class ProfanityFilter {
    void write(String message) {
        def filtered = message.replaceAll('stupid', 's*****')
        invokeOnPreviousMixin(metaClass, 'write', filtered)
    }
}

