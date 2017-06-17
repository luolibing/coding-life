package cn.tim.groovy1.mop1

import org.junit.Test

/**
 * Created by LuoLiBing on 15/10/15.
 * DomCatagory
 */
class FindUtils {

    def static extractOnly(self, closure) {
        def result = ''
        self.each{
            if(closure(it)) {
                result += it
            }
        }
        result
    }

    @Test
    void test1() {
        use(FindUtils) {
            println "1246564462132".extractOnly{
                ((int)it)%2 == 0
            }
        }
    }

    @Test
    void test2() {
        use(CategoryTest.StringUtils, FindUtils) {
            def str = "000xx43x35a843"
            println str.toSSN()
            println str.extractOnly {
                ((int) it)%2 == 0
            }
        }
    }

    @Test
    void test3() {
        use(Helper) {
            println "abfddt4".toString()
        }
    }

    class Helper {
        def static toString(String self) {
            def method = self.metaClass.methods.find{ it.name == "toString"}
            '!!' +  method.invoke(self, null) + '!!'
        }
    }
}
