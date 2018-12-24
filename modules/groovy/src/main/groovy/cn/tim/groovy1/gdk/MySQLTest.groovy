package cn.tim.groovy1.gdk

import cn.tim.groovy.demo1.Demo1
import groovy.sql.Sql
import groovy.xml.MarkupBuilder
import org.junit.Test
/**
 * Created by TIM on 2015/9/18.
 */
class MySQLTest {

    private Sql sql;

//    @Before
//    void connectTest() {
//        sql = Sql.newInstance("jdbc:mysql://localhost:3306/benyoyo", 'root', 'root', 'com.mysql.jdbc.Driver')
//    }

    @Test
    void categoryTest() {
        println sql.connection.catalog
    }

    @Test
    void queryTest() {
        sql.eachRow('SELECT * FROM cps_capture_modify') {
            println "$it.record_id,$it.modify_reason"
        }
    }

    @Test
    void metaClouseTest() {
        def processMeta = { metaData ->
            metaData.columnCount.times { i ->
                printf "%-21s", metaData.getColumnLabel( i + 1 )
            }
            println ""
        }

        sql.eachRow('SELECT * FROM cps_capture_modify', processMeta) {
            printf "%-20s %s\n", it.record_id, it.modify_reason
        }
    }

    @Test
    void rowsTest() {
        def rows = sql.rows('SELECT * FROM cps_capture_modify')
        println "rows.length=$rows.size()"
    }

    @Test
    void transferToXml() {
        def bldr = new MarkupBuilder()
        def xml = bldr.capture {
            sql.eachRow('SELECT * FROM cps_capture_modify') {
                city(id: it.record_id, reason: it.modify_reason)
            }
        }
        println xml
    }

    @Test
    void dataSetTest() {
        def dataSet = sql.dataSet('cps_capture_modify')
        def interested = dataSet.findAll { it.id> 2 }
//        interested.each {
//            println it.year
//        }
        interested.each {
            println it.id
        }
    }

    @Test
    void check() {
        String name = null
        println name?.substring(0,1) == null ? "没有": "有"
    }

    @Test
    void access() {
        def grow = new ArrayList<>().outOfBoundsMsg(1)
        println grow
    }
}
