package cn.tim.groovy.closure

import groovy.transform.Canonical
import groovy.transform.Immutable
import org.junit.Test
import static Math.random as rand

/**
 * Created by LuoLiBing on 15/8/15.
 */
class GStringClosure {

    @Test
    void run1() {
        def x = 1
        def gs = "x = ${x}"
        //assert gs == 'x = 1'

        x = 2
        assert gs == 'x = 2'
    }

    @Test
    void run2() {
        def x = 1
        def gs = "x = ${ -> x}"
        assert gs == 'x = 1'

        x = 2
        assert gs == 'x = 2'
    }

    class Person {
        String name
        String toString() { name }
    }

    @Test
    void run3() {
        def sam = new Person(name: 'sam')
        def lucy = new Person(name: 'lucy')
        def  p = sam
        def gs = "Name: ${p}"
        assert gs == "Name: sam"
        p = lucy
        println gs

        sam.name = 'lucy'
        println gs
    }

    @Test
    void run4() {
        def sam = new Person(name: 'sam')
        def lucy = new Person(name: 'lucy')
        def p = sam
        def gs = "Name: ${ -> p}"
        p = lucy
        println gs
    }

    @Test
    void stringTest1() {
        for(def ch='a'; ch<'z'; ch++) {
            println ch
        }
    }

    class ComplexNumber {
        def real, imaginary
        def plus(other) {
            new ComplexNumber(real: real + other.real,
                imaginary: imaginary + other.imaginary)
        }
        String toString() {"$real ${imaginary >0 ? '+' : ''} ${imaginary} i"}
    }

    @Test
    void complexTest() {
        def c1 = new ComplexNumber(real: 1, imaginary: 2)
        def c2 = new ComplexNumber(real: 4, imaginary: 1)
        println c1 + c2
        println 3.getClass().name
    }

    @Test
    void foreach() {
        for(i in 0..10) {
            println i
        }
    }

    enum CoffeeSize {
        SHORT(10), SMALL(100), MIDDLE(200), BIG(300), LARGER(1000);
        final int sizeOfCoffeesize
        CoffeeSize(size) {
            sizeOfCoffeesize = size
        }
        def iterationDetails() {
            println "$this recommends $sizeOfCoffeesize size"
        }
    }

    @Test
    void enumTest() {
            def size = CoffeeSize.SHORT
        switch (size) {
            case [CoffeeSize.SHORT,CoffeeSize.SMALL]: println "С��"; break;
            case CoffeeSize.MIDDLE: println "�еȴ�С"; break;
        }

        for(s in CoffeeSize.values()) {
            s.iterationDetails()
        }
    }

    void reciveArgs(int a, int...b) {
        println a
    }

    @Test
    void staticImport() {
        def val = rand()
        println val
    }

    @Canonical(includes = "id, name")
    class Customer {
        Integer id
        String name
    }

    @Test
    void custTest() {
        def cust = new Customer(id: 1, name: 'zhang')
        println cust
    }

    class Manage {
        @Delegate Worker worker = new Worker()
        @Delegate Export export = new Export()
    }

    @Test
    void delegateTest() {
        def man = new Manage()
        man.work()
        man.export()
    }

    class Worker {
        void work() {
            println "i am work"
        }
    }

    class Export {
        void export() {
            println "i am export"
        }
    }

    @Immutable
    class CreditCard {
        String cardNumber
        int creditLimit
    }

    @Test
    void creditcardTest() {
        println new CreditCard("121212121", 134343)
    }

    interface listener {
        void sayHello(name)
        void goodbye()
    }

    @Test
    void listenerTest() {
        def lis = [
                sayHello: { println it },
                goodbye: { println 'goodbye!'}
        ] as listener
        def btn = new Button(listen: lis, name: 'tim')
        btn.init()
    }

    class Button {
        def listen
        def name

        void init() {
            listen.sayHello(name)
            println "init"
            listen.goodbye()
        }
    }

}
