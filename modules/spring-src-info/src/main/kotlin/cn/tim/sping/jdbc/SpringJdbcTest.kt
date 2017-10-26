//package cn.tim.sping.jdbc
//
//import org.springframework.context.support.ClassPathXmlApplicationContext
//import org.springframework.jdbc.core.JdbcTemplate
//import org.springframework.jdbc.core.RowMapper
//import org.springframework.transaction.annotation.Propagation
//import org.springframework.transaction.annotation.Transactional
//import java.sql.ResultSet
//import java.sql.Types
//import javax.sql.DataSource
//
///**
// * Created by luolibing on 2017/6/13.
// */
//class Book(var id: Long, var name: String)
//
//class BookRowMapper : RowMapper<Book> {
//    override fun mapRow(rs: ResultSet?, rowNum: Int): Book {
//        // !!断言
//        if (rs != null) {
//            return Book(rs.getLong("id"), rs.getString("name") ?: "未知")
//        } else {
//            return Book(-1, "null")
//        }
//    }
//
//}
//
//@Transactional(propagation = Propagation.REQUIRED, rollbackFor= arrayOf(Exception::class))
//interface BookService {
//    fun save(book: Book)
//
//    fun getBooks(): List<Book>
//}
//
//class BookServiceImpl(dataSource: DataSource) : BookService {
//
//    var jdbcTemplate: JdbcTemplate
//
//    init {
//        jdbcTemplate = JdbcTemplate(dataSource)
//    }
//
//    override fun getBooks(): List<Book> {
//        return jdbcTemplate.query("select * from book", BookRowMapper())
//    }
//
//    /**
//     * 默认只会回滚RuntimeException，所以要不回滚，可以声明为其他的异常
//     */
//    @Transactional
//    override fun save(book: Book) {
//        jdbcTemplate.update(
//                "insert into book(id,name) values(?,?)",
//                arrayOf(book.id, book.name),
//                intArrayOf(Types.BIGINT, Types.VARCHAR))
//        throw Exception("aaa")
//    }
//}
//
//fun main(args: Array<String>) {
//    val context = ClassPathXmlApplicationContext("jdbc/jdbc.xml")
//    val bean = context.getBean(BookService::class.java)
//    bean.save(Book(103237L, "测试Book"))
//    val books = bean.getBooks()
//    for(book in books) {
//        println(book)
//    }
//}