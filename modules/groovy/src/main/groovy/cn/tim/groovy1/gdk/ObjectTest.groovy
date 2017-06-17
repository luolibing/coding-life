package cn.tim.groovy1.gdk

import groovy.util.slurpersupport.GPathResult
import groovy.xml.DOMBuilder
import groovy.xml.MarkupBuilder
import groovy.xml.StreamingMarkupBuilder
import groovy.xml.dom.DOMCategory
import org.junit.Test
import org.w3c.dom.Document

/**
 * Created by TIM on 2015/9/9.
 */
class ObjectTest {

    @Test
    void dumpTest() {
        def p = new Person(27,'luolibing')
        println p.dump()
        println p.inspect()
    }

    class Person {
        int age
        String name
        Person(int age, String name) {
            this.age = age
            this.name = name
        }
        void sayHello() {
            println "name:$name, age:$age"
        }
    }

    @Test
    void withTest() {
        def lst = [1, 2, 3]
        lst.with {
            add(3)
            add(4)
            println lst.size()
            println lst
            println delegate
        }
    }

    @Test
    void sleepTest() {
        def thread = Thread.start {

        }
    }

    @Test
    void property() {
        def p = new Person(12, 'li')
        println "name: $p.name"
        p.invokeMethod('sayHello', null)
    }

    @Test
    void threadStart() {
        Thread.start {
            println "thread start!"
            sleep(1000)
            println "thread finish!"
        }
        sleep(1000)
        Thread.startDaemon {
            println "started Daemon!"
            sleep(5000)
            println "thread finish!"
        }
    }

    class Car {
        int miles, fuelLevel
        int getPrice() {
            return miles
        }
    }

    @Test
    void invoke1() {
        def car = new Car(fuelLevel: 12, miles: 25)
        println car."fuelLevel"

        println car.invokeMethod("getPrice", null)
    }

    @Test
    void arrTest1() {
        int[] arr = [1, 2, 3, 4, 5]
        println arr[2..3]
    }

    @Test
    void readTxt() {
        println new File("c:\\eula.1028.txt").text
        new File("c:\\eula.1028.txt").eachLine { line ->
            println line
        }
    }

    @Test
    void readXml() {
        def path = this.class.classLoader.getResource("languages.xml").path
        def document = DOMBuilder.parse(new FileReader(path))
        def rootElement = document.documentElement
        use(DOMCategory) {
            //println rootElement.localName
            //println rootElement.namespaceURI

            def languages = rootElement.language
            languages.each { language ->
                println "${language.'@name'} : ${language.author[0].text()}"
            }
        }
    }

    @Test
    void readXml1() {
        def path = this.class.classLoader.getResource("pom.xml").path
        def document = DOMBuilder.parse(new FileReader(path))
        def rootElement = document.documentElement
        use(DOMCategory) {
            //println rootElement.localName
            //println rootElement.namespaceURI
            println rootElement.name()
            def modelVersion = rootElement.modelVersion
            println modelVersion.text()

            def dependencies = rootElement.dependencies
            dependencies.each { dependency ->
                println dependency.groupId.text()
            }
        }
    }

    @Test
    void readXml2() {

        def path = this.class.classLoader.getResource("languages.xml").path
        def languages = new XmlParser().parse(path)
        languages.each {
            println "${it.@name} authors by ${it.author[0].text()}"
        }
    }

    @Test
    void readXml3() {
        def path = this.class.classLoader.getResource("languages.xml").path
        def languages = new XmlSlurper().parse(path)
        languages.language.each {
            println "${it.@name} authors by ${it.author[0].text()}"
        }
    }

    @Test
    void readXml4() {
        def path = this.class.classLoader.getResource("languages.xml").path
        def languages = new XmlSlurper().parse(path).declareNamespace(human: 'Natural')
        println languages.language.collect{ it.@name }.join(",")
    }

    @Test
    void writeXml1() {
        def langs = ['C++' : 'Stroustrup', 'java' : 'Gosling', 'Lisp' : 'McCarthy']
        def content = ''
        langs.each { language, name ->
            def fragment = '''
                <language name="${language}">
                    <author>${author}</author>
                </language>
            '''
            content += fragment
        }
        def xml = "<languages>${content}</languages>"
        println xml
    }

    @Test
    void writeXml2() {
        def langs = ['C++' : 'Stroustrup', 'java' : 'Gosling', 'Lisp' : 'McCarthy']
        def xmlDocument = new StreamingMarkupBuilder().bind { mkp ->
            mkp.xmlDeclaration()
            mkp.declareNamespace(computer: 'Computer')

            languages {
                comment << "created xml"
                langs.each { key, value->
                    computer.language(name: key) {
                        author (value)
                    }
                }
            }
        }
        println xmlDocument
    }
}
