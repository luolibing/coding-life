package cn.tim.mybatis.base;

import cn.tim.mybatis.base.mapper.CityMapper;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

/**
 * Created by luolibing on 2017/4/1.
 */
@SpringBootApplication
public class MybatisApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(MybatisApplication.class, args);
    }

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder
                .create()
                .driverClassName("com.mysql.jdbc.Driver")
                .url("jdbc:mysql://localhost:3306/world?useUnicode=true&characterEncoding=utf8")
                .username("root")
                .password("root")
                .build();
    }

    @Override
    public void run(String... strings) throws Exception {
        // 数据源
        DataSource dataSource = dataSource();
        // 事物factory
        JdbcTransactionFactory transactionFactory = new JdbcTransactionFactory();
        // 环境
        Environment environment = new Environment("dev", transactionFactory, dataSource);
        // 配置
        Configuration configuration = new Configuration(environment);
        configuration.addMapper(CityMapper.class);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);

        //
        try(SqlSession session = sqlSessionFactory.openSession()) {
            CityMapper mapper = session.getMapper(CityMapper.class);
        }
    }
}
