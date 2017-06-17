package cn.tim.groovy1.mop1

import org.junit.Test

/**
 * Created by LuoLiBing on 15/10/15.
 */
@Category(String.class)
class StringUtilsAnnotated {

    def toSSN() {
        if(size() >=9) {
            "${this[0..2]}-${this[3..4]}-${this[5..8]}"
        }

    }

    @Test
    void test() {
        use(StringUtilsAnnotated) {
            println "xxx44395454243".toSSN()
        }
    }

}
