package cn.tim.groovy1.mop1

import org.junit.Test

/**
 * Created by LuoLiBing on 15/10/13.
 */
class CategoryTest {

    class StringUtils {
        def static toSSN(self) {
            // XXX-XX-XXXX
            if(self.size() >= 9) {
                "${self[0..2]}-${self[3..4]}-${self[5..8]}"
            }
        }
    }

    @Test
    void test1() {
        String isbn = "0044xx545s"
        use(StringUtils) {
            println isbn.toSSN()
            println new StringBuilder(isbn).toSSN()
        }

        println isbn.toSSN()
    }

    @Test
    void test2() {
        Object obj = new Person()
        println obj.hasProperty("CACHE_KEY1")
    }


    class Person {
        public final static String CACHE_KEY = "persons"
    }
}
