package cn.tim.groovy1.gdk

import org.junit.Test

/**
 * Created by TIM on 2015/8/21.
 */
class ShellTest {

    @Test
    void test1() {
        def process = Runtime.getRuntime().exec("svn help")
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()))
        String line
        while((line = reader.readLine()) != null) {
            println line
        }
    }

    @Test
    void test2() {
        println "svn help".execute().text
        println "svn help".execute().getClass().name
        "svn help".execute().waitFor()
        "svn".execute().waitForOrKill(1000)
    }

    @Test
    void test3() {
        Person p = null
        p?.sayHello()
    }

    class Person {
        void sayHello() {
            println "hello"
        }
    }

    void openFile(filename) {
        new FileInputStream(filename)
    }

    @Test
    void testOpenFile() {
        try {
            openFile("11111")
        } catch (ex) {
            ex.printStackTrace()
        }
    }

    @Test
    void testStatic() {
        Wizard.learn('groovy', { println "i am study groovy"})
                .learn('scale', { println 'i am study scale'})
        new Wizard().age = 50
    }

    final int year = 100

    class Wizard {
        private int age = 23
        def static learn(trick, action) {
            // action
            action()
            this
        }

        private void setAge(age) {
            throw new IllegalAccessException("you are not allowed to change miles")
        }
    }

    class Robot {
        //def type, height, width
        def access(weight, location, fragile) {
            println "Received fragile? $fragile, weight$weight, loc$location"
        }
    }

    @Test
    void test4() {
        def rebot = new Robot()
        //println "$rebot.type"

        //rebot.access(x:30, y:20, z:10,  50, true)
        rebot.access(50, true, x:30, y:20, z: 50)
    }

    @Test
    void test5() {
        println log(1000)
        println log(1024, 10)
        println log(1024, 2)
    }

    def log(x, base = 10) {
        Math.log(x) / Math.log(base)
    }

    def task(name, String[] details) {
        println "name: $name, detail:$details"
    }

    @Test
    void test6() {
        task("luolibing")
    }

    @Test
    void test7() {
        def (firstName, lastName) = splitName('Tim Bing')
        println "$lastName, $firstName $lastName"

        def(name, age) = sayHello()
        println "name: $name, age: $age"
    }

    def splitName(fullName) { fullName.split(' ') }

    def sayHello() { ['luolibing', 24] }

}
