package cn.tim.groovy1.collection

import org.junit.Test

/**
 * Created by TIM on 2015/9/7.
 */
class MapTest {

    @Test
    void test() {
        def map = ['java':'gosling', 'c++':'jack']
        println map.java
        println map.'c++'

        map.each { entry ->
            println "key: $entry.key , value: $entry.value"
        }

        println map.collect { lan, author ->
            lan.toUpperCase()
        }

        println map.groupBy {
            it.value
        }
    }
}
