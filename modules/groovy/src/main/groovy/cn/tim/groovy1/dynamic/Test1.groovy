package cn.tim.groovy1.dynamic

import org.apache.commons.io.FilenameUtils
import org.junit.Test

/**
 * Created by TIM on 2015/8/27.
 */
class Test1 {

    class TakeHelp {
        def takeHelp(helper) {
            // ���������
            helper.helpMoveThings()
        }

        def eatSugarcane(helper) {
            if(helper.metaClass.respondsTo(helper, 'eatSugarcane')) {
                helper.eatSugarcane()
            } else {
                println 'not eat'
            }
        }
    }

    class Man {
        void helpMoveThings() {
            println "i am man, i can help you!!!"
        }
    }

    class Women {
        void helpMoveThings() {
            println "i am woman, i can help you too"
        }

        void eatSugarcane() {
            println "i am woman, i like eat sugarcane"
        }
    }

    @Test
    void test1() {
        def help = new TakeHelp()
        def man = new Man()
        help.takeHelp(man)
        def women = new Women()
        help.takeHelp(women)
        help.eatSugarcane(man)
        help.eatSugarcane(women)
    }

    @Test
    void test2() {
        use(FilenameUtils) {
            println "/usr/local/a.txt".path
            println "/usr/local/a.txt".name
        }
    }

}
