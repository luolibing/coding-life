package cn.tim.sping.jdbc

import java.sql.Connection
import java.sql.DriverManager

/**
 * kotlin jdbc数据库查询
 * Created by luolibing on 2017/6/13.
 */
class JdbcTest {

    companion object {
        init {
            Class.forName("com.mysql.jdbc.Driver")
        }
    }

    fun execute() {
        val connection = createConnection()
        val stmt = connection.createStatement()
        stmt.fetchSize = 20
        stmt.maxRows = 10
        val resultSet = stmt.executeQuery("SELECT * FROM BOOK LIMIT 0, 10")
        while (resultSet.next()) {
            val name = resultSet.getString("NAME")
            println(name)
        }
        connection.close()
    }

    fun createConnection(): Connection {
        return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/bebase", "root", "root")
    }
}

fun main(args: Array<String>) {
    JdbcTest().execute()
}