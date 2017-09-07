package cn.tim.mybatis.sessionfactory;

import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.util.UUID;

/**
 * User: luolibing
 * Date: 2017/9/7 17:16
 */
@org.springframework.context.annotation.Configuration
public class SqlSessionFactoryTest {

    @Bean
    public void buildSqlSessionFactory(DataSource dataSource) {
       TransactionFactory transactionFactory = new JdbcTransactionFactory();
       Environment environment = new Environment(UUID.randomUUID().toString(), transactionFactory, dataSource);
       Configuration configuration = new Configuration();
       configuration.setEnvironment(environment);
       SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);

    }
}
