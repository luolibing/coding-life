package cn.tim.groovy.lambdas
import org.junit.Test

import java.nio.file.Paths
/**
 * Created by TIM on 2015/7/17.
 */
class Sample1 {

    @Test
    void test1() {
        Runnable run = { println 'run'}
        List list = [1, 'hello', new Date(), false]
        println list[-1]
        list.each { print "${it}  " }
        GString
    }

    @Test
    void testGString1() {
        int a = 1;
        String quote = "${a}"
        println quote
        a = 2
        println quote
    }

    @Test
    void testGString2() {
        int a = 1;
        GString quote = "${->a}"
        println quote
        a = 2
        println quote
    }

    @Test
    void testGString3() {
        assert 'c'.getClass() == String
        assert "c".getClass() == String
        int b = 1
        assert "c${b}".getClass() in GString
    }

    @Test
    void tsetChar1() {
        char a = "a"
        assert Character.digit(a, 16) == 10
        try {
            assert Character.digit((char)'a', 16) == 10
            assert Character.digit('a', 16) == 10
            assert true: 'Need explicit cast'
        } catch (MissingMethodException e) {}

    }

    @Test
    void testChar2() {
        assert ((char) "c").class == Character
        assert ("C" as char).class == Character

            ((char) 'cx') == 'c'
            assert alse:'will fail not castable'


        assert ('cx' as char) == 'c'
        assert 'cx'.asType(char) == 'c'
    }

    @Test
    void testFindIndex() {
        println ([1, 5, 3].indexOf(5))
        int[] arr = [1, 5, 3]
        println arr.findIndexOf { it == 7}
    }

    @Test
    void testPath() {
        def path = Paths.get("/Users/tim")
        println path.getFileName().toString()
    }


    @Test
    void orderCompare() {
        def lines1 = Paths.get("/share/fishcard-logs/order.txt").readLines()
        def lines2 = Paths.get("/share/fishcard-logs/service.txt").readLines()
        def order = new ArrayList(lines1)
        def service = new ArrayList(lines2)
        order.removeAll(service)
        println order

        order = new ArrayList(lines1)
        service = new ArrayList(lines2)
        service.removeAll(order)
        println service.collect {it.toString().substring(0, it.toString().length()-2)}
        println service.size()
    }
}
