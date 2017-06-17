package cn.tim.groovy1.collection

import org.junit.Test

/**
 * Created by TIM on 2015/9/7.
 */
class ListTest {

    @Test
    void eachTest() {
        def lst = [1, 2, 3, 4, 5]
        lst.each{ println it }

        lst.reverse(true)
        lst.each{ println it }

        lst.reverseEach { println  it }
    }

    @Test
    void collectTest() {
        def lst = [1, 2, 3, 4, 5]
        lst.each { it*2 }
        lst.each{ println it }

        lst = lst.collect { it * 2 }
        lst.each{ println it }
    }

    @Test
    void findTest() {
        def lst = [1, 2, 3, 4, 5]
        println lst.find{ it%2 == 0 }
        println lst.findAll { it>3 }.sum()
    }

    @Test
    void injectTest() {
        def lst = [1, 2, 3, 4, 5]
        println lst.inject(1) { carryOver,element -> carryOver + element}
    }

    @Test
    void flattenTest() {
        def lst = [[1, 2, 3], 4, 5]
        println lst
        println lst.flatten()
    }

    @Test
    void sizeTest() {
        def arr = ['a', 'bcd', 'efgk', 'ab']
        println arr.size()
        println arr*.size()
    }

    @Test
    void test1() {
        def lst = ['a', 'b', 'c', 'd']
        words(*lst)
    }

    def words(a, b, c, d) {
        println "$a, $b, $c, $d"
    }
}
