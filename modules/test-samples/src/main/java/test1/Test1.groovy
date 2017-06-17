package test1

import org.junit.Test

import java.nio.file.Paths

/**
 * Created by LuoLiBing on 15/9/1.
 */
class Test1 {

    @Test
    void test1() {
        def hashCodeAbs = Math.abs("儿歌片段－对话场景－rainy..jpg".hashCode()).toString()
        println hashCodeAbs[0..2]
        println "00012828dee7e3fdf2fb823fa1ddd38a".length()
    }

    @Test
    void test2() {
        def path = Paths.get("/Users/tim/1.txt")
        println path.getFileName().toString()
    }
}
