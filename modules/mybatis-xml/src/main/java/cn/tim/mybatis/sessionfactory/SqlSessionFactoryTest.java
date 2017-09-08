package cn.tim.mybatis.sessionfactory;

import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;
import java.util.UUID;

/**
 * User: luolibing
 * Date: 2017/9/7 17:16
 */
@org.springframework.context.annotation.Configuration
public class SqlSessionFactoryTest {

//    @Bean

    /**
     * mybatis的几个配置
     * 1 datasource
     * 2 configuration （可以通过spring.properties或者yml注入，也可以通过mybatis.xml配置）
     * 3 plugins interceptors
     * 4 TypeAliases 字段别名
     * 5 TypeHandler 字段处理
     * 6 MapperLocation 映射文件位置
     * 7 vfs
     *
     * @param dataSource
     */
    public void buildSqlSessionFactory(DataSource dataSource) {
       TransactionFactory transactionFactory = new JdbcTransactionFactory();
       Environment environment = new Environment(UUID.randomUUID().toString(), transactionFactory, dataSource);
       Configuration configuration = new Configuration();
       configuration.setEnvironment(environment);
       SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);

    }
}
