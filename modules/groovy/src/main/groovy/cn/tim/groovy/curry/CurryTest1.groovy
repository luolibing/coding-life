package cn.tim.groovy.curry

import org.junit.Test

/**
 * Created by TIM on 2015/8/18.
 */
class CurryTest1 {

    @Test
    void leftCurry1() {
        def ncopies = { int n, String str -> str*n }
        def twice = ncopies.curry(2)
        println twice('luo')

        leftCurry2(ncopies.curry(3))
    }

    void leftCurry2(def ncopies) {
        println ncopies('liu')
    }

    @Test
    void rightCurry() {
        def nCopies = { int n, String str -> str*n }
        def blah = nCopies.rcurry("luo")
        println blah(2)
    }

    @Test
    void indexCurry() {
        def volume = { double l, double w, double h -> l*w*h}
        def fixedWithVolume = volume.ncurry(1, 2d)
        fixedWithVolume = fixedWithVolume.ncurry(1, 2d)
        println fixedWithVolume(1d)
    }

    @Test
    void memoization() {
        def fib
        fib = { long n -> n<2 ? n: fib(n-1) + fib(n-2)}
        println fib(30)
    }

    @Test
    void memoize() {
        def fib
        fib = { long n -> n<2 ? n: fib(n-1) + fib(n-2)}.memoizeAtLeast(20)

        println fib(50)
    }

    @Test
    void composition() {
        def plus2 = { it+2 }
        def time3 = { it*3 }
        def time3plus2 =  time3 << plus2
        println time3plus2(2)
    }

    @Test
    void composition2() {
        def sayHello = {String str-> str[1..3]}
        def sayBye = { String str-> str[2]}
        def sayHelloBye = sayBye << sayHello
        println sayHelloBye("luolibing")
    }

    @Test
    void trampoline() {
        def factorial
        factorial = { int n, def accu = 1G ->
            if (n < 2) return accu
            factorial.trampoline(n - 1, n * accu)
        }

        factorial = factorial.trampoline()
        println factorial(1)
        println factorial(3)
        println factorial(1000)
    }
}
