package cn.tim.groovy.demo1
import org.junit.Test
/**
 * Created by TIM on 2015/7/16.
 */
class Demo2 {

    @Test
    void test1() {
        println  new Demo1().name;
        println new Demo1().age
        println new Demo1().@name
    }

    @Test
    void readFile1() {
        new File("C:/eula.1031.txt").eachLine("UTF-8") {
            println it
        }
    }

    @Test
    void readFile2() {
        new File("C:/eula.1031.txt").withReader("UTF-8") { reader ->
            reader.eachLine{
                println it
            }
        }
    }

    public class X {
        void sayHelloX() {
            println "hello X!"
        }
    }

    public X foo() {
        return new X()
    }

    public static X createX(Demo2 d) {
        return new X(d)
    }

    @Test
    void testInnerClass() {
        X x = Demo2.createX(this);
        x.sayHelloX()
    }
}
